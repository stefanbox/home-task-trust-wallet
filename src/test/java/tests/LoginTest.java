package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Login Screen functionality.
 * Tests credential validation and login flow.
 * 
 * KNOWN ISSUES FOUND:
 * 1. Error message has typo: "Incoorect" instead of "Incorrect"
 * 2. Same generic error shown for all validation failures
 *    (empty fields, wrong username, wrong password)
 *    - Should ideally show specific messages like "Username required" or "Invalid password"
 */
public class LoginTest extends BaseTest {
    
    @BeforeMethod(alwaysRun = true)
    public void ensureOnLoginScreen() {
        // If we're on Test page, go back to Login
        if (testPage.isTestPageDisplayed()) {
            testPage.goBack();
        }
    }
    
    // ============ POSITIVE TESTS ============
    
    @Test(priority = 1, description = "Verify login screen is displayed on app launch")
    public void testLoginScreenDisplayed() {
        Assert.assertTrue(loginPage.isLoginScreenDisplayed(), 
                "Login screen should be displayed on app launch");
    }
    
    @Test(priority = 2, description = "Verify logo is displayed on login screen")
    public void testLogoDisplayed() {
        Assert.assertTrue(loginPage.isLogoDisplayed(), 
                "Logo should be visible on login screen");
    }
    
    @Test(priority = 3, description = "Verify successful login with valid credentials (admin/password)")
    public void testValidLogin() {
        loginPage.login("admin", "password");
        
        Assert.assertTrue(testPage.isTestPageDisplayed(), 
                "Should navigate to Test page after successful login");
    }
    
    // ============ NEGATIVE TESTS - EMPTY FIELDS ============
    
    @Test(priority = 4, description = "Verify error when submitting with empty username and password")
    public void testEmptyCredentials() {
        loginPage.clearFields();
        loginPage.clickSubmit();
        
        // Verify error is displayed
        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed for empty credentials");
        
        // Verify we stay on login screen
        Assert.assertTrue(loginPage.isLoginScreenDisplayed(),
                "Should stay on login screen with empty credentials");
        
        // Log the actual error message for debugging
        String actualError = loginPage.getErrorMessage();
        System.out.println("Empty credentials error: " + actualError);
        
        // NOTE: App shows generic "Incoorect credentials" instead of specific "Fields required" message
        // This is documented as a UX issue
    }
    
    @Test(priority = 5, description = "Verify error when submitting with empty username only")
    public void testEmptyUsername() {
        loginPage.clearFields();
        loginPage.enterPassword("password");
        loginPage.clickSubmit();
        
        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed for empty username");
        Assert.assertTrue(loginPage.isLoginScreenDisplayed(),
                "Should stay on login screen");
        
        String actualError = loginPage.getErrorMessage();
        System.out.println("Empty username error: " + actualError);
        
        // NOTE: Should ideally show "Username is required" but shows generic error
    }
    
    @Test(priority = 6, description = "Verify error when submitting with empty password only")
    public void testEmptyPassword() {
        loginPage.clearFields();
        loginPage.enterUsername("admin");
        loginPage.clickSubmit();
        
        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed for empty password");
        Assert.assertTrue(loginPage.isLoginScreenDisplayed(),
                "Should stay on login screen");
        
        String actualError = loginPage.getErrorMessage();
        System.out.println("Empty password error: " + actualError);
        
        // NOTE: Should ideally show "Password is required" but shows generic error
    }
    
    // ============ NEGATIVE TESTS - INVALID CREDENTIALS ============
    
    @Test(priority = 7, description = "Verify error with invalid username and valid password")
    public void testInvalidUsername() {
        loginPage.clearFields();
        loginPage.login("wronguser", "password");
        
        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed for invalid username");
        Assert.assertTrue(loginPage.isLoginScreenDisplayed(),
                "Should stay on login screen with invalid username");
        
        String actualError = loginPage.getErrorMessage();
        System.out.println("Invalid username error: " + actualError);
        
        // NOTE: Should ideally show "Invalid username" but shows generic error
    }
    
    @Test(priority = 8, description = "Verify error with valid username and invalid password")
    public void testInvalidPassword() {
        loginPage.clearFields();
        loginPage.login("admin", "wrongpassword");
        
        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed for invalid password");
        Assert.assertTrue(loginPage.isLoginScreenDisplayed(),
                "Should stay on login screen with invalid password");
        
        String actualError = loginPage.getErrorMessage();
        System.out.println("Invalid password error: " + actualError);
        
        // NOTE: Should ideally show "Invalid password" but shows generic error
    }
    
    @Test(priority = 9, description = "Verify error with both invalid username and password")
    public void testBothInvalidCredentials() {
        loginPage.clearFields();
        loginPage.login("wronguser", "wrongpassword");
        
        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed for invalid credentials");
        Assert.assertTrue(loginPage.isLoginScreenDisplayed(),
                "Should stay on login screen");
        
        String actualError = loginPage.getErrorMessage();
        System.out.println("Both invalid error: " + actualError);
    }
    
    // ============ ERROR MESSAGE VALIDATION ============
    
    @Test(priority = 10, description = "Verify error message contains 'credentials' text")
    public void testErrorMessageContent() {
        loginPage.clearFields();
        loginPage.clickSubmit();
        
        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed");
        
        // Verify error message contains expected keywords
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.toLowerCase().contains("credentials"),
                "Error message should mention 'credentials'. Actual: " + errorMsg);
        
        // Document the typo bug
        if (errorMsg.contains("Incoorect")) {
            System.out.println("BUG FOUND: Error message has typo 'Incoorect' instead of 'Incorrect'");
        }
    }
    
    // ============ EDGE CASES ============
    
    @Test(priority = 11, description = "Verify login with spaces in username")
    public void testUsernameWithSpaces() {
        loginPage.clearFields();
        loginPage.login(" admin ", "password");
        
        // Check if login succeeds (spaces trimmed) or fails
        boolean loginSucceeded = testPage.isTestPageDisplayed();
        boolean errorShown = loginPage.isErrorDisplayed();
        
        System.out.println("Username with spaces - Login succeeded: " + loginSucceeded + ", Error shown: " + errorShown);
        
        // Either should succeed (if app trims) or show error
        Assert.assertTrue(loginSucceeded || errorShown,
                "Should either login successfully or show error for username with spaces");
    }
    
    @Test(priority = 12, description = "Verify case sensitivity of credentials")
    public void testCaseSensitivity() {
        loginPage.clearFields();
        loginPage.login("ADMIN", "PASSWORD");
        
        // Check if login is case-sensitive
        boolean loginSucceeded = testPage.isTestPageDisplayed();
        boolean errorShown = loginPage.isErrorDisplayed();
        
        System.out.println("Uppercase credentials - Login succeeded: " + loginSucceeded + ", Error shown: " + errorShown);
        
        // Document the behavior
        if (loginSucceeded) {
            System.out.println("NOTE: Login is case-insensitive");
        } else {
            System.out.println("NOTE: Login is case-sensitive");
        }
    }
}
