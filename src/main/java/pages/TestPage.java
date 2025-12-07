package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object for the main Test screen with tabs.
 * 
 * This is the main page shown after successful login.
 * It contains 4 tabs:
 *   - List (DEFAULT tab) - Scrollable list with Items 1-20
 *   - Buttons - 3 color-cycling buttons + Reset
 *   - Switches - 3 toggle switches + Save functionality
 *   - Input (Crypto) - Currency converter with spinners
 * 
 * Note: "Test" is the page title shown in the app's toolbar.
 *       After login, user lands on this page with List tab selected.
 */
public class TestPage extends BasePage {
    
    // ============ TAB LOCATORS ============
    
    private final By listTab = AppiumBy.androidUIAutomator("new UiSelector().text(\"List\")");
    private final By buttonsTab = AppiumBy.androidUIAutomator("new UiSelector().text(\"Buttons\")");
    private final By switchesTab = AppiumBy.androidUIAutomator("new UiSelector().text(\"Switches\")");
    private final By inputTab = AppiumBy.androidUIAutomator("new UiSelector().text(\"Input\")");
    private final By backButton = AppiumBy.accessibilityId("Navigate up");
    private final By pageTitle = AppiumBy.id("com.example.trusttest:id/toolbar_title");
    
    // ============ TAB NAVIGATION ============
    
    /**
     * Navigate to List tab.
     */
    public TestPage goToListTab() {
        click(listTab);
        return this;
    }
    
    /**
     * Navigate to Buttons tab.
     */
    public TestPage goToButtonsTab() {
        click(buttonsTab);
        return this;
    }
    
    /**
     * Navigate to Switches tab.
     */
    public TestPage goToSwitchesTab() {
        click(switchesTab);
        return this;
    }
    
    /**
     * Navigate to Input (Crypto) tab.
     */
    public TestPage goToInputTab() {
        click(inputTab);
        return this;
    }
    
    /**
     * Click back button to return to login.
     */
    public void goBack() {
        click(backButton);
    }
    
    /**
     * Check if Test page is displayed.
     */
    public boolean isTestPageDisplayed() {
        return isDisplayed(listTab, 5);
    }
    
    // ============================================
    // LIST TAB METHODS
    // ============================================
    
    private final By listItems = AppiumBy.androidUIAutomator(
            "new UiSelector().textStartsWith(\"Item\")");
    
    /**
     * Get all visible list items.
     */
    public List<WebElement> getListItems() {
        return findElements(listItems);
    }
    
    /**
     * Get count of visible list items.
     */
    public int getVisibleItemCount() {
        return getListItems().size();
    }
    
