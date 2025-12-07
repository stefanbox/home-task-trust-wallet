package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Test class for List Tab functionality.
 * Tests scrolling and list item interactions.
 */
public class ListTest extends BaseTest {
    
    @BeforeClass(alwaysRun = true)
    @Override
    public void setUp() {
        super.setUp();
        // Login and navigate to List tab
        loginPage = new pages.LoginPage();
        testPage = new pages.TestPage();
        
        if (loginPage.isLoginScreenDisplayed()) {
            loginPage.loginWithValidCredentials();
        }
        testPage.goToListTab();
    }
    
    @Test(priority = 1, description = "Verify List tab displays items")
    public void testListItemsDisplayed() {
        int visibleItems = testPage.getVisibleItemCount();
        System.out.println("📋 Visible items count: " + visibleItems);
        
        Assert.assertTrue(visibleItems > 0, 
                "List should display at least one item");
    }
    
    @Test(priority = 2, description = "Verify Item 1 is visible without scrolling")
    public void testItem1Visible() {
        Assert.assertTrue(testPage.isItemVisible(1), 
                "Item 1 should be visible at the top of the list");
    }
    
    @Test(priority = 3, description = "Verify scrolling down reveals more items")
    public void testScrollDown() {
        // Check if Item 15 is initially visible
        boolean item15VisibleBefore = testPage.isItemVisible(15);
        System.out.println("📋 Item 15 visible before scroll: " + item15VisibleBefore);
        
        // Scroll to Item 15
        testPage.scrollToItem(15);
        
        // Verify Item 15 is now visible
        Assert.assertTrue(testPage.isItemVisible(15), 
                "Item 15 should be visible after scrolling");
    }
    
    @Test(priority = 4, description = "Verify scrolling up returns to top items")
    public void testScrollUp() {
        // First scroll down
        testPage.scrollToItem(15);
        
        // Then scroll back up
        testPage.scrollToItem(1);
        
        // Verify Item 1 is visible again
        Assert.assertTrue(testPage.isItemVisible(1), 
                "Item 1 should be visible after scrolling back up");
    }
    
    @Test(priority = 5, description = "Verify clicking on list item triggers interaction")
    public void testClickListItem() {
        // Make sure Item 1 is visible
        testPage.scrollToItem(1);
        
        // Click on Item 1
        testPage.clickListItem(1);
        
        // Note: Depending on app behavior, verify expected result
        // For now, just verify no crash and still on list
        Assert.assertTrue(testPage.isTestPageDisplayed(), 
                "Should remain on Test page after clicking item");
    }
    
    @Test(priority = 6, description = "Verify multiple items can be scrolled through")
    public void testScrollThroughMultipleItems() {
        // Scroll through items 5, 10, 15
        int[] itemsToCheck = {5, 10, 15};
        
        for (int itemNum : itemsToCheck) {
            testPage.scrollToItem(itemNum);
            Assert.assertTrue(testPage.isItemVisible(itemNum), 
                    "Item " + itemNum + " should be visible after scrolling");
            System.out.println("✅ Item " + itemNum + " verified");
        }
    }
}















