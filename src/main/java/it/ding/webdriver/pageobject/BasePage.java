package it.ding.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    private RemoteWebDriver driver;

    public BasePage(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void visit(String url) {
        driver.get(url);
    }

    public WebElement find(By locator) {
        return driver.findElement(locator);
    }

    public void click(By locator) {
        find(locator).click();
    }

    public void scrollIntoView(By locator) {
        WebElement element = driver.findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void longPress(By locator) {
        Actions actions = new Actions(driver);
        actions.moveToElement(find(locator)).clickAndHold().perform();
    }

    public void type(By locator, String inputText) {
        find(locator).sendKeys(inputText);
    }

    public void select(String inputText, By locator) {
        Select selectItem = new Select(find(locator));
        selectItem.selectByVisibleText(inputText);
    }

    public void clear(By locator) {
        find(locator).clear();
    }

    public String getText(By locator) {
        return find(locator).getText();
    }

    public boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            return false;
        }
    }

    public boolean isDisplayed(By locator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }

    public boolean isDisplayed(WebElement element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }

    public boolean textPresentInElement(By locator, String searchString, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, searchString));
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }

    public String getVisibleText(By locator) {
        return find(locator).getText();
    }

    public String getAttributeValue(By locator, String attribute) {
        return find(locator).getAttribute(attribute);
    }
}
