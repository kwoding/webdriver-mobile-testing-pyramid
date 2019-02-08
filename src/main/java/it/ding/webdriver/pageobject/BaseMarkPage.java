package it.ding.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseMarkPage extends BasePage {

    private static final String BASEMARK = "http://web.basemark.com";
    private static final By RUN_DEMO_BUTTON = By.id("run-demo");

    public BaseMarkPage(RemoteWebDriver driver) {
        super(driver);
    }

    public void visit() {
        visit(BASEMARK);
    }

    public void runDemo() {
        click(RUN_DEMO_BUTTON);
    }

}
