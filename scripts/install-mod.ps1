param(
    [string]$ModsDir = "C:\Users\Emmanuel Tremblay\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods",
    [string]$BuildDir = "",
    [string]$ModId = "immersive_ego",
    [switch]$SkipRuntimeDependencies
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

if ([string]::IsNullOrWhiteSpace($BuildDir)) {
    $BuildDir = Join-Path (Resolve-Path (Join-Path $PSScriptRoot "..")).Path "build\libs"
}

$BuildDir = (Resolve-Path -LiteralPath $BuildDir).Path
$RepoRoot = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$ReportPath = Join-Path $RepoRoot "build\install-report.json"

function Resolve-SafeModsDirectory {
    param([Parameter(Mandatory = $true)][string]$Path)

    if ([string]::IsNullOrWhiteSpace($Path)) {
        throw "ModsDir cannot be blank."
    }

    $fullPath = [System.IO.Path]::GetFullPath($Path)
    if (Test-Path -LiteralPath $fullPath) {
        $item = Get-Item -LiteralPath $fullPath
        if (-not $item.PSIsContainer) {
            throw "ModsDir exists but is not a directory: $fullPath"
        }
        return $item.FullName
    }

    $leaf = Split-Path -Path $fullPath -Leaf
    if ($leaf -ne "mods") {
        throw "ModsDir does not exist and its final segment is not 'mods'; refusing to create ambiguous target directory: $fullPath"
    }

    $parent = Split-Path -Path $fullPath -Parent
    if ([string]::IsNullOrWhiteSpace($parent) -or -not (Test-Path -LiteralPath $parent -PathType Container)) {
        throw "ModsDir parent does not exist; refusing to create full target tree: $parent"
    }

    New-Item -ItemType Directory -Path $fullPath | Out-Null
    return (Resolve-Path -LiteralPath $fullPath).Path
}

if (-not $SkipRuntimeDependencies) {
    $runtimeDepInstaller = Join-Path $PSScriptRoot "install-runtime-deps.ps1"
    if (-not (Test-Path -LiteralPath $runtimeDepInstaller)) {
        throw "Runtime dependency installer not found: $runtimeDepInstaller"
    }
    & $runtimeDepInstaller -ModsDir $ModsDir | Out-Host
}

function Get-JarMetadata {
    param([Parameter(Mandatory = $true)][string]$JarPath)

    Add-Type -AssemblyName System.IO.Compression.FileSystem
    $zip = [System.IO.Compression.ZipFile]::OpenRead($JarPath)
    try {
        $entry = $zip.Entries | Where-Object { $_.FullName -eq "META-INF/neoforge.mods.toml" } | Select-Object -First 1
        if ($null -eq $entry) {
            return $null
        }
        $reader = New-Object System.IO.StreamReader($entry.Open())
        try {
            $content = $reader.ReadToEnd()
        }
        finally {
            $reader.Dispose()
        }

        $modIdMatch = [regex]::Match($content, 'modId\s*=\s*"([^"]+)"')
        $versionMatch = [regex]::Match($content, 'version\s*=\s*"([^"]+)"')
        $nameMatch = [regex]::Match($content, 'displayName\s*=\s*"([^"]+)"')

        [pscustomobject]@{
            modId = if ($modIdMatch.Success) { $modIdMatch.Groups[1].Value } else { $null }
            version = if ($versionMatch.Success) { $versionMatch.Groups[1].Value } else { $null }
            displayName = if ($nameMatch.Success) { $nameMatch.Groups[1].Value } else { $null }
        }
    }
    finally {
        $zip.Dispose()
    }
}

function Get-Sha256 {
    param([Parameter(Mandatory = $true)][string]$Path)
    (Get-FileHash -LiteralPath $Path -Algorithm SHA256).Hash.ToLowerInvariant()
}

$excludedNamePattern = '(?i)(sources|javadoc|dev|plain|test|api)'
$runtimeCandidates = Get-ChildItem -LiteralPath $BuildDir -Filter "*.jar" -File |
    Where-Object { $_.BaseName -notmatch $excludedNamePattern } |
    ForEach-Object {
        $metadata = Get-JarMetadata -JarPath $_.FullName
        [pscustomobject]@{
            file = $_
            metadata = $metadata
        }
    } |
    Where-Object { $null -ne $_.metadata -and $_.metadata.modId -eq $ModId }

if ($runtimeCandidates.Count -ne 1) {
    $candidateNames = $runtimeCandidates | ForEach-Object { $_.file.FullName }
    throw "Expected exactly one runtime jar for mod id '$ModId' in '$BuildDir'; found $($runtimeCandidates.Count): $($candidateNames -join ', ')"
}

$sourceJar = $runtimeCandidates[0].file
$sourceMetadata = $runtimeCandidates[0].metadata

$ModsDir = Resolve-SafeModsDirectory -Path $ModsDir

$matchedInstalled = @()
Get-ChildItem -LiteralPath $ModsDir -Filter "*.jar" -File | ForEach-Object {
    $jar = $_
    $matchedBy = @()
    $modIdFilePattern = "(?i)^$([regex]::Escape($ModId))(-.*)?[.]jar$"
    $legacyFilePattern = "(?i)^immersive-ego(-.*)?[.]jar$"
    if ($jar.Name -match $modIdFilePattern -or $jar.Name -match $legacyFilePattern) {
        $matchedBy += "filename"
    }
    try {
        $metadata = Get-JarMetadata -JarPath $jar.FullName
        if ($null -ne $metadata -and $metadata.modId -eq $ModId) {
            $matchedBy += "mod metadata"
        }
    }
    catch {
        $metadata = $null
    }
    if ($matchedBy.Count -gt 0) {
        $matchedInstalled += [pscustomobject]@{
            path = $jar.FullName
            reason = ($matchedBy -join ", ")
            metadata = $metadata
        }
    }
}

Write-Host "Matched installed jars for removal because they identify as '$ModId':"
if ($matchedInstalled.Count -eq 0) {
    Write-Host "  none"
}
else {
    $matchedInstalled | ForEach-Object { Write-Host "  $($_.path) [$($_.reason)]" }
}

$deleted = @()
foreach ($match in $matchedInstalled) {
    Remove-Item -LiteralPath $match.path
    $deleted += $match.path
}

$targetJar = Join-Path $ModsDir $sourceJar.Name
Copy-Item -LiteralPath $sourceJar.FullName -Destination $targetJar

$sourceInfo = Get-Item -LiteralPath $sourceJar.FullName
$targetInfo = Get-Item -LiteralPath $targetJar
$sourceHash = Get-Sha256 -Path $sourceJar.FullName
$targetHash = Get-Sha256 -Path $targetJar
$installedMetadata = Get-JarMetadata -JarPath $targetJar

$remaining = @()
Get-ChildItem -LiteralPath $ModsDir -Filter "*.jar" -File | ForEach-Object {
    try {
        $metadata = Get-JarMetadata -JarPath $_.FullName
        if ($null -ne $metadata -and $metadata.modId -eq $ModId) {
            $remaining += $_.FullName
        }
    }
    catch {
    }
}

$report = [pscustomobject]@{
    modId = $ModId
    modName = $sourceMetadata.displayName
    version = $sourceMetadata.version
    buildDir = $BuildDir
    modsDir = $ModsDir
    sourceJar = $sourceJar.FullName
    installedJar = $targetJar
    sourceSize = $sourceInfo.Length
    installedSize = $targetInfo.Length
    sourceSha256 = $sourceHash
    installedSha256 = $targetHash
    hashMatch = ($sourceHash -eq $targetHash)
    metadata = $installedMetadata
    deletedOldJars = $deleted
    remainingJarsForMod = $remaining
    remainingInstalledJarCount = $remaining.Count
    runtimeDependenciesReport = if ($SkipRuntimeDependencies) { $null } else { Join-Path $RepoRoot "build\runtime-deps-report.json" }
}

$report | ConvertTo-Json -Depth 8 | Set-Content -LiteralPath $ReportPath -Encoding UTF8

if (-not $report.hashMatch) {
    throw "Installed jar hash does not match source jar."
}
if ($report.remainingInstalledJarCount -ne 1) {
    throw "Expected exactly one installed jar for '$ModId'; found $($report.remainingInstalledJarCount)."
}

$report | ConvertTo-Json -Depth 8
