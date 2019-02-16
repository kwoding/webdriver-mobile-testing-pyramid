package it.ding.webdriver;

import static it.ding.webdriver.DriverFactory.getDriver;
import static it.ding.webdriver.DriverFactory.setDriver;
import static it.ding.webdriver.Platform.CHROME_DESKTOP_LOCAL;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import it.ding.webdriver.pageobject.TheInternetLoginPage;
import java.net.MalformedURLException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DesktopBrowserTest {

    private static RemoteWebDriver driver;

    private TheInternetLoginPage theInternetLoginPage = new TheInternetLoginPage(driver);

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
}
