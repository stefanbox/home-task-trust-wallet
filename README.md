# Trust Wallet Mobile Automation Framework

A lightweight mobile automation framework built with Java and Appium for testing the Trust Test Android demo application.

## 📋 Table of Contents

- [Framework Overview](#framework-overview)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Running Tests](#running-tests)
- [Test Coverage](#test-coverage)
- [Debugging & Artifacts](#debugging--artifacts)
- [Stability Decisions](#stability-decisions)
- [Known Limitations](#known-limitations)
- [Bugs Found](#bugs-found)

---

## 🏗️ Framework Overview

This framework implements the **Page Object Model (POM)** design pattern for maintainability and scalability. It provides:

- **Clean separation** between test logic and page interactions
- **Centralized configuration** via properties file
- **Automatic screenshots** on test failures
- **Detailed logging** for debugging
- **TestNG integration** for test organization and reporting

### Key Features

| Feature | Implementation |
|---------|---------------|
| Design Pattern | Page Object Model |
| Test Framework | TestNG 7.8.0 |
| Mobile Driver | Appium Java Client 9.3.0 |
| Reporting | ExtentReports + Console Logs |
| Screenshots | Automatic on failure |
| Configuration | External properties file |

---

## 📁 Project Structure

```
trust-wallet-automation/
├── app/
│   └── trust_test.apk           # Android APK file
├── src/
│   ├── main/java/
│   │   ├── config/
│   │   │   ├── AppConfig.java   # Configuration loader
│   │   │   └── DriverManager.java# Driver singleton
│   │   ├── pages/
│   │   │   ├── BasePage.java    # Base page with common methods
│   │   │   ├── LoginPage.java   # Login screen interactions
│   │   │   └── TestPage.java    # Main test screen (all tabs)
│   │   └── utils/
│   │       └── TestListener.java# Screenshot & logging listener
│   └── test/
│       ├── java/tests/
│       │   ├── BaseTest.java    # Base test class
│       │   ├── LoginTest.java   # Login screen tests
│       │   ├── ListTest.java    # List tab tests
│       │   ├── ButtonsTest.java # Buttons tab tests
│       │   ├── SwitchesTest.java# Switches tab tests
│       │   └── CryptoTest.java  # Crypto/Input tab tests
│       └── resources/
│           └── config.properties# Test configuration
├── test-output/
│   └── screenshots/             # Failure screenshots
├── pom.xml                      # Maven dependencies
├── testng.xml                   # TestNG suite configuration
└── README.md
```

---

## ⚙️ Prerequisites

Before running tests, ensure you have the following installed:

| Tool | Version | Check Command |
|------|---------|---------------|
| Java JDK | 1.8+ | `java -version` |
| Maven | 3.6+ | `mvn -version` |
| Node.js | 14+ | `node -v` |
| Appium | 2.0+ | `appium -v` |
| Android SDK | - | `adb version` |

### Environment Variables

```bash
ANDROID_HOME = C:\Users\<user>\AppData\Local\Android\Sdk
PATH += %ANDROID_HOME%\platform-tools
```

---

## 🚀 Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/stefanbox/home-task-trust-wallet.git
cd home-task-trust-wallet
```

### 2. Install Dependencies

```bash
mvn clean install -DskipTests
```

### 3. Place the APK

Copy `trust_test.apk` to the `app/` directory.

### 4. Connect Android Device

```bash
# Enable USB debugging on your phone
# Connect via USB cable
adb devices  # Should show your device
```

### 5. Update Configuration (if needed)

Edit `src/test/resources/config.properties`:

```properties
device.name=Your Device Name
platform.version=8.0
device.udid=your_device_udid  # Optional: run 'adb devices' to get this
```

### 6. Start Appium Server

```bash
appium
```

---

## ▶️ Running Tests

### Run All Tests (One Command)

```bash
mvn clean test
```

### Run Specific Test Class

```bash
mvn test -Dtest=LoginTest
mvn test -Dtest=ListTest
mvn test -Dtest=ButtonsTest
mvn test -Dtest=SwitchesTest
mvn test -Dtest=CryptoTest
```

### Run with TestNG XML

```bash
mvn test -DsuiteXmlFile=testng.xml
```

---

## 📱 App Structure

The Trust Test app has the following screen flow:

```
┌─────────────────────────────────────────────────────────────┐
│                      LOGIN PAGE                              │
│  - Username input                                           │
│  - Password input                                           │
│  - Submit button                                            │
│  - Credentials: admin / password                            │
└──────────────────────────┬──────────────────────────────────┘
                           │ (after successful login)
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                      TEST PAGE                               │
│  (Main page with 4 tabs)                                    │
│                                                             │
│  ┌─────────┬─────────┬──────────┬─────────┐                │
│  │  LIST   │ BUTTONS │ SWITCHES │  INPUT  │                │
│  │(default)│         │          │ (Crypto)│                │
│  └─────────┴─────────┴──────────┴─────────┘                │
│                                                             │
│  List Tab: Items 1-20 (scrollable list)                    │
│  Buttons Tab: 3 color-cycling buttons + Reset              │
│  Switches Tab: 3 toggle switches + Save                    │
│  Input Tab: Crypto converter with spinners                 │
└─────────────────────────────────────────────────────────────┘
```

**Note:** After login, user lands on "Test" page with **List** tab selected by default.

---

## 🧪 Test Coverage

### Login Screen Tests (LoginTest.java)
| Test | Description |
|------|-------------|
| testLoginScreenDisplayed | Verifies login screen appears on launch |
| testLogoDisplayed | Verifies logo is visible |
| testValidLogin | Tests successful login with admin/password |
| testInvalidUsername | Tests rejection of wrong username |
| testInvalidPassword | Tests rejection of wrong password |
| testEmptyCredentials | Tests submission with empty fields |

### List Tab Tests (ListTest.java)
| Test | Description |
|------|-------------|
| testListItemsDisplayed | Verifies list shows items |
| testItem1Visible | Verifies Item 1 visible without scroll |
| testScrollDown | Tests scrolling to Item 15 |
| testScrollUp | Tests scrolling back to top |
| testClickListItem | Tests item click interaction |
| testScrollThroughMultipleItems | Tests scrolling to items 5, 10, 15 |

### Buttons Tab Tests (ButtonsTest.java)
| Test | Description |
|------|-------------|
| testButtonsDisplayed | Verifies all 3 buttons visible |
| testResetButtonDisplayed | Verifies Reset button exists |
| testClickButton1/2/3 | Tests each button click |
| testResetButtonStates | Tests reset functionality |
| testButtonColorCycle | Tests color change (blue→green→red) |
| testAllButtonsSequence | Tests clicking all buttons in order |

### Switches Tab Tests (SwitchesTest.java)
| Test | Description |
|------|-------------|
| testSaveButtonDisplayed | Verifies Save button exists |
| testToggleSwitch1/2/3 | Tests each switch toggle |
| testAllSwitchesOn | Tests toggling all switches |
| testSavePersistsStates | Verifies Save persists state |
| testSwitchStatusPattern | Validates ON/OFF display format |

### Crypto Tab Tests (CryptoTest.java)
| Test | Description |
|------|-------------|
| testCryptoTabDisplayed | Verifies crypto tab loads |
| testEnterAmount | Tests amount input |
| testConversionToUSD | Verifies USD conversion result |
| testDifferentAmounts | Tests different values produce different results |
| testSelectNetwork | Tests network dropdown |
| testSelectToken | Tests token dropdown |
| testCompleteConversionWorkflow | End-to-end conversion test |
| testDecimalAmount | Tests decimal input (0.5) |
| testFiatValueDisplay | Verifies fiat value display |

---

## 🔍 Debugging & Artifacts

### When Something Fails, Look Here:

1. **Console Output**
   - Detailed logs with ✅ ❌ ⏭ indicators
   - Stack traces for failures
   - Test duration metrics

2. **Screenshots**
   ```
   test-output/screenshots/
   └── testMethodName_20241204_143022.png
   ```
   - Automatically captured on failure
   - Named with test name + timestamp

3. **TestNG Reports**
   ```
   target/surefire-reports/
   ├── index.html          # HTML report
   ├── emailable-report.html
   └── testng-results.xml  # XML results
   ```

4. **Maven Logs**
   ```bash
   mvn test -X  # Debug mode for verbose output
   ```

### Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| Device not found | Run `adb devices`, check USB debugging |
| Appium connection failed | Ensure Appium server is running on port 4723 |
| Element not found | Check locators match current app version |
| Timeout errors | Increase wait times in config.properties |

---

## 🛡️ Stability Decisions

### 1. Explicit Waits Over Implicit
- Used `WebDriverWait` for element visibility
- Configurable timeout via properties file
- Prevents flaky tests from timing issues

### 2. Page Object Model
- Locators centralized in page classes
- Easy maintenance when app UI changes
- Test methods read like user actions

### 3. Singleton Driver Pattern
- Single driver instance per test session
- Proper cleanup in `@AfterClass`
- Prevents memory leaks

### 4. Retry Logic
- Flexible locator strategies (ID → UIAutomator)
- Helper methods handle common wait scenarios

### 5. Screenshot on Failure
- Automatic capture via TestListener
- Timestamped filenames prevent overwrites
- Critical for debugging CI/CD failures

---

## ⚠️ Known Limitations

1. **Element Locators**: Locators are based on common Android patterns. If the app uses custom resource IDs, they may need adjustment after inspecting with Appium Inspector.

2. **Color Verification**: Button color cycling test verifies functionality but doesn't validate actual colors (would require image comparison or accessing view properties).

3. **Network-Dependent Tests**: Crypto conversion tests depend on API responses; may need mocking for consistent results in CI/CD.

4. **Single Device**: Currently configured for one device. Parallel execution would require Grid setup.

5. **Android Only**: Framework targets Android with UiAutomator2. iOS would need XCUITest driver and separate locators.

---

## 🐞 Bugs Found

### Bug #1: Error Message Typo - "Incoorect" instead of "Incorrect"
- **Location**: Login Screen
- **Steps to Reproduce**: 
  1. Open the app
  2. Enter invalid credentials or leave fields empty
  3. Click Submit
- **Expected**: Error message "Incorrect credentials. Please try again."
- **Actual**: Error message shows "Incoorect credentials. Please try again." (double 'o')
- **Impact**: Unprofessional appearance, suggests lack of QA review
- **Severity**: Low (cosmetic)

### Bug #2: Generic Error Message for All Validation Failures
- **Location**: Login Screen
- **Steps to Reproduce**: 
  1. Submit with empty fields → "Incoorect credentials..."
  2. Submit with wrong username → "Incoorect credentials..."
  3. Submit with wrong password → "Incoorect credentials..."
- **Expected**: Specific error messages like "Username is required", "Invalid password", etc.
- **Actual**: Same generic message for all error scenarios
- **Impact**: Poor user experience - users don't know what specifically is wrong
- **Severity**: Medium (UX issue)

### Bug #3: Item 15 Does Not Respond to Tap
- **Location**: List Tab
- **Steps to Reproduce**: 
  1. Login with admin/password
  2. On List tab, tap on Item 14 → shows highlight + toast "Selected -> Item 14"
  3. Tap on Item 15 → **NOTHING HAPPENS**
  4. Tap on Item 16 → shows highlight + toast "Selected -> Item 16"
- **Expected**: Item 15 should highlight and show toast "Selected -> Item 15"
- **Actual**: No visual feedback, no toast message, item does not respond
- **Impact**: Functional bug - users cannot interact with Item 15
- **Severity**: High (functional)
- **Evidence**: Screenshots `tap14.png`, `tap15.png`, `tap16.png` showing the difference

### Bug #4: List Sorting Error - Item 20 Before Item 19
- **Location**: List Tab
- **Steps to Reproduce**: 
  1. Login and go to List tab
  2. Scroll down to the bottom of the list
- **Expected**: Items in order: ...Item 17 → Item 18 → Item 19 → Item 20
- **Actual**: Items in wrong order: ...Item 17 → Item 18 → Item 20 → Item 19
- **Impact**: Data integrity issue, incorrect sorting logic
- **Severity**: Medium (data/logic error)
- **Evidence**: UI dump and screenshots confirm Item 20 appears at position before Item 19

### Bug #5: Typo in Switches Tab Instructions - "swithch" instead of "switch"
- **Location**: Switches Tab
- **Steps to Reproduce**: 
  1. Login with admin/password
  2. Navigate to Switches tab
  3. Read the instruction text at the top
- **Expected**: "Toggle any **switch** and save the state..."
- **Actual**: "Toggle any **swithch** and save the state..." (extra 'h')
- **Impact**: Unprofessional appearance, suggests lack of QA review
- **Severity**: Low (cosmetic/typo)

### Bug #6: Save Button Does Not Save Switch 3 State
- **Location**: Switches Tab
- **Steps to Reproduce**: 
  1. Login with admin/password
  2. Navigate to Switches tab
  3. Toggle Switch 1 to ON → Save → Works! (shows "Switch 1: ON")
  4. Toggle Switch 2 to ON → Save → Works! (shows "Switch 2: ON")
  5. Toggle Switch 3 to ON → Save → **FAILS!** (still shows "Switch 3: OFF")
- **Expected**: Saved state should update to show "Switch 3: ON"
- **Actual**: Saved state remains "Switch 3: OFF" even though Switch 3 is visibly ON (checked="true")
- **Impact**: Critical - Switch 3 state cannot be saved, partial functionality broken
- **Severity**: High (functional)
- **Evidence**: 
  - UI dump shows switch3 checked="true", text="ON"
  - saveStateText shows "Switch 1: ON, Switch 2: ON, Switch 3: OFF"
  - Multiple Save clicks do not fix the issue

### Bug #7: Crypto Converter Accepts Negative Numbers
- **Location**: Input (Crypto) Tab
- **Steps to Reproduce**: 
  1. Login with admin/password
  2. Navigate to Input tab
  3. Enter "-5" in the amount field
  4. Observe fiat value calculation
- **Expected**: App should reject negative input or show error message
- **Actual**: App accepts "-5" and calculates negative fiat value "$-3,95"
- **Impact**: Logic error - cannot convert negative cryptocurrency amount
- **Severity**: Medium (validation/logic error)
- **Evidence**: UI dump confirms inputValue="-5" and outputValue="$-3,95"

### Bug #8: Toast Message Race Condition During Fast Item Selection
- **Location**: List Tab
- **Steps to Reproduce**: 
  1. Login with admin/password
  2. On List tab, quickly tap items 1, 2, 3, 4, 5, 6 in rapid succession (< 100ms between taps)
  3. Observe the toast message during the clicks
- **Expected**: Toast should update for each item: "Selected -> Item 1", then "Selected -> Item 2", etc.
- **Actual**: Toast shows "1 item clicked" and stays stuck, only updating to correct count when clicking stops
- **Impact**: UI feedback is incorrect during rapid interaction - confusing for users
- **Severity**: Low (UI/UX issue)
- **Note**: Bug only occurs with rapid clicks; slow clicks (>300ms apart) work correctly

### Bug #9: Input Field Has Default Value "1" Instead of Being Empty
- **Location**: Input (Crypto) Tab
- **Steps to Reproduce**: 
  1. Login with admin/password
  2. Navigate to Input tab
  3. Observe the amount input field
- **Expected**: Input field should be empty by default
- **Actual**: Input field automatically contains "1"
- **Impact**: User must clear the field before entering their desired amount
- **Severity**: Low (UX issue)

### Improvement #1: Missing Logout Functionality
- **Location**: Global navigation / Test screen
- **Observation**: After successful login there is no visible way to log out or switch user.
- **Expected**: App should provide a clear **Logout** action (e.g. in toolbar menu) so users can safely end their session or change accounts.
- **Impact**: Users must force-close the app to \"log out\", which is confusing and not aligned with common mobile UX patterns.
- **Severity**: Low (UX / usability improvement)

---

## 📊 Example Test Output

```
=============================================================
▶ STARTING: testValidLogin
=============================================================
✅ PASSED: testValidLogin
   Duration: 3245ms

=============================================================
▶ STARTING: testScrollDown
=============================================================
📋 Item 15 visible before scroll: false
✅ PASSED: testScrollDown
   Duration: 2156ms

=============================================================
▶ STARTING: testButtonColorCycle
=============================================================
📋 Testing color cycle for Button 1:
   Click 1 completed
   Click 2 completed
   Click 3 completed
✅ PASSED: testButtonColorCycle
   Duration: 1823ms
```

---

## 👤 Author

Stefan - QA Engineer

---

## 📝 License

This project is for evaluation purposes as part of a take-home assessment.
