package it.ding.webdriver.pageobject;

import org.openqa.selenium.remote.RemoteWebDriver;

public class DataIqHomePage extends BasePage {

    private static final String HOME_PAGE_URL = "https://www.dataiq.co.uk";

    public DataIqHomePage(RemoteWebDriver driver) {
        super(driver);
    }

    public void visit() {
        visit(HOME_PAGE_URL);
    }
}
