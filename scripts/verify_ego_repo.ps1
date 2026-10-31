# Immersive EGO repository verification helper.
# Run from repository root:
#   powershell -ExecutionPolicy Bypass -File .\scripts\verify_ego_repo.ps1

$ErrorActionPreference = "Continue"

$RequiredFiles = @(
  "AGENTS.md",
  "TASKS.md",
  "LICENSE",
  "THIRD_PARTY_NOTICES.md",
  "README.md",
  "README_FR.md",
  "CHANGELOG.md",
  "docs\SOURCE_PROVENANCE.md",
  "docs\LICENSE_MATRIX.md",
  "docs\ASSET_POLICY.md",
  "docs\DEPENDENCY_AUDIT.md",
  "docs\DECISIONS.md",
  "docs\IMPLEMENTATION_LOG.md",
  "docs\CODEX_HANDOFF.md",
  "docs\ARCHITECTURE.md",
  "docs\CONFIG_REFERENCE.md",
  "docs\USER_GUIDE.md",
  "docs\APOTHIC_INTEGRATION.md",
  "docs\MARIESLIB_INTEGRATION.md",
  "docs\APOTHEOSIS_COMPAT.md",
  "docs\PERFORMANCE.md",
  "docs\TEST_PLAN.md",
  "docs\MODPACK_GUIDE.md",
  "docs\CURSEFORGE_DESCRIPTION.md",
  "docs\CREDITS.md"
)

Write-Host "Immersive EGO repo verification" -ForegroundColor Cyan
Write-Host "Working directory: $(Get-Location)"

$Missing = @()
foreach ($File in $RequiredFiles) {
  if (Test-Path $File) {
    Write-Host "[OK] $File" -ForegroundColor Green
  } else {
    Write-Host "[MISSING] $File" -ForegroundColor Red
    $Missing += $File
  }
}

Write-Host ""
Write-Host "Java version:" -ForegroundColor Cyan
try { java -version } catch { Write-Host "Java not found in PATH" -ForegroundColor Red }

Write-Host ""
if (Test-Path ".\gradlew.bat") {
  Write-Host "Gradle wrapper detected." -ForegroundColor Green
  Write-Host "Recommended checks:"
  Write-Host "  .\gradlew.bat --version"
  Write-Host "  .\gradlew.bat build --stacktrace"
  Write-Host "  .\gradlew.bat test --stacktrace"
} else {
  Write-Host "Gradle wrapper not found. Bootstrap the official NeoForge 1.21.1 project first." -ForegroundColor Yellow
}

Write-Host ""
if ($Missing.Count -eq 0) {
  Write-Host "All required governance/docs files are present." -ForegroundColor Green
  exit 0
} else {
  Write-Host "$($Missing.Count) required files missing." -ForegroundColor Red
  exit 1
}
