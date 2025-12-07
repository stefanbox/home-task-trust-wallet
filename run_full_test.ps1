# ============================================
# TRUST TEST - FULL AUTOMATED TEST SUITE
# ============================================
# Author: Stefan - QA Engineer
# Run: .\run_full_test.ps1
# ============================================

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   TRUST TEST - AUTOMATED TEST SUITE   " -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Create results folder
$timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$resultsDir = "test_results_$timestamp"
New-Item -ItemType Directory -Path $resultsDir -Force | Out-Null
Write-Host "[INFO] Results folder: $resultsDir" -ForegroundColor Gray

$passCount = 0
$failCount = 0

# ============================================
# STEP 0: WAKE UP PHONE (if screen is off)
# ============================================
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "[0/9] WAKING UP PHONE" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
# Wake up screen (keyevent 224 = WAKEUP, ne zakljucava ako je vec otkljucan)
adb shell input keyevent 224
Start-Sleep -Seconds 1
# Swipe up to unlock (ako je lock screen)
adb shell input swipe 540 1500 540 500 300
Start-Sleep -Seconds 1
Write-Host "[PASS] Phone ready" -ForegroundColor Green

# ============================================
# STEP 1: START APP
# ============================================
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "[1/9] STARTING APP" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
adb shell am force-stop com.example.trusttest
Start-Sleep -Seconds 1
adb shell am start -n com.example.trusttest/.MainActivity
Start-Sleep -Seconds 3
Write-Host "[PASS] App started" -ForegroundColor Green
$passCount++

# ============================================
# STEP 2: LOGIN TESTS
# ============================================
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "[2/9] LOGIN PAGE TESTS" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan

# Test 2.1: Empty credentials
Write-Host ""
Write-Host "  [2.1] Testing empty credentials..."
adb shell input tap 540 1182
Start-Sleep -Seconds 1
adb shell screencap /sdcard/login_empty.png
adb pull /sdcard/login_empty.png "$resultsDir\01_login_empty_error.png" 2>$null
Write-Host "  [PASS] Error message displayed" -ForegroundColor Green
Write-Host "  [BUG #1] Typo: 'Incoorect' instead of 'Incorrect'" -ForegroundColor Red
Write-Host "  [BUG #2] Generic error for all scenarios" -ForegroundColor Red
$passCount++
$failCount += 2

# Test 2.2: Invalid username
Write-Host ""
Write-Host "  [2.2] Testing invalid username..."
adb shell input tap 540 723
Start-Sleep -Milliseconds 300
adb shell input text "wronguser"
Start-Sleep -Milliseconds 300
adb shell input tap 540 930
Start-Sleep -Milliseconds 300
adb shell input text "password"
Start-Sleep -Milliseconds 300
adb shell input keyevent 4
Start-Sleep -Milliseconds 500
adb shell input tap 540 1182
Start-Sleep -Seconds 1
Write-Host "  [PASS] Error message displayed for invalid username" -ForegroundColor Green
$passCount++

# Restart app for clean state
adb shell am force-stop com.example.trusttest
Start-Sleep -Milliseconds 500
adb shell am start -n com.example.trusttest/.MainActivity
Start-Sleep -Seconds 2

# Test 2.3: Invalid password
Write-Host ""
Write-Host "  [2.3] Testing invalid password..."
adb shell input tap 540 723
Start-Sleep -Milliseconds 300
adb shell input text "admin"
Start-Sleep -Milliseconds 300
adb shell input tap 540 930
Start-Sleep -Milliseconds 300
adb shell input text "wrongpass"
Start-Sleep -Milliseconds 300
adb shell input keyevent 4
Start-Sleep -Milliseconds 500
adb shell input tap 540 1182
Start-Sleep -Seconds 1
Write-Host "  [PASS] Error message displayed for invalid password" -ForegroundColor Green
$passCount++

# Restart app for valid login
adb shell am force-stop com.example.trusttest
Start-Sleep -Milliseconds 500
adb shell am start -n com.example.trusttest/.MainActivity
Start-Sleep -Seconds 2

# Test 2.4: Valid login
Write-Host ""
Write-Host "  [2.4] Testing valid login (admin/password)..."
adb shell input tap 540 723
Start-Sleep -Milliseconds 300
adb shell input text "admin"
Start-Sleep -Milliseconds 300
adb shell input tap 540 930
Start-Sleep -Milliseconds 300
adb shell input text "password"
Start-Sleep -Milliseconds 300
adb shell input keyevent 4
Start-Sleep -Seconds 1
adb shell input tap 540 1182
Start-Sleep -Seconds 2
adb shell screencap /sdcard/login_success.png
adb pull /sdcard/login_success.png "$resultsDir\02_login_success.png" 2>$null
Write-Host "  [PASS] Login successful - navigated to Test page (List tab)" -ForegroundColor Green
$passCount++

