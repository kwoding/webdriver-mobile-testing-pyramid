package it.ding.webdriver;

import static it.ding.webdriver.DriverFactory.getDriver;
import static it.ding.webdriver.DriverFactory.setDriver;
import static it.ding.webdriver.Platform.GALAXYS9_EMULATOR_CLOUD;
import static it.ding.webdriver.util.BrowserUtil.takeScreenshot;

import io.appium.java_client.android.AndroidDriver;
import it.ding.webdriver.pageobject.SauceConHomePage;
import java.io.IOException;
import java.net.MalformedURLException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MobileEmulatorTest {

    private static AndroidDriver driver;

    private SauceConHomePage sauceConHomePage = new SauceConHomePage(driver);

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        setDriver(GALAXYS9_EMULATOR_CLOUD);
        driver = ((AndroidDriver) getDriver());
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void canSendSms() throws InterruptedException, IOException {
        sauceConHomePage.visit();
        ((AndroidDriver) getDriver()).sendSMS("555-123-9999", "SauceCon Rocks! -Kwo");
        Thread.sleep(3000);
        takeScreenshot("sms");
    }
}
