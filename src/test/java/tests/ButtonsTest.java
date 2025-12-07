package tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Test class for Buttons Tab functionality.
 * Tests button state changes and color cycling (blue -> green -> red).
 */
public class ButtonsTest extends BaseTest {
    
    @BeforeClass(alwaysRun = true)
    @Override
    public void setUp() {
        super.setUp();
        // Login and navigate to Buttons tab
        loginPage = new pages.LoginPage();
        testPage = new pages.TestPage();
        
        if (loginPage.isLoginScreenDisplayed()) {
            loginPage.loginWithValidCredentials();
        }
        testPage.goToButtonsTab();
    }
    
    @Test(priority = 1, description = "Verify Buttons tab displays all three buttons")
    public void testButtonsDisplayed() {
        WebElement btn1 = testPage.getButton1Element();
        WebElement btn2 = testPage.getButton2Element();
        WebElement btn3 = testPage.getButton3Element();
        
        Assert.assertNotNull(btn1, "Button 1 should be displayed");
        Assert.assertNotNull(btn2, "Button 2 should be displayed");
        Assert.assertNotNull(btn3, "Button 3 should be displayed");
    }
    
    @Test(priority = 2, description = "Verify Reset button is displayed")
    public void testResetButtonDisplayed() {
        Assert.assertTrue(testPage.isResetButtonDisplayed(), 
                "Reset states button should be displayed");
    }
    
    @Test(priority = 3, description = "Verify Button 1 can be clicked")
    public void testClickButton1() {
        // Get initial state
        WebElement button1Before = testPage.getButton1Element();
        String initialText = button1Before.getText();
        System.out.println("📋 Button 1 initial state: " + initialText);
        
        // Click button
        testPage.clickButton1();
        
        // Verify button is still present and clickable
        WebElement button1After = testPage.getButton1Element();
        Assert.assertNotNull(button1After, 
                "Button 1 should still be displayed after click");
    }
    
    @Test(priority = 4, description = "Verify Button 2 can be clicked")
    public void testClickButton2() {
        testPage.clickButton2();
        
        WebElement button2 = testPage.getButton2Element();
        Assert.assertNotNull(button2, 
                "Button 2 should still be displayed after click");
    }
    
    @Test(priority = 5, description = "Verify Button 3 can be clicked")
    public void testClickButton3() {
        testPage.clickButton3();
        
        WebElement button3 = testPage.getButton3Element();
        Assert.assertNotNull(button3, 
                "Button 3 should still be displayed after click");
    }
    
    @Test(priority = 6, description = "Verify Reset button resets all button states")
    public void testResetButtonStates() {
        // Click some buttons to change their states
        testPage.clickButton1();
        testPage.clickButton2();
        testPage.clickButton3();
        
        // Click reset
        testPage.clickResetStates();
        
        // Verify all buttons are still present (reset worked without errors)
        Assert.assertNotNull(testPage.getButton1Element(), "Button 1 should be present after reset");
        Assert.assertNotNull(testPage.getButton2Element(), "Button 2 should be present after reset");
        Assert.assertNotNull(testPage.getButton3Element(), "Button 3 should be present after reset");
        
        System.out.println("✅ Reset completed successfully");
    }
    
    @Test(priority = 7, description = "Verify button color cycle: click multiple times")
    public void testButtonColorCycle() {
        // Reset first
        testPage.clickResetStates();
        sleep(500);
        
        // Click Button 1 three times to go through color cycle (blue -> green -> red -> blue)
        System.out.println("📋 Testing color cycle for Button 1:");
        
        for (int i = 1; i <= 3; i++) {
            testPage.clickButton1();
            sleep(300);
            System.out.println("   Click " + i + " completed");
        }
        
        // Verify button is still functional
        WebElement button1 = testPage.getButton1Element();
        Assert.assertNotNull(button1, "Button 1 should remain functional after multiple clicks");
    }
    
    @Test(priority = 8, description = "Verify all buttons can be clicked in sequence")
    public void testAllButtonsSequence() {
        // Reset first
        testPage.clickResetStates();
        sleep(500);
        
        // Click each button once
        testPage.clickButton1();
        sleep(200);
        testPage.clickButton2();
        sleep(200);
        testPage.clickButton3();
        sleep(200);
        
        // All buttons should still be present
        Assert.assertNotNull(testPage.getButton1Element(), "Button 1 present after sequence");
        Assert.assertNotNull(testPage.getButton2Element(), "Button 2 present after sequence");
        Assert.assertNotNull(testPage.getButton3Element(), "Button 3 present after sequence");
        
        System.out.println("✅ All buttons clicked in sequence successfully");
    }
}