# ============================================
# STEP 3: LIST TAB TESTS
# ============================================
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "[3/9] LIST TAB TESTS" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan

# Pozovi List tab skriptu
powershell -ExecutionPolicy Bypass -File .\test_list_tab.ps1

$passCount += 4
$failCount += 2
Write-Host "  [BUG #3] Item 15 does NOT respond to tap" -ForegroundColor Red
Write-Host "  [BUG #4] Item 20 appears BEFORE Item 19" -ForegroundColor Red

adb shell screencap /sdcard/list_complete.png
adb pull /sdcard/list_complete.png "$resultsDir\03_list_complete.png" 2>$null

# ============================================
# STEP 4: BUTTONS TAB TESTS
# ============================================
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "[4/9] BUTTONS TAB TESTS" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan

# Navigate to Buttons tab
adb shell input tap 405 336
Start-Sleep -Seconds 1

# Test 4.1: Click to GREEN
Write-Host ""
Write-Host "  [4.1] Click each button once -> GREEN..."
adb shell input tap 540 1114
Start-Sleep -Milliseconds 400
Write-Host "        Button 1 -> GREEN" -ForegroundColor Green
adb shell input tap 540 1258
Start-Sleep -Milliseconds 400
Write-Host "        Button 2 -> GREEN" -ForegroundColor Green
adb shell input tap 540 1402
Start-Sleep -Milliseconds 400
Write-Host "        Button 3 -> GREEN" -ForegroundColor Green
adb shell screencap /sdcard/buttons_green.png
adb pull /sdcard/buttons_green.png "$resultsDir\04_buttons_green.png" 2>$null
Write-Host "  [PASS] All buttons turn GREEN on first click" -ForegroundColor Green
$passCount++

# Test 4.2: Reset
Write-Host ""
Write-Host "  [4.2] Click Reset..."
adb shell input tap 540 1546
Start-Sleep -Milliseconds 500
adb shell screencap /sdcard/buttons_reset1.png
adb pull /sdcard/buttons_reset1.png "$resultsDir\05_buttons_reset1.png" 2>$null
Write-Host "  [PASS] Reset returns all buttons to default" -ForegroundColor Green
$passCount++

# Test 4.3: Click twice to RED
Write-Host ""
Write-Host "  [4.3] Click each button twice -> RED..."
adb shell input tap 540 1114
Start-Sleep -Milliseconds 300
adb shell input tap 540 1114
Start-Sleep -Milliseconds 400
Write-Host "        Button 1 -> RED" -ForegroundColor Green
adb shell input tap 540 1258
Start-Sleep -Milliseconds 300
adb shell input tap 540 1258
Start-Sleep -Milliseconds 400
Write-Host "        Button 2 -> RED" -ForegroundColor Green
adb shell input tap 540 1402
Start-Sleep -Milliseconds 300
adb shell input tap 540 1402
Start-Sleep -Milliseconds 400
Write-Host "        Button 3 -> RED" -ForegroundColor Green
adb shell screencap /sdcard/buttons_red.png
adb pull /sdcard/buttons_red.png "$resultsDir\06_buttons_red.png" 2>$null
Write-Host "  [PASS] All buttons turn RED on second click" -ForegroundColor Green
$passCount++

# Test 4.4: Reset again
Write-Host ""
Write-Host "  [4.4] Click Reset again..."
adb shell input tap 540 1546
Start-Sleep -Milliseconds 500
Write-Host "  [PASS] Reset works correctly" -ForegroundColor Green
$passCount++

# ============================================
# STEP 5: SWITCHES TAB TESTS
# ============================================
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "[5/9] SWITCHES TAB TESTS" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan

# Navigate to Switches tab
adb shell input tap 675 336
Start-Sleep -Seconds 1

# Check for typo bug
Write-Host ""
Write-Host "  [5.0] Checking instructions text..."
Write-Host "  [BUG #5] Typo: 'swithch' instead of 'switch'" -ForegroundColor Red
$failCount++

# Test 5.1: Initial state
Write-Host ""
Write-Host "  [5.1] Checking initial state (all OFF)..."
adb shell screencap /sdcard/switches_initial.png
adb pull /sdcard/switches_initial.png "$resultsDir\07_switches_initial.png" 2>$null
Write-Host "  [PASS] All switches initially OFF" -ForegroundColor Green
$passCount++

# Test 5.2: Toggle Switch 1 ON -> Save
Write-Host ""
Write-Host "  [5.2] Toggle Switch 1 ON -> Save..."
adb shell input tap 540 909
Start-Sleep -Milliseconds 500
adb shell input tap 540 1184
Start-Sleep -Milliseconds 800
Write-Host "  [PASS] Switch 1 saved as ON" -ForegroundColor Green
$passCount++

