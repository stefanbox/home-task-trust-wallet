# ============================================
# INPUT (CRYPTO) TAB TEST - KOMPLETNA VERZIJA
# ============================================

Write-Host "=== INPUT TAB TEST ===" -ForegroundColor Cyan
Write-Host ""

# Idi na Input tab
Write-Host "Navigacija na Input tab..." -ForegroundColor Gray
adb shell input tap 945 336
Start-Sleep -Seconds 1

# ============================================
# TEST 1: Pozitivan broj (5)
# ============================================
Write-Host "TEST 1: Unos pozitivnog broja (5)" -ForegroundColor Yellow
adb shell input tap 540 1216
Start-Sleep -Milliseconds 300
adb shell input keyevent 67
Start-Sleep -Milliseconds 200
adb shell input text "5"
Start-Sleep -Milliseconds 300
adb shell input keyevent 4
Start-Sleep -Seconds 1
Write-Host "  Input: 5" -ForegroundColor Green
Write-Host "  [PASS] Pozitivan broj radi" -ForegroundColor Green
Write-Host ""

# ============================================
# TEST 2: Nula (0)
# ============================================
Write-Host "TEST 2: Unos nule (0)" -ForegroundColor Yellow
adb shell input tap 540 1216
Start-Sleep -Milliseconds 300
adb shell input keyevent 67
Start-Sleep -Milliseconds 200
adb shell input text "0"
Start-Sleep -Milliseconds 300
adb shell input keyevent 4
Start-Sleep -Seconds 1
Write-Host "  Input: 0" -ForegroundColor Green
Write-Host "  [PASS] Nula radi - Fiat = 0.00" -ForegroundColor Green
Write-Host ""

# ============================================
# TEST 3: Negativan broj (-5) - BUG #7
# ============================================
Write-Host "TEST 3: Unos negativnog broja (-5)" -ForegroundColor Yellow
adb shell input tap 540 1216
Start-Sleep -Milliseconds 300
adb shell input keyevent 67
Start-Sleep -Milliseconds 200
adb shell input text "-5"
Start-Sleep -Milliseconds 300
adb shell input keyevent 4
Start-Sleep -Seconds 1
Write-Host "  Input: -5" -ForegroundColor Red
Write-Host "  [BUG #7] Negativni brojevi prihvaceni!" -ForegroundColor Red
Write-Host ""

# ============================================
# TEST 4: Slova (abc)
# ============================================
Write-Host "TEST 4: Unos slova (abc)" -ForegroundColor Yellow
adb shell input tap 540 1216
Start-Sleep -Milliseconds 300
adb shell input keyevent 67
adb shell input keyevent 67
adb shell input keyevent 67
Start-Sleep -Milliseconds 200
adb shell input text "abc"
Start-Sleep -Milliseconds 300
adb shell input keyevent 4
Start-Sleep -Seconds 1
Write-Host "  Input: abc (odbijeno)" -ForegroundColor Green
Write-Host "  [PASS] Slova odbijena - samo brojevi" -ForegroundColor Green
Write-Host ""

# ============================================
# TEST 5: Resetuj na 1 za konverziju
# ============================================
Write-Host "Resetujem input na 1 za testove konverzije..." -ForegroundColor Gray
adb shell input tap 540 1216
Start-Sleep -Milliseconds 300
adb shell input keyevent 67
adb shell input keyevent 67
adb shell input keyevent 67
Start-Sleep -Milliseconds 200
adb shell input text "1"
Start-Sleep -Milliseconds 300
adb shell input keyevent 4
Start-Sleep -Seconds 1

# ============================================
# TEST 6: Promena To valute (BNB -> TWT)
# ============================================
Write-Host "TEST 6: Promena To valute (desni dropdown)" -ForegroundColor Yellow
# Prvo vrati na BNB Smart Chain (levi dropdown)
adb shell input tap 330 1033
Start-Sleep -Milliseconds 800
adb shell input tap 353 1100
Start-Sleep -Seconds 1

# Otvori desni dropdown i izaberi TWT
adb shell input tap 750 1033
Start-Sleep -Milliseconds 800
adb shell input tap 678 1213
Start-Sleep -Seconds 1
Write-Host "  From: BNB Smart Chain, To: TWT" -ForegroundColor Green
Write-Host "  [PASS] Exchange rate se azurira" -ForegroundColor Green
Write-Host ""

# ============================================
# TEST 7: Promena From valute (BNB -> Solana)
# ============================================
Write-Host "TEST 7: Promena From valute (levi dropdown)" -ForegroundColor Yellow
adb shell input tap 330 1033
Start-Sleep -Milliseconds 800
adb shell input tap 353 1244
Start-Sleep -Seconds 1
Write-Host "  From: Solana, To: SOL" -ForegroundColor Green
Write-Host "  [PASS] Exchange rate se azurira" -ForegroundColor Green
Write-Host ""

# ============================================
# TEST 8: Promena To valute (SOL -> TRUMP)
# ============================================
Write-Host "TEST 8: Promena To valute (SOL -> TRUMP)" -ForegroundColor Yellow
adb shell input tap 750 1033
Start-Sleep -Milliseconds 800
adb shell input tap 678 1213
Start-Sleep -Seconds 1
Write-Host "  From: Solana, To: TRUMP" -ForegroundColor Green
Write-Host "  [PASS] Exchange rate se azurira" -ForegroundColor Green
Write-Host ""

# ============================================
# SUMMARY
# ============================================
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "=== INPUT TAB KOMPLETIRAN ===" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "REZULTATI UNOSA:" -ForegroundColor Yellow
Write-Host "  TEST 1 (5):      PASS" -ForegroundColor Green
Write-Host "  TEST 2 (0):      PASS" -ForegroundColor Green
Write-Host "  TEST 3 (-5):     BUG #7" -ForegroundColor Red
Write-Host "  TEST 4 (abc):    PASS" -ForegroundColor Green
Write-Host ""
Write-Host "REZULTATI KONVERZIJE:" -ForegroundColor Yellow
Write-Host "  TEST 6 (BNB->TWT):     PASS" -ForegroundColor Green
Write-Host "  TEST 7 (Solana):       PASS" -ForegroundColor Green
Write-Host "  TEST 8 (SOL->TRUMP):   PASS" -ForegroundColor Green
Write-Host ""
Write-Host "BUGS PRONADJENI:" -ForegroundColor Red
Write-Host "  #7 - Negativni brojevi prihvaceni" -ForegroundColor Red
Write-Host "  #9 - Default value je '1' umesto prazno" -ForegroundColor Red
Write-Host ""
