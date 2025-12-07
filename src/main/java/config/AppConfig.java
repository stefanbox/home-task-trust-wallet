package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Application configuration manager.
 * Loads settings from config.properties file.
 */
public class AppConfig {
    private static Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config.properties";

    static {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(CONFIG_PATH);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.out.println("Warning: Could not load config.properties. Using defaults.");
        }
    }

    // App settings
    public static String getAppPath() {
        return properties.getProperty("app.path", "app/trust_test.apk");
    }

    public static String getAppPackage() {
        return properties.getProperty("app.package", "com.example.trusttest");
    }

    public static String getAppActivity() {
        return properties.getProperty("app.activity", "com.example.trusttest.MainActivity");
    }

    // Device settings
    public static String getDeviceName() {
        return properties.getProperty("device.name", "Android Device");
    }

    public static String getPlatformVersion() {
        return properties.getProperty("platform.version", "8.0");
    }

    public static String getUdid() {
        return properties.getProperty("device.udid", "");
    }

    // Appium settings
    public static String getAppiumUrl() {
        return properties.getProperty("appium.url", "http://127.0.0.1:4723");
    }

    // Timeouts
    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("timeout.implicit", "10"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("timeout.explicit", "15"));
    }

    // Credentials
    public static String getUsername() {
        return properties.getProperty("login.username", "admin");
    }

    public static String getPassword() {
        return properties.getProperty("login.password", "password");
    }
}