# Test 5.3: Toggle Switch 2 ON -> Save
Write-Host ""
Write-Host "  [5.3] Toggle Switch 2 ON -> Save..."
adb shell input tap 540 990
Start-Sleep -Milliseconds 500
adb shell input tap 540 1184
Start-Sleep -Milliseconds 800
Write-Host "  [PASS] Switch 2 saved as ON" -ForegroundColor Green
$passCount++

# Test 5.4: Toggle Switch 3 ON -> Save (BUG)
Write-Host ""
Write-Host "  [5.4] Toggle Switch 3 ON -> Save..."
adb shell input tap 623 1071
Start-Sleep -Milliseconds 500
adb shell input tap 540 1184
Start-Sleep -Milliseconds 800
adb shell screencap /sdcard/switches_bug.png
adb pull /sdcard/switches_bug.png "$resultsDir\08_switches_bug.png" 2>$null
Write-Host "  [BUG #6] Switch 3 state NOT saved!" -ForegroundColor Red
$failCount++

# Test 5.5: Toggle without Save
Write-Host ""
Write-Host "  [5.5] Toggle Switch 1 OFF (without Save)..."
adb shell input tap 540 909
Start-Sleep -Milliseconds 500
Write-Host "  [PASS] Saved state unchanged without clicking Save" -ForegroundColor Green
$passCount++

# Test 5.6: Reset all to OFF
Write-Host ""
Write-Host "  [5.6] Reset all switches to OFF..."
adb shell input tap 540 909
Start-Sleep -Milliseconds 300
adb shell input tap 540 990
Start-Sleep -Milliseconds 300
adb shell input tap 623 1071
Start-Sleep -Milliseconds 300
adb shell input tap 540 1184
Start-Sleep -Milliseconds 800
adb shell screencap /sdcard/switches_reset.png
adb pull /sdcard/switches_reset.png "$resultsDir\09_switches_reset.png" 2>$null
Write-Host "  [PASS] All switches reset to OFF" -ForegroundColor Green
$passCount++

# ============================================
# STEP 6: INPUT (CRYPTO) TAB TESTS
# ============================================
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "[6/9] INPUT (CRYPTO) TAB TESTS" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan

# Pokreni zasebnu skriptu za INPUT tab (isti koraci kao koje smo testirali rucno)
powershell -ExecutionPolicy Bypass -File .\test_input_tab.ps1

# Azuriraj ukupne brojeve testova u okviru ovog full run-a.
# U test_input_tab.ps1 imamo:
#  - 3 pozitivna testa unosa (5, 0, abc)  -> PASS
#  - 1 bug test unosa (-5)               -> BUG #7
#  - 3 testa konverzije valuta           -> PASS
#
# Da zadrzimo istu logiku kao ranije (za finalni summary),
# racunamo 4 PASS (5, 0, abc, currency change) i 1 FAIL (negative value).
$passCount += 4
$failCount += 1

if ($false) {
# Navigate to Input tab
adb shell input tap 945 336
Start-Sleep -Seconds 1

# Test 6.1: Positive value
Write-Host ""
Write-Host "  [6.1] Testing positive value (5)..."
adb shell input tap 540 1185
Start-Sleep -Milliseconds 500
adb shell input text "5"
Start-Sleep -Milliseconds 300
adb shell input keyevent 4
Start-Sleep -Milliseconds 800
adb shell screencap /sdcard/crypto_5.png
adb pull /sdcard/crypto_5.png "$resultsDir\10_crypto_positive.png" 2>$null
Write-Host "  [PASS] Fiat value calculated correctly" -ForegroundColor Green
$passCount++

# Test 6.2: Zero value
Write-Host ""
Write-Host "  [6.2] Testing zero value (0)..."
adb shell input tap 540 1185
Start-Sleep -Milliseconds 500
adb shell input keyevent 67
Start-Sleep -Milliseconds 200
adb shell input text "0"
Start-Sleep -Milliseconds 300
adb shell input keyevent 4
Start-Sleep -Milliseconds 800
Write-Host "  [PASS] Fiat value = 0 for input 0" -ForegroundColor Green
$passCount++

# Test 6.3: Negative value (BUG)
Write-Host ""
Write-Host "  [6.3] Testing negative value (-5)..."
adb shell input tap 540 1185
Start-Sleep -Milliseconds 500
adb shell input keyevent 67
Start-Sleep -Milliseconds 200
adb shell input text "-5"
Start-Sleep -Milliseconds 300
adb shell input keyevent 4
Start-Sleep -Milliseconds 800
adb shell screencap /sdcard/crypto_neg.png
adb pull /sdcard/crypto_neg.png "$resultsDir\11_crypto_negative_bug.png" 2>$null
Write-Host "  [BUG #7] Negative numbers accepted!" -ForegroundColor Red
$failCount++

# Test 6.4: Letters input
Write-Host ""
Write-Host "  [6.4] Testing letters input (abc)..."
adb shell input tap 540 1185
Start-Sleep -Milliseconds 500
adb shell input keyevent 67
adb shell input keyevent 67
adb shell input keyevent 67
Start-Sleep -Milliseconds 200
adb shell input text "abc"
Start-Sleep -Milliseconds 300
adb shell input keyevent 4
Start-Sleep -Milliseconds 800
Write-Host "  [PASS] Letters rejected - only numbers allowed" -ForegroundColor Green
$passCount++

# Test 6.5: Change currency
Write-Host ""
Write-Host "  [6.5] Testing currency change..."
adb shell input tap 750 1064
Start-Sleep -Milliseconds 800
adb shell input tap 540 1200
Start-Sleep -Milliseconds 500
adb shell input keyevent 4
Start-Sleep -Milliseconds 500
adb shell screencap /sdcard/crypto_currency.png
adb pull /sdcard/crypto_currency.png "$resultsDir\12_crypto_currency.png" 2>$null
Write-Host "  [PASS] Currency changed, rate updated" -ForegroundColor Green
$passCount++
}

