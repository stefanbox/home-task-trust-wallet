# ============================================
# LIST TAB TEST - FINALNA VERZIJA
# Bazirano na rucnom testiranju
# ============================================

Write-Host "=== LIST TAB TEST ===" -ForegroundColor Cyan

# 1. Scroll na vrh
Write-Host "Scroll na vrh..." -ForegroundColor Gray
adb shell input swipe 540 800 540 1800 300
Start-Sleep -Milliseconds 300
adb shell input swipe 540 800 540 1800 300
Start-Sleep -Milliseconds 300
adb shell input swipe 540 800 540 1800 300
Start-Sleep -Milliseconds 500

# 2. Items 1-9
Write-Host "Items 1-9:" -ForegroundColor Yellow
adb shell input tap 540 697
Start-Sleep -Milliseconds 350
Write-Host "  1" -ForegroundColor Green
adb shell input tap 540 841
Start-Sleep -Milliseconds 350
Write-Host "  2" -ForegroundColor Green
adb shell input tap 540 985
Start-Sleep -Milliseconds 350
Write-Host "  3" -ForegroundColor Green
adb shell input tap 540 1129
Start-Sleep -Milliseconds 350
Write-Host "  4" -ForegroundColor Green
adb shell input tap 540 1273
Start-Sleep -Milliseconds 350
Write-Host "  5" -ForegroundColor Green
adb shell input tap 540 1417
Start-Sleep -Milliseconds 350
Write-Host "  6" -ForegroundColor Green
adb shell input tap 540 1561
Start-Sleep -Milliseconds 350
Write-Host "  7" -ForegroundColor Green
adb shell input tap 540 1705
Start-Sleep -Milliseconds 350
Write-Host "  8" -ForegroundColor Green
adb shell input tap 540 1849
Start-Sleep -Milliseconds 350
Write-Host "  9" -ForegroundColor Green

# 3. Mali scroll
Write-Host ""
Write-Host "--- Mali scroll ---" -ForegroundColor Gray
adb shell input swipe 540 1400 540 900 400
Start-Sleep -Milliseconds 600

# 4. Items 10-12 (VAZNO: nastavljamo DOLE, ne na vrhu!)
Write-Host "Items 10-12:" -ForegroundColor Yellow
adb shell input tap 540 1273
Start-Sleep -Milliseconds 350
Write-Host "  10" -ForegroundColor Green
adb shell input tap 540 1417
Start-Sleep -Milliseconds 350
Write-Host "  11" -ForegroundColor Green
adb shell input tap 540 1561
Start-Sleep -Milliseconds 350
Write-Host "  12" -ForegroundColor Green

# 5. Scroll do dna
Write-Host ""
Write-Host "--- Scroll do dna ---" -ForegroundColor Gray
adb shell input swipe 540 1600 540 500 400
Start-Sleep -Milliseconds 600

# 6. Items 13-14 (Item 12 je sada na vrhu, 13 je ispod)
Write-Host "Items 13-14:" -ForegroundColor Yellow
adb shell input tap 540 841
Start-Sleep -Milliseconds 350
Write-Host "  13" -ForegroundColor Green
adb shell input tap 540 985
Start-Sleep -Milliseconds 350
Write-Host "  14" -ForegroundColor Green

# 7. Skip Item 15 (BUG)
Write-Host "  15 - SKIP (BUG!)" -ForegroundColor Red

# 8. Item 16 + cekaj 7 sekundi
Write-Host "  Cekam 7 sekundi..." -ForegroundColor Gray
adb shell input tap 540 1273
Start-Sleep -Seconds 7
Write-Host "  16" -ForegroundColor Green

# 9. Items 17-18-20-19
Write-Host "Items 17-20:" -ForegroundColor Yellow
adb shell input tap 540 1417
Start-Sleep -Milliseconds 350
Write-Host "  17" -ForegroundColor Green
adb shell input tap 540 1561
Start-Sleep -Milliseconds 350
Write-Host "  18" -ForegroundColor Green
adb shell input tap 540 1705
Start-Sleep -Milliseconds 350
Write-Host "  20 (BUG - pre 19!)" -ForegroundColor Red
adb shell input tap 540 1849
Start-Sleep -Milliseconds 350
Write-Host "  19" -ForegroundColor Green

Write-Host ""
Write-Host "=== LIST TAB KOMPLETIRAN ===" -ForegroundColor Green
Write-Host "BUGS:" -ForegroundColor Yellow
Write-Host "  - Item 15: Ne reaguje na klik" -ForegroundColor Red
Write-Host "  - Item 20: Prikazan pre Item 19" -ForegroundColor Red
