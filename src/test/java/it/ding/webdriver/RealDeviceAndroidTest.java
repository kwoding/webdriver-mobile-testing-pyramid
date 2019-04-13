package it.ding.webdriver;

import static it.ding.webdriver.DriverFactory.getDriver;
import static it.ding.webdriver.DriverFactory.setDriver;
import static it.ding.webdriver.Platform.NEXUS5X_RDC;
import static it.ding.webdriver.util.BrowserUtil.takeScreenshot;

import io.appium.java_client.android.AndroidDriver;
import it.ding.webdriver.pageobject.SauceConHomePage;
import java.io.IOException;
import java.net.MalformedURLException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RealDeviceAndroidTest {

    private static AndroidDriver driver;

    private SauceConHomePage sauceConHomePage = new SauceConHomePage(driver);

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        setDriver(NEXUS5X_RDC);
        driver = ((AndroidDriver) getDriver());
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void canTakeScreenshotOfScreen() throws IOException {
        sauceConHomePage.visit();
        takeScreenshot("saucecon-real-android-device");
    }
}
