package it.ding.webdriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import it.ding.webdriver.util.GlobalProperties;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {

    private static GlobalProperties globalProperties = GlobalProperties.getInstance();
    private static RemoteWebDriver driver;
    private static final String SAUCE_USERNAME = globalProperties.getString("sauce.username");
    private static final String SAUCE_KEY = globalProperties.getString("sauce.key");
    private static final String TEST_OBJECT_API_KEY = "testobjectApiKey";
    private static final String TEST_OBJECT_WEB_KEY_VALUE = globalProperties.getString("testobject.web.key");
    private static final String TEST_OBJECT_IOS_KEY_VALUE = globalProperties.getString("testobject.ios.key");
    private static final String TEST_OBJECT_ANDROID_KEY_VALUE = globalProperties.getString("testobject.android.key");
    private static final String CLOUD_HUB_URL = String.format(
        "https://%s:%s@ondemand.saucelabs.com:443/wd/hub",
        SAUCE_USERNAME, SAUCE_KEY);
    private static final String RDC_HUB_URL = "https://eu1.appium.testobject.com/wd/hub";
    private static final String LOCAL_APPIUM_SERVER_URL = "http://0.0.0.0:4723/wd/hub";
    private static final String ANDROID = "Android";
    private static final String IOS = "iOS";

    private DriverFactory() {
    }

    static void setDriver(Platform platform) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        switch (platform) {
            case CHROME_DESKTOP_CLOUD:
                ChromeOptions chromeOptions = new ChromeOptions();
                driver = new RemoteWebDriver(new URL(CLOUD_HUB_URL), chromeOptions);
                break;
            case FIREFOX_DESKTOP_CLOUD:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new RemoteWebDriver(new URL(CLOUD_HUB_URL), firefoxOptions);
                break;
            case PIXEL2_EMULATOR_LOCAL:
                setDeviceCapabilities(capabilities, "Pixel 2", ANDROID, "8.0");
                driver = new AndroidDriver(new URL(LOCAL_APPIUM_SERVER_URL), capabilities);
                break;
            case IPHONEX_SIMULATOR_LOCAL:
                setDeviceCapabilities(capabilities, "iPhone X", IOS, "12.1");
                capabilities.setCapability("startIWDP", true);
                driver = new IOSDriver(new URL(LOCAL_APPIUM_SERVER_URL), capabilities);
                break;
            case CHROME_DESKTOP_LOCAL:
            default:
                ChromeOptions options = new ChromeOptions();
                WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
                driver = new ChromeDriver(options);
                break;
        }
    }

    public static RemoteWebDriver getDriver() {
        return driver;
    }

    private static void setDeviceCapabilities(DesiredCapabilities capabilities, String deviceName,
        String platformName, String platformVersion) {
        capabilities.setCapability("automationName", "iOS".equalsIgnoreCase(platformName) ? "XCUITest" : "uiautomator2");
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("browserName", "iOS".equalsIgnoreCase(platformName) ? "safari" : "chrome");
        capabilities.setCapability("nativeWebScreenshot", true);
        capabilities.setCapability("newCommandTimeout", 90);
    }
}
