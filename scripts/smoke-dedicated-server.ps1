param(
    [int]$TimeoutSeconds = 120,
    [switch]$AcceptEula
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$RepoRoot = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$BuildDir = Join-Path $RepoRoot "build"
$RunDir = Join-Path $RepoRoot "run"
$ReportPath = Join-Path $BuildDir "dedicated-server-smoke-report.json"
$StdoutPath = Join-Path $BuildDir "dedicated-server-smoke.stdout.log"
$StderrPath = Join-Path $BuildDir "dedicated-server-smoke.stderr.log"
$GradleWrapper = Join-Path $RepoRoot "gradlew.bat"
$EulaPath = Join-Path $RunDir "eula.txt"

if ($TimeoutSeconds -lt 30) {
    throw "TimeoutSeconds must be at least 30."
}
if (-not (Test-Path -LiteralPath $GradleWrapper -PathType Leaf)) {
    throw "Gradle wrapper not found: $GradleWrapper"
}

New-Item -ItemType Directory -Force -Path $BuildDir | Out-Null
New-Item -ItemType Directory -Force -Path $RunDir | Out-Null

if ($AcceptEula) {
    @(
        "# Accepted for local Immersive EGO dedicated-server smoke testing."
        "eula=true"
    ) | Set-Content -LiteralPath $EulaPath -Encoding ASCII
}

if (-not (Test-Path -LiteralPath $EulaPath -PathType Leaf) -or
        -not ((Get-Content -Raw -LiteralPath $EulaPath) -match '(?im)^\s*eula\s*=\s*true\s*$')) {
    throw "Minecraft EULA is not accepted for the local run directory. Re-run with -AcceptEula to write $EulaPath for this smoke test."
}

Remove-Item -LiteralPath $StdoutPath, $StderrPath -Force -ErrorAction SilentlyContinue

function Get-DescendantProcessIds {
    param([Parameter(Mandatory = $true)][int]$ParentProcessId)

    $children = @(Get-CimInstance Win32_Process -Filter "ParentProcessId=$ParentProcessId" -ErrorAction SilentlyContinue)
    foreach ($child in $children) {
        [int]$child.ProcessId
        Get-DescendantProcessIds -ParentProcessId ([int]$child.ProcessId)
    }
}

function Stop-ProcessTree {
    param([Parameter(Mandatory = $true)][int]$RootProcessId)

    $processIds = @(Get-DescendantProcessIds -ParentProcessId $RootProcessId)
    $processIds += $RootProcessId
    foreach ($processId in ($processIds | Select-Object -Unique | Sort-Object -Descending)) {
        $process = Get-Process -Id $processId -ErrorAction SilentlyContinue
        if ($null -ne $process) {
            Stop-Process -Id $processId -Force -ErrorAction SilentlyContinue
        }
    }
}

function Read-LogText {
    $parts = @()
    if (Test-Path -LiteralPath $StdoutPath -PathType Leaf) {
        $parts += Get-Content -Raw -LiteralPath $StdoutPath -ErrorAction SilentlyContinue
    }
    if (Test-Path -LiteralPath $StderrPath -PathType Leaf) {
        $parts += Get-Content -Raw -LiteralPath $StderrPath -ErrorAction SilentlyContinue
    }
    return ($parts -join "`n")
}

$requiredMarkers = @(
    "Immersive EGO bootstrap loaded",
    "Loaded Immersive EGO dynamic server config",
    "Loaded Immersive EGO synergy config",
    "Loaded scanner spec from",
    "Done ("
)
$failurePatterns = @(
    "Mod Loading has failed",
    "Failed to load scanner_spec",
    "Missing or unsupported mandatory dependencies",
    "Exception in thread",
    "java.lang.",
    "Attempted to load class net/minecraft/client",
    "Attempted to load class net.minecraft.client",
    "NoClassDefFoundError: net/minecraft/client",
    "ClassNotFoundException: net.minecraft.client",
    "Crash report",
    "[ERROR]"
)

$startedAt = Get-Date
$process = Start-Process `
    -FilePath $GradleWrapper `
    -ArgumentList @("--no-daemon", "runServer") `
    -WorkingDirectory $RepoRoot `
    -RedirectStandardOutput $StdoutPath `
    -RedirectStandardError $StderrPath `
    -WindowStyle Hidden `
    -PassThru

$success = $false
$timedOut = $false
$failureMatches = @()
$seenMarkers = @()
$terminatedAfterSuccess = $false

try {
    while ($true) {
        Start-Sleep -Seconds 2
        $logText = Read-LogText
        $seenMarkers = @($requiredMarkers | Where-Object { $logText.Contains($_) })
        $failureMatches = @($failurePatterns | Where-Object { $logText.Contains($_) })

        if ($failureMatches.Count -gt 0) {
            break
        }
        if ($seenMarkers.Count -eq $requiredMarkers.Count) {
            $success = $true
            break
        }
        if ($process.HasExited) {
            break
        }
        if (((Get-Date) - $startedAt).TotalSeconds -ge $TimeoutSeconds) {
            $timedOut = $true
            break
        }
    }
}
finally {
    if (-not $process.HasExited) {
        Stop-ProcessTree -RootProcessId $process.Id
        $terminatedAfterSuccess = $success
        Start-Sleep -Seconds 2
    }
}

$exitCode = $null
try {
    $process.Refresh()
    if ($process.HasExited) {
        $exitCode = $process.ExitCode
    }
}
catch {
    $exitCode = $null
}

$report = [pscustomobject]@{
    success = $success
    timeoutSeconds = $TimeoutSeconds
    timedOut = $timedOut
    processId = $process.Id
    exitCode = $exitCode
    terminatedAfterSuccess = $terminatedAfterSuccess
    acceptedEula = [bool]$AcceptEula
    startedAt = $startedAt.ToString("o")
    finishedAt = (Get-Date).ToString("o")
    command = ".\gradlew.bat --no-daemon runServer"
    stdout = $StdoutPath
    stderr = $StderrPath
    requiredMarkers = $requiredMarkers
    seenMarkers = $seenMarkers
    failureMatches = $failureMatches
}

$report | ConvertTo-Json -Depth 8 | Set-Content -LiteralPath $ReportPath -Encoding UTF8
$report | ConvertTo-Json -Depth 8

if (-not $success) {
    throw "Dedicated server smoke failed. See $ReportPath and $StdoutPath."
}
