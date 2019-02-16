package it.ding.webdriver;

import static it.ding.webdriver.DriverFactory.getDriver;
import static it.ding.webdriver.DriverFactory.setDriver;
import static it.ding.webdriver.Platform.CHROME_DESKTOP_LOCAL;
import static it.ding.webdriver.util.BrowserUtil.resizeBrowser;
import static it.ding.webdriver.util.BrowserUtil.takeScreenshot;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import it.ding.webdriver.pageobject.BookingComPage;
import it.ding.webdriver.pageobject.TheInternetLoginPage;
import java.io.IOException;
import java.net.MalformedURLException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DesktopBrowserTest {

    private static RemoteWebDriver driver;

    private TheInternetLoginPage theInternetLoginPage = new TheInternetLoginPage(driver);

    private BookingComPage bookingComPage = new BookingComPage(driver);

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        setDriver(CHROME_DESKTOP_LOCAL);
        driver = getDriver();
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void canLogin() {
        theInternetLoginPage.visit();

        theInternetLoginPage.login("tomsmith", "SuperSecretPassword!");

        assertThat(theInternetLoginPage.getConfirmationText(),
            containsString("You logged into a secure area!"));
    }

    @Test
    // Add argument "--user-agent" when instantiating the browser
    public void canInjectUserAgent() {
        bookingComPage.visit();
        resizeBrowser(360, 740);
        assertThat(bookingComPage.isBookingAppDisplayed(), is(true));
    }

    @Test
    public void canSetGeoLocation() throws InterruptedException, IOException {
        ((LocationContext) driver).setLocation(new Location(3.1466, 101.6958, 100));
        driver.get("http://maps.google.com");
        Thread.sleep(3000);
        takeScreenshot("gps");
    }
}
