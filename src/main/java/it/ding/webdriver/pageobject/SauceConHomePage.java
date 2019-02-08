package it.ding.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SauceConHomePage extends BasePage {

    private static final String HOME_PAGE_URL = "https://saucecon.com";
    private static final By EMAIL = By.id("input_2_1");
    private static final By SUBMIT = By.id("gform_submit_button_2");
    private static final By CONFIRMATION_TEXT = By.cssSelector(".success");

    public SauceConHomePage(RemoteWebDriver driver) {
        super(driver);
    }

    public void visit() {
        visit(HOME_PAGE_URL);
    }

    public void signUpNewsLetter(String email) {
        type(EMAIL, email);
        click(SUBMIT);
    }

    public String getConfirmationText() {
        isDisplayed(CONFIRMATION_TEXT);
        return getText(CONFIRMATION_TEXT);
    }

    public Point getEmailInputLocation() {
        return find(EMAIL).getLocation();
    }

    public Point getSubmitNewsLetterButtonLocation() {
        return find(SUBMIT).getLocation();
    }

    public Dimension getEmailInputSize() {
        return find(EMAIL).getSize();
    }

    public Dimension getSubmitNewsLetterButtonSize() {
        return find(SUBMIT).getSize();
    }
}
