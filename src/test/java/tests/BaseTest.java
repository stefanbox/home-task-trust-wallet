package tests;

import config.DriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import pages.TestPage;

/**
 * Base test class that all test classes should extend.
 * Handles driver setup/teardown and provides common functionality.
 */
public class BaseTest {
    
    protected LoginPage loginPage;
    protected TestPage testPage;
    
    /**
     * Setup before all tests in the class.
     * Initializes the driver.
     */
    @BeforeClass(alwaysRun = true)
    public void setUp() {
        System.out.println("\n🚀 Initializing test session...");
        // Driver is initialized lazily when first accessed
        DriverManager.getDriver();
        System.out.println("✅ Driver initialized successfully!");
    }
    
    /**
     * Setup before each test method.
     * Initializes page objects.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        loginPage = new LoginPage();
        testPage = new TestPage();
    }
    
    /**
     * Cleanup after each test method.
     * Can be used to reset app state if needed.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDownTest() {
        // Optional: Reset app state between tests
        // DriverManager.resetApp();
    }
    
    /**
     * Cleanup after all tests in the class.
     * Quits the driver.
     */
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        System.out.println("\n🧹 Cleaning up test session...");
        DriverManager.quitDriver();
        System.out.println("✅ Driver quit successfully!");
    }
    
    /**
     * Helper method to perform login with valid credentials.
     * Use this in tests that need to be logged in first.
     */
    protected void performLogin() {
        loginPage.loginWithValidCredentials();
    }
    
    /**
     * Helper method to wait for a specific duration.
     * Use sparingly - prefer explicit waits in page objects.
     */
    protected void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}















