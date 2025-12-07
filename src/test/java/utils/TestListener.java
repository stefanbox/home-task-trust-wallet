package utils;

import config.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TestNG Listener for test events.
 * Captures screenshots on test failures and logs test progress.
 */
public class TestListener implements ITestListener {
    
    private static final String SCREENSHOT_DIR = "test-output/screenshots";
    
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("▶ STARTING: " + result.getMethod().getMethodName());
        System.out.println("=".repeat(60));
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ PASSED: " + result.getMethod().getMethodName());
        System.out.println("   Duration: " + getTestDuration(result) + "ms");
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("❌ FAILED: " + result.getMethod().getMethodName());
        System.out.println("   Reason: " + result.getThrowable().getMessage());
        System.out.println("   Duration: " + getTestDuration(result) + "ms");
        
        // Capture screenshot
        captureScreenshot(result.getMethod().getMethodName());
        
        // Print stack trace for debugging
        System.out.println("\n📋 Stack Trace:");
        result.getThrowable().printStackTrace();
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⏭ SKIPPED: " + result.getMethod().getMethodName());
        if (result.getThrowable() != null) {
            System.out.println("   Reason: " + result.getThrowable().getMessage());
        }
    }
    
    /**
     * Capture screenshot and save to file.
     */
    private void captureScreenshot(String testName) {
        try {
            // Create screenshot directory if not exists
            Path screenshotPath = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotPath)) {
                Files.createDirectories(screenshotPath);
            }
            
            // Take screenshot
            if (DriverManager.getDriver() != null) {
                TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
                File source = ts.getScreenshotAs(OutputType.FILE);
                
                // Generate filename with timestamp
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = testName + "_" + timestamp + ".png";
                Path destination = screenshotPath.resolve(fileName);
                
                // Copy screenshot to destination
                Files.copy(source.toPath(), destination);
                
                System.out.println("📸 Screenshot saved: " + destination.toAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("⚠ Failed to capture screenshot: " + e.getMessage());
        }
    }
    
    /**
     * Calculate test duration in milliseconds.
     */
    private long getTestDuration(ITestResult result) {
        return result.getEndMillis() - result.getStartMillis();
    }
}