    /**
     * Click on a specific list item by number.
     */
    public void clickListItem(int itemNumber) {
        By item = AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"Item " + itemNumber + "\")");
        click(item);
    }
    
    /**
     * Check if specific item is visible.
     */
    public boolean isItemVisible(int itemNumber) {
        By item = AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"Item " + itemNumber + "\")");
        return isDisplayed(item, 2);
    }
    
    /**
     * Scroll to specific item in the list.
     */
    public WebElement scrollToItem(int itemNumber) {
        return scrollToText("Item " + itemNumber);
    }
    
    // ============================================
    // BUTTONS TAB METHODS
    // ============================================
    
    private final By button1 = AppiumBy.androidUIAutomator("new UiSelector().text(\"Button 1\")");
    private final By button2 = AppiumBy.androidUIAutomator("new UiSelector().text(\"Button 2\")");
    private final By button3 = AppiumBy.androidUIAutomator("new UiSelector().text(\"Button 3\")");
    private final By resetButton = AppiumBy.androidUIAutomator("new UiSelector().text(\"Reset states\")");
    
    /**
     * Click Button 1.
     */
    public TestPage clickButton1() {
        click(button1);
        return this;
    }
    
    /**
     * Click Button 2.
     */
    public TestPage clickButton2() {
        click(button2);
        return this;
    }
    
    /**
     * Click Button 3.
     */
    public TestPage clickButton3() {
        click(button3);
        return this;
    }
    
    /**
     * Click Reset states button.
     */
    public TestPage clickResetStates() {
        click(resetButton);
        return this;
    }
    
    /**
     * Get Button 1 element for color inspection.
     */
    public WebElement getButton1Element() {
        return waitForElement(button1);
    }
    
    /**
     * Get Button 2 element for color inspection.
     */
    public WebElement getButton2Element() {
        return waitForElement(button2);
    }
    
    /**
     * Get Button 3 element for color inspection.
     */
    public WebElement getButton3Element() {
        return waitForElement(button3);
    }
    
    /**
     * Check if Reset button is displayed.
     */
    public boolean isResetButtonDisplayed() {
        return isDisplayed(resetButton, 3);
    }
    
    // ============================================
    // SWITCHES TAB METHODS
    // ============================================
    
    private final By switch1 = AppiumBy.androidUIAutomator("new UiSelector().text(\"Switch 1\")");
    private final By switch2 = AppiumBy.androidUIAutomator("new UiSelector().text(\"Switch 2\")");
    private final By switch3 = AppiumBy.androidUIAutomator("new UiSelector().text(\"Switch 3\")");
    private final By saveButton = AppiumBy.androidUIAutomator("new UiSelector().text(\"Save\")");
    private final By switchStatus1 = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Switch 1:\")");
    private final By switchStatus2 = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Switch 2:\")");
    private final By switchStatus3 = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Switch 3:\")");
    
    /**
     * Toggle Switch 1.
     */
    public TestPage toggleSwitch1() {
        click(switch1);
        return this;
    }
    
    /**
     * Toggle Switch 2.
     */
    public TestPage toggleSwitch2() {
        click(switch2);
        return this;
    }
    
    /**
     * Toggle Switch 3.
     */
    public TestPage toggleSwitch3() {
        click(switch3);
        return this;
    }
    
    /**
     * Click Save button on Switches tab.
     */
    public TestPage clickSave() {
        click(saveButton);
        return this;
    }
    
    /**
     * Get Switch 1 status text.
     */
    public String getSwitch1Status() {
        return getText(switchStatus1);
    }
    
    /**
     * Get Switch 2 status text.
     */
    public String getSwitch2Status() {
        return getText(switchStatus2);
    }
    
    /**
     * Get Switch 3 status text.
     */
    public String getSwitch3Status() {
        return getText(switchStatus3);
    }
    
    /**
     * Check if Save button is displayed.
     */
    public boolean isSaveButtonDisplayed() {
        return isDisplayed(saveButton, 3);
    }
    
    // ============================================
    // CRYPTO/INPUT TAB METHODS
    // ============================================
    
    private final By networkDropdown = AppiumBy.id("com.example.trusttest:id/network_spinner");
    private final By tokenDropdown = AppiumBy.id("com.example.trusttest:id/token_spinner");
    private final By amountInput = AppiumBy.id("com.example.trusttest:id/amount_input");
    private final By conversionResult = AppiumBy.id("com.example.trusttest:id/conversion_result");
    private final By fiatValue = AppiumBy.androidUIAutomator("new UiSelector().textStartsWith(\"Fiat value:\")");
    
    /**
     * Select network from dropdown.
     */
    public TestPage selectNetwork(String network) {
        click(networkDropdown);
        By networkOption = AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + network + "\")");
        click(networkOption);
        return this;
    }
    
    /**
     * Select token from dropdown.
     */
    public TestPage selectToken(String token) {
        click(tokenDropdown);
        By tokenOption = AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + token + "\")");
        click(tokenOption);
        return this;
    }
    
    /**
     * Enter amount for conversion.
     */
    public TestPage enterAmount(String amount) {
        type(amountInput, amount);
        return this;
    }
    
    /**
     * Get conversion result text.
     */
    public String getConversionResult() {
        return getText(conversionResult);
    }
    
    /**
     * Get fiat value text.
     */
    public String getFiatValue() {
        return getText(fiatValue);
    }
    
    /**
     * Check if conversion result is displayed.
     */
    public boolean isConversionResultDisplayed() {
        return isDisplayed(conversionResult, 3);
    }
    
    /**
     * Clear amount input field.
     */
    public TestPage clearAmount() {
        waitForElement(amountInput).clear();
        return this;
    }
}




