package it.ding.webdriver;

import static it.ding.webdriver.DriverFactory.getDriver;
import static it.ding.webdriver.DriverFactory.setDriver;
import static it.ding.webdriver.Platform.CHROME_DESKTOP_LOCAL;
import static it.ding.webdriver.util.BrowserUtil.resizeBrowser;
import static it.ding.webdriver.util.BrowserUtil.takeScreenshot;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import it.ding.webdriver.pageobject.BookingComPage;
import it.ding.webdriver.pageobject.SauceConHomePage;
import it.ding.webdriver.pageobject.TheInternetLoginPage;
import java.io.IOException;
import java.net.MalformedURLException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DesktopBrowserTest {

    private static RemoteWebDriver driver;

    private TheInternetLoginPage theInternetLoginPage = new TheInternetLoginPage(driver);

    private SauceConHomePage sauceConHomePage = new SauceConHomePage(driver);

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
    public void canInjectUserAgent() {
        // Add argument "--user-agent" when instantiating the browser
        bookingComPage.visit();
        resizeBrowser(360, 740);
        assertThat(bookingComPage.isBookingAppDisplayed(), is(true));
    }

    @Test
    public void canSetGeoLocation() throws InterruptedException, IOException {
        ((LocationContext)driver).setLocation(new Location(3.1466, 101.6958, 100));
        driver.get("http://maps.google.com");
        Thread.sleep(3000);
        takeScreenshot("gps");
    }

    @Test
    public void elementLocationShouldBeDisplayedDifferentlyOnMobileViewPort() {
        sauceConHomePage.visit();

        Point emailInputLocation = sauceConHomePage.getEmailInputLocation();
        Dimension emailInputSize = sauceConHomePage.getEmailInputSize();
        Point submitNewsLetterButtonLocation = sauceConHomePage.getSubmitNewsLetterButtonLocation();

        assertThat(submitNewsLetterButtonLocation.getX(), greaterThan(emailInputLocation.getX() + emailInputSize.getWidth()));
        assertThat(submitNewsLetterButtonLocation.getY(), lessThanOrEqualTo(emailInputLocation.getY() + emailInputSize.getHeight()));
        resizeBrowser(360, 740);

        Point emailInputMobileLocation = sauceConHomePage.getEmailInputLocation();
        Dimension emailInputMobileSize = sauceConHomePage.getEmailInputSize();
        Point submitNewsLetterButtonMobileLocation = sauceConHomePage.getSubmitNewsLetterButtonLocation();

        assertThat(submitNewsLetterButtonMobileLocation.getX(), lessThanOrEqualTo(emailInputMobileLocation.getX() + emailInputMobileSize.getWidth()));
        assertThat(submitNewsLetterButtonMobileLocation.getY(), greaterThan(emailInputMobileLocation.getY() + emailInputMobileSize.getHeight()));
    }

    @Test
    public void canTakeScreenshotOfScreen() throws IOException {
        sauceConHomePage.visit();
        resizeBrowser(360, 740);
        takeScreenshot("saucecon-browser");
    }

}
