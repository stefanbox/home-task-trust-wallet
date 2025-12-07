package pages;

import config.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Base page class with common methods for all page objects.
 * All page classes should extend this class.
 */
public class BasePage {
    protected AndroidDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = DriverManager.getWait();
    }

    /**
     * Wait for element to be visible and return it.
     */
    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable and return it.
     */
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Click on element with wait.
     */
    protected void click(By locator) {
        waitForClickable(locator).click();
    }

    /**
     * Type text into element.
     */
    protected void type(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text from element.
     */
    protected String getText(By locator) {
        return waitForElement(locator).getText();
    }

    /**
     * Check if element is displayed.
     */
    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if element is displayed with custom timeout.
     */
    protected boolean isDisplayed(By locator, int timeoutSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Find all elements matching locator.
     */
    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Scroll down on the screen.
     */
    protected void scrollDown() {
        driver.executeScript("mobile: scrollGesture", java.util.Map.of(
            "left", 100,
            "top", 500,
            "width", 200,
            "height", 500,
            "direction", "down",
            "percent", 0.75
        ));
    }

    /**
     * Scroll up on the screen.
     */
    protected void scrollUp() {
        driver.executeScript("mobile: scrollGesture", java.util.Map.of(
            "left", 100,
            "top", 500,
            "width", 200,
            "height", 500,
            "direction", "up",
            "percent", 0.75
        ));
    }

    /**
     * Scroll to element with specific text.
     */
    protected WebElement scrollToText(String text) {
        return driver.findElement(AppiumBy.androidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + text + "\"))"
        ));
    }

    /**
     * Take screenshot for debugging.
     */
    protected void takeScreenshot(String name) {
        // Screenshot logic will be handled by test listener
        System.out.println("Screenshot requested: " + name);
    }
}

