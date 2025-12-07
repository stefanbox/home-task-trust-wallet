package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * Page Object for Login Screen.
 * Handles login functionality and validation.
 */
public class LoginPage extends BasePage {
    
    // ============ LOCATORS ============
    // Using resource-id for reliable element identification
    
    private final By usernameField = AppiumBy.id("com.example.trusttest:id/editTextUsername");
    private final By passwordField = AppiumBy.id("com.example.trusttest:id/editTextPassword");
    private final By submitButton = AppiumBy.id("com.example.trusttest:id/buttonSubmit");
    private final By errorMessage = AppiumBy.id("com.example.trusttest:id/textViewError");
    
    private final By logoImage = AppiumBy.id("com.example.trusttest:id/imageViewAppIcon");
    
    // ============ ERROR MESSAGE CONSTANTS ============
    // Note: App shows same generic message for all errors (potential UX issue)
    // Also contains typo: "Incoorect" instead of "Incorrect"
    public static final String GENERIC_ERROR_MESSAGE = "Incoorect credentials. Please try again.";
    
    // Expected specific messages (app should ideally show these)
    public static final String EXPECTED_EMPTY_USERNAME_ERROR = "Username is required";
    public static final String EXPECTED_EMPTY_PASSWORD_ERROR = "Password is required";
    public static final String EXPECTED_INVALID_USERNAME_ERROR = "Invalid username";
    public static final String EXPECTED_INVALID_PASSWORD_ERROR = "Invalid password";
    
    // Alternative locators (backup)
    private final By usernameByClass = AppiumBy.className("android.widget.EditText");
    private final By submitByText = AppiumBy.androidUIAutomator(
            "new UiSelector().text(\"Submit\")");
    
    // ============ ACTIONS ============
    
    /**
     * Enter username in the username field.
     */
    public LoginPage enterUsername(String username) {
        type(usernameField, username);
        return this;
    }
    
    /**
     * Enter password in the password field.
     */
    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }
    
    /**
     * Click the Submit button.
     */
    public void clickSubmit() {
        click(submitButton);
    }
    
    /**
     * Perform complete login with given credentials.
     * This is a convenience method combining all login steps.
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSubmit();
    }
    
    /**
     * Perform login with valid credentials from config.
     */
    public void loginWithValidCredentials() {
        login(config.AppConfig.getUsername(), config.AppConfig.getPassword());
    }
    
    // ============ VERIFICATIONS ============
    
    /**
     * Check if login screen is displayed.
     */
    public boolean isLoginScreenDisplayed() {
        return isDisplayed(submitButton, 5);
    }
    
    /**
     * Check if logo is displayed.
     */
    public boolean isLogoDisplayed() {
        return isDisplayed(logoImage, 3);
    }
    
    /**
     * Check if error message is displayed.
     */
    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage, 3);
    }
    
    /**
     * Get error message text.
     */
    public String getErrorMessage() {
        if (isErrorDisplayed()) {
            return getText(errorMessage);
        }
        return "";
    }
    
    /**
     * Verify error message contains expected text.
     */
    public boolean errorMessageContains(String expectedText) {
        String actualError = getErrorMessage();
        return actualError.toLowerCase().contains(expectedText.toLowerCase());
    }
    
    /**
     * Verify generic error message is displayed.
     * Note: App currently shows same message for all error types.
     */
    public boolean isGenericErrorDisplayed() {
        return getErrorMessage().equals(GENERIC_ERROR_MESSAGE);
    }
    
    /**
     * Get username field value.
     */
    public String getUsernameValue() {
        return waitForElement(usernameField).getText();
    }
    
    /**
     * Get password field value.
     */
    public String getPasswordValue() {
        return waitForElement(passwordField).getText();
    }
    
    /**
     * Clear both input fields.
     */
    public LoginPage clearFields() {
        waitForElement(usernameField).clear();
        waitForElement(passwordField).clear();
        return this;
    }
}


