param(
    [string]$ModsDir = "C:\Users\Emmanuel Tremblay\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods",
    [string]$DownloadDir = ""
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$RepoRoot = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
if ([string]::IsNullOrWhiteSpace($DownloadDir)) {
    $DownloadDir = Join-Path $RepoRoot "build\runtime-deps-downloads"
}

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

$ModsDir = Resolve-SafeModsDirectory -Path $ModsDir
New-Item -ItemType Directory -Force -Path $DownloadDir | Out-Null
$DownloadDir = (Resolve-Path -LiteralPath $DownloadDir).Path
$ReportPath = Join-Path $RepoRoot "build\runtime-deps-report.json"

Add-Type -AssemblyName System.IO.Compression.FileSystem

$Dependencies = @(
    [pscustomobject]@{
        modId = "apothic_attributes"
        fileName = "ApothicAttributes-1.21.1-2.9.1.jar"
        url = "https://maven.shadowsoffire.dev/releases/dev/shadowsoffire/ApothicAttributes/1.21.1-2.9.1/ApothicAttributes-1.21.1-2.9.1.jar"
        sha256 = "6e3dd73d01c1784f4d1293126e3020470887456cd0d246a4c44a0fc1a244e381"
        filenamePatterns = @("ApothicAttributes-*.jar", "apothic_attributes*.jar")
    },
    [pscustomobject]@{
        modId = "placebo"
        fileName = "Placebo-1.21.1-9.9.1.jar"
        url = "https://maven.shadowsoffire.dev/releases/dev/shadowsoffire/Placebo/1.21.1-9.9.1/Placebo-1.21.1-9.9.1.jar"
        sha256 = "42c90bc43800558b9322bd60faa9011d6694e66a87b7e6b3b9f51fa735b527ea"
        filenamePatterns = @("Placebo-*.jar", "placebo*.jar")
    },
    [pscustomobject]@{
        modId = "marieslib"
        fileName = "marieslib-0.1.0-beta.5.jar"
        url = "https://cdn.modrinth.com/data/VA0Qgf4B/versions/OfkXJ5sM/marieslib-0.1.0-beta.5.jar"
        sha256 = "97b1a0c2375058307957cfa6f23cadf14e5fced0452b9d7b0262cdab85126593"
        filenamePatterns = @("marieslib-*.jar")
    },
    [pscustomobject]@{
        modId = "curios"
        fileName = "curios-neoforge-9.5.1+1.21.1.jar"
        url = "https://maven.theillusivec4.top/top/theillusivec4/curios/curios-neoforge/9.5.1+1.21.1/curios-neoforge-9.5.1+1.21.1.jar"
        sha256 = "a45df2125c26219974aba7507ffc9afe7b83acc941a386af3faacb1cc0056fde"
        filenamePatterns = @("curios-neoforge-*.jar", "curios*.jar")
    },
    [pscustomobject]@{
        modId = "cloth_config"
        fileName = "cloth-config-15.0.140-neoforge.jar"
        url = "https://cdn.modrinth.com/data/9s6osm5g/versions/izKINKFg/cloth-config-15.0.140-neoforge.jar"
        sha256 = "65e722e0d98431a07c45f8bdd8d529a217cc8c175fde1740248bd5c1b4f3c0d4"
        filenamePatterns = @("cloth-config-*.jar", "cloth_config*.jar")
    }
)

function Get-Sha256 {
    param([Parameter(Mandatory = $true)][string]$Path)
    (Get-FileHash -LiteralPath $Path -Algorithm SHA256).Hash.ToLowerInvariant()
}

function Get-JarModId {
    param([Parameter(Mandatory = $true)][string]$JarPath)

    $zip = [System.IO.Compression.ZipFile]::OpenRead($JarPath)
    try {
        $entry = $zip.Entries |
            Where-Object { $_.FullName -eq "META-INF/neoforge.mods.toml" -or $_.FullName -eq "META-INF/mods.toml" -or $_.FullName -eq "fabric.mod.json" } |
            Select-Object -First 1
        if ($null -eq $entry) {
            return $null
        }
        $reader = [System.IO.StreamReader]::new($entry.Open())
        try {
            $content = $reader.ReadToEnd()
        }
        finally {
            $reader.Dispose()
        }

        $tomlMatch = [regex]::Match($content, 'modId\s*=\s*"([^"]+)"')
        if ($tomlMatch.Success) {
            return $tomlMatch.Groups[1].Value
        }
        $jsonMatch = [regex]::Match($content, '"id"\s*:\s*"([^"]+)"')
        if ($jsonMatch.Success) {
            return $jsonMatch.Groups[1].Value
        }
        return $null
    }
    finally {
        $zip.Dispose()
    }
}

function Find-InstalledDependencyJars {
    param(
        [Parameter(Mandatory = $true)][string]$ModId,
        [Parameter(Mandatory = $true)][string[]]$FilenamePatterns
    )

    $matches = @()
    Get-ChildItem -LiteralPath $ModsDir -Filter "*.jar" -File | ForEach-Object {
        $jar = $_
        $matchedBy = @()
        foreach ($pattern in $FilenamePatterns) {
            if ($jar.Name -like $pattern) {
                $matchedBy += "filename:$pattern"
            }
        }
        try {
            $jarModId = Get-JarModId -JarPath $jar.FullName
            if ($jarModId -eq $ModId) {
                $matchedBy += "mod metadata"
            }
        }
        catch {
        }
        if ($matchedBy.Count -gt 0) {
            $matches += [pscustomobject]@{
                path = $jar.FullName
                reason = ($matchedBy -join ", ")
            }
        }
    }
    return $matches
}

$results = @()
foreach ($dependency in $Dependencies) {
    $sourcePath = Join-Path $DownloadDir $dependency.fileName
    $sourceExists = Test-Path -LiteralPath $sourcePath
    if ($sourceExists -and (Get-Sha256 -Path $sourcePath) -ne $dependency.sha256) {
        Remove-Item -LiteralPath $sourcePath
        $sourceExists = $false
    }
    if (-not $sourceExists) {
        Write-Host "Downloading $($dependency.fileName)"
        Invoke-WebRequest -Uri $dependency.url -OutFile $sourcePath
    }

    $sourceHash = Get-Sha256 -Path $sourcePath
    if ($sourceHash -ne $dependency.sha256) {
        throw "Downloaded dependency hash mismatch for $($dependency.fileName): $sourceHash"
    }

    $installedMatches = @(Find-InstalledDependencyJars -ModId $dependency.modId -FilenamePatterns $dependency.filenamePatterns)
    $validInstalled = @(
        $installedMatches | Where-Object {
            (Test-Path -LiteralPath $_.path) -and (Get-Sha256 -Path $_.path) -eq $dependency.sha256
        }
    )

    $deleted = @()
    if ($validInstalled.Count -eq 1 -and $installedMatches.Count -eq 1) {
        $targetPath = $validInstalled[0].path
        Write-Host "Dependency already installed: $targetPath"
    }
    else {
        Write-Host "Matched installed jars for dependency '$($dependency.modId)':"
        if ($installedMatches.Count -eq 0) {
            Write-Host "  none"
        }
        else {
            foreach ($match in $installedMatches) {
                Write-Host "  $($match.path) [$($match.reason)]"
                Remove-Item -LiteralPath $match.path
                $deleted += $match.path
            }
        }
        $targetPath = Join-Path $ModsDir $dependency.fileName
        Copy-Item -LiteralPath $sourcePath -Destination $targetPath
    }

    $remainingMatches = @(Find-InstalledDependencyJars -ModId $dependency.modId -FilenamePatterns $dependency.filenamePatterns)
    $remainingValid = @($remainingMatches | Where-Object { (Get-Sha256 -Path $_.path) -eq $dependency.sha256 })
    if ($remainingMatches.Count -ne 1 -or $remainingValid.Count -ne 1) {
        throw "Expected exactly one installed dependency jar for '$($dependency.modId)'; found $($remainingMatches.Count)."
    }

    $installedHash = Get-Sha256 -Path $remainingMatches[0].path
    $results += [pscustomobject]@{
        modId = $dependency.modId
        source = $sourcePath
        installed = $remainingMatches[0].path
        sourceSha256 = $sourceHash
        installedSha256 = $installedHash
        hashMatch = ($sourceHash -eq $installedHash)
        deletedOldJars = $deleted
        remainingInstalledJarCount = $remainingMatches.Count
    }
}

$report = [pscustomobject]@{
    modsDir = $ModsDir
    downloadDir = $DownloadDir
    dependencies = $results
}

$report | ConvertTo-Json -Depth 8 | Set-Content -LiteralPath $ReportPath -Encoding UTF8
$report | ConvertTo-Json -Depth 8
