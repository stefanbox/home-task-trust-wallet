package config;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Manages AndroidDriver instance and configuration.
 * Implements singleton pattern for driver reuse across tests.
 */
public class DriverManager {
    private static AndroidDriver driver;
    private static WebDriverWait wait;

    /**
     * Initialize and return AndroidDriver instance.
     * Creates new driver if none exists.
     */
    public static AndroidDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    /**
     * Get WebDriverWait instance for explicit waits.
     */
    public static WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(AppConfig.getExplicitWait()));
        }
        return wait;
    }

    /**
     * Initialize the AndroidDriver with UiAutomator2 options.
     */
    private static void initializeDriver() {
        UiAutomator2Options options = new UiAutomator2Options();

        // Set app path
        File appFile = new File(AppConfig.getAppPath());
        if (appFile.exists()) {
            options.setApp(appFile.getAbsolutePath());
        } else {
            // If APK not found, use package/activity (app must be installed)
            options.setAppPackage(AppConfig.getAppPackage());
            options.setAppActivity(AppConfig.getAppActivity());
        }

        // Device capabilities
        options.setDeviceName(AppConfig.getDeviceName());
        options.setPlatformVersion(AppConfig.getPlatformVersion());
        options.setAutomationName("UiAutomator2");

        // Set UDID if specified (for specific device)
        String udid = AppConfig.getUdid();
        if (udid != null && !udid.isEmpty()) {
            options.setUdid(udid);
        }

        // Additional options for stability
        options.setNoReset(false);        // Fresh app state for each session
        options.setFullReset(false);      // Don't uninstall app after test
        options.setAutoGrantPermissions(true);  // Auto-grant app permissions
        options.setNewCommandTimeout(Duration.ofSeconds(300));

        try {
            URL appiumUrl = new URL(AppConfig.getAppiumUrl());
            driver = new AndroidDriver(appiumUrl, options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(AppConfig.getImplicitWait()));
            System.out.println("Driver initialized successfully!");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium URL: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize driver: " + e.getMessage());
        }
    }

    /**
     * Quit driver and clean up resources.
     */
    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Driver quit successfully!");
            } catch (Exception e) {
                System.out.println("Error quitting driver: " + e.getMessage());
            } finally {
                driver = null;
                wait = null;
            }
        }
    }

    /**
     * Reset the app to initial state.
     */
    public static void resetApp() {
        if (driver != null) {
            driver.terminateApp(AppConfig.getAppPackage());
            driver.activateApp(AppConfig.getAppPackage());
        }
    }
}

