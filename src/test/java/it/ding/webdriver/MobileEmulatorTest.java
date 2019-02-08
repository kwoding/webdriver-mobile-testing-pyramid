package it.ding.webdriver;

import static it.ding.webdriver.DriverFactory.getDriver;
import static it.ding.webdriver.DriverFactory.setDriver;
import static it.ding.webdriver.Platform.SAMSUNG_GALAXY_S9_EMULATOR_CLOUD;
import static it.ding.webdriver.util.BrowserUtil.getCurrentUrl;
import static it.ding.webdriver.util.BrowserUtil.takeScreenshot;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.GsmCallActions;
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
        setDriver(SAMSUNG_GALAXY_S9_EMULATOR_CLOUD);
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

    @Test
    public void canMakeGsmCall() throws InterruptedException, IOException {
        String phoneNumber = "1234567890";
        sauceConHomePage.visit();
        driver.makeGsmCall(phoneNumber, GsmCallActions.CALL);
        Thread.sleep(3000);
        takeScreenshot("gsm-incoming-call");
        driver.makeGsmCall(phoneNumber, GsmCallActions.CANCEL);
        Thread.sleep(3000);
        takeScreenshot("gsm-no-call");
        assertThat(getCurrentUrl(), containsString("saucecon.com"));
    }

}
