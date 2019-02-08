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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {

    private static GlobalProperties globalProperties = GlobalProperties.getInstance();
    private static RemoteWebDriver driver;
    private static final String SAUCE_USERNAME = globalProperties.getString("sauce.username");
    private static final String SAUCE_KEY = globalProperties.getString("sauce.key");
    private static final String CLOUD_HUB_URL = String.format(
        "https://%s:%s@ondemand.saucelabs.com:443/wd/hub",
        SAUCE_USERNAME, SAUCE_KEY);
    private static final String MOBILE_SERVER_URL = "http://0.0.0.0:4723/wd/hub";
    private static final String AUTOMATION_NAME = "automationName";
    private static final String DEVICE_NAME = "deviceName";
    private static final String PLATFORM_NAME = "platformName";
    private static final String BROWSER_NAME = "browserName";
    private static final String PLATFORM_VERSION = "platformVersion";
    private static final String START_IWDP = "startIWDP";
    private static final String NATIVE_WEB_SCREENSHOT = "nativeWebScreenshot";
    private static final String NEW_COMMAND_TIMEOUT = "newCommandTimeout";

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
                capabilities.setBrowserName("firefox");
                driver = new RemoteWebDriver(new URL(CLOUD_HUB_URL), capabilities);
                break;
            case SAMSUNG_GALAXY_S9_EMULATOR_CLOUD:
                capabilities.setCapability(AUTOMATION_NAME, "uiautomator2");
                capabilities.setCapability(DEVICE_NAME, "Samsung Galaxy S9 HD GoogleAPI Emulator");
                capabilities.setCapability(PLATFORM_NAME, "Android");
                capabilities.setCapability(PLATFORM_VERSION, "8.0");
                capabilities.setCapability(BROWSER_NAME, "chrome");
                capabilities.setCapability(NATIVE_WEB_SCREENSHOT, true);
                driver = new AndroidDriver(new URL(CLOUD_HUB_URL), capabilities);
                break;
            case PIXEL2_EMULATOR_LOCAL:
                capabilities.setCapability(AUTOMATION_NAME, "uiautomator2");
                capabilities.setCapability(DEVICE_NAME, "Pixel 2");
                capabilities.setCapability(PLATFORM_NAME, "Android");
                capabilities.setCapability(BROWSER_NAME, "chrome");
                capabilities.setCapability(NATIVE_WEB_SCREENSHOT, true);
                capabilities.setCapability(NEW_COMMAND_TIMEOUT, 90);
                driver = new AndroidDriver(new URL(MOBILE_SERVER_URL), capabilities);
                break;
            case IPHONEX_SIMULATOR_LOCAL:
                capabilities.setCapability(AUTOMATION_NAME, "XCUITest");
                capabilities.setCapability(DEVICE_NAME, "iPhone X");
                capabilities.setCapability(PLATFORM_NAME, "iOS");
                capabilities.setCapability(PLATFORM_VERSION, "12.1");
                capabilities.setCapability(BROWSER_NAME, "safari");
                capabilities.setCapability(START_IWDP, true);
                driver = new IOSDriver(new URL(MOBILE_SERVER_URL), capabilities);
                break;
            case CHROME_DESKTOP_LOCAL:
            default:
                ChromeOptions options = new ChromeOptions();
                WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
                options.addArguments(
                    "--user-agent=\"Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/603.1.23 (KHTML, like Gecko) Version/10.0 Mobile/14E5239e Safari/602.1\"");
                driver = new ChromeDriver(options);
                break;
        }
    }

    public static RemoteWebDriver getDriver() {
        return driver;
    }
}