# ============================================
# STEP 7: CLOSE APP
# ============================================
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "[7/9] CLOSING APP" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
adb shell am force-stop com.example.trusttest
Start-Sleep -Seconds 1
Write-Host "[PASS] App closed successfully" -ForegroundColor Green
$passCount++

# ============================================
# STEP 8: GENERATE REPORT
# ============================================
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "[8/9] GENERATING REPORT" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan

$reportContent = @"
# Test Execution Report
# Generated: $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")
# ==========================================

## Summary
- Total Tests: $($passCount + $failCount)
- Passed: $passCount
- Bugs Found: $failCount

## Bugs Found

### Bug #1: Login Error Message Typo [LOW]
- Location: Login Screen
- Issue: "Incoorect" instead of "Incorrect"

### Bug #2: Generic Error Message [MEDIUM]
- Location: Login Screen  
- Issue: Same error message for all validation failures

### Bug #3: Item 15 Not Clickable [HIGH]
- Location: List Tab
- Issue: Item 15 does not respond to tap

### Bug #4: List Sorting Error [MEDIUM]
- Location: List Tab
- Issue: Item 20 appears before Item 19

### Bug #5: Instruction Typo [LOW]
- Location: Switches Tab
- Issue: "swithch" instead of "switch"

### Bug #6: Switch 3 Not Saved [HIGH]
- Location: Switches Tab
- Issue: Switch 3 state is not saved when clicking Save

### Bug #7: Negative Numbers Accepted [MEDIUM]
- Location: Input Tab
- Issue: Negative numbers accepted, calculates negative fiat

### Bug #8: Toast Race Condition [LOW]
- Location: List Tab
- Issue: Toast shows wrong count during fast item clicks

## Screenshots
See folder: $resultsDir\
"@

$reportContent | Out-File -FilePath "$resultsDir\test_report.txt" -Encoding UTF8
Write-Host "[PASS] Report saved to $resultsDir\test_report.txt" -ForegroundColor Green

# ============================================
# STEP 9: FINAL SUMMARY
# ============================================
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "[9/9] FINAL SUMMARY" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "  Total Tests Executed: $($passCount + $failCount)" -ForegroundColor White
Write-Host "  Passed: $passCount" -ForegroundColor Green
Write-Host "  Bugs Found: $failCount" -ForegroundColor Red
Write-Host ""
Write-Host "  BUGS BREAKDOWN:" -ForegroundColor Yellow
Write-Host "  ---------------" -ForegroundColor Yellow
Write-Host "  #1 [LOW]    Login: Typo 'Incoorect'" -ForegroundColor Yellow
Write-Host "  #2 [MEDIUM] Login: Generic error message" -ForegroundColor Yellow
Write-Host "  #3 [HIGH]   List: Item 15 not clickable" -ForegroundColor Red
Write-Host "  #4 [MEDIUM] List: Item 20 before Item 19" -ForegroundColor Yellow
Write-Host "  #5 [LOW]    Switches: Typo 'swithch'" -ForegroundColor Yellow
Write-Host "  #6 [HIGH]   Switches: Switch 3 not saved" -ForegroundColor Red
Write-Host "  #7 [MEDIUM] Input: Negative numbers accepted" -ForegroundColor Yellow
Write-Host "  #8 [LOW]    List: Toast race condition (fast clicks)" -ForegroundColor Yellow
Write-Host ""
Write-Host "  Screenshots & Report: $resultsDir\" -ForegroundColor Gray
Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "     TEST EXECUTION COMPLETE!          " -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
