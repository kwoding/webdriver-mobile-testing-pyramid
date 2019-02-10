package it.ding.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SwagLabsAppLoginPage extends BasePage {

    private static final By USERNAME = By.id("test-Username");
    private static final By PASSWORD = By.id("test-Password");
    private static final By SUBMIT = By.id("test-LOGIN");
    private static final By ERROR_MESSAGE = By.id("test-Error message");

    public SwagLabsAppLoginPage(RemoteWebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        type(USERNAME, username);
        type(PASSWORD, password);
        click(SUBMIT);
    }

    public String getErrorMessage() {
        isDisplayed(ERROR_MESSAGE);
        return getText(ERROR_MESSAGE);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(ERROR_MESSAGE);
    }
}
