package it.ding.webdriver;

import static it.ding.webdriver.DriverFactory.getDriver;
import static it.ding.webdriver.DriverFactory.setDriver;
import static it.ding.webdriver.Platform.IPHONE6_RDC;
import static it.ding.webdriver.util.BrowserUtil.takeScreenshot;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.appium.java_client.ios.IOSDriver;
import it.ding.webdriver.pageobject.SauceConHomePage;
import it.ding.webdriver.pageobject.SwagLabsAppLoginPage;
import java.io.IOException;
import java.net.MalformedURLException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RealDeviceIOSTest {

    private static IOSDriver driver;

    private SauceConHomePage sauceConHomePage = new SauceConHomePage(driver);

    private SwagLabsAppLoginPage swagLabsAppLoginPage = new SwagLabsAppLoginPage(driver);

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        setDriver(IPHONE6_RDC);
        driver = ((IOSDriver) getDriver());
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void canTakeScreenshotOfScreen() throws IOException {
        sauceConHomePage.visit();
        takeScreenshot("saucecon-real-ios-device");
    }

    @Test
    // Set desired capability `recordDeviceVitals`
    public void canGetDeviceVitals() {
        assertThat(swagLabsAppLoginPage.isErrorMessageDisplayed(), is(false));

        swagLabsAppLoginPage.login("username", "password");

        assertThat(swagLabsAppLoginPage.getErrorMessage(),
            containsString("Username and password do not match any user in this service."));
    }
}
