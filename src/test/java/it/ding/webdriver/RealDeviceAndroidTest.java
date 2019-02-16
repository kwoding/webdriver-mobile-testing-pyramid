package it.ding.webdriver;

import static it.ding.webdriver.DriverFactory.getDriver;
import static it.ding.webdriver.DriverFactory.setDriver;
import static it.ding.webdriver.Platform.NEXUS5X_RDC;
import static it.ding.webdriver.util.BrowserUtil.takeScreenshot;

import io.appium.java_client.android.AndroidDriver;
import it.ding.webdriver.pageobject.BaseMarkPage;
import it.ding.webdriver.pageobject.DataIqHomePage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RealDeviceAndroidTest {

    private static AndroidDriver driver;

    private DataIqHomePage dataIqHomePage = new DataIqHomePage(driver);

    private BaseMarkPage baseMarkPage = new BaseMarkPage(driver);

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        setDriver(NEXUS5X_RDC);
        driver = ((AndroidDriver) getDriver());
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void canTakeScreenshotOfScreen() throws IOException {
        dataIqHomePage.visit();
        takeScreenshot("dataiq-real-android-device");
    }

    @Test
    public void canGetDeviceInfo() throws InterruptedException {
        baseMarkPage.visit();
        baseMarkPage.runDemo();

        System.out.println("memoryinfo: " + getDeviceInfo(driver, "memoryinfo"));
        System.out.println("batteryinfo: " + getDeviceInfo(driver, "batteryinfo"));
        System.out.println("networkinfo: " + getDeviceInfo(driver, "networkinfo"));
        Thread.sleep(30000);

        System.out.println("memoryinfo: " + getDeviceInfo(driver, "memoryinfo"));
        System.out.println("batteryinfo: " + getDeviceInfo(driver, "batteryinfo"));
        System.out.println("networkinfo: " + getDeviceInfo(driver, "networkinfo"));
    }

    private Map<String, Integer> getDeviceInfo(AndroidDriver driver, String dataType) {
        List<List<Object>> data = driver.getPerformanceData("com.android.chrome", dataType, 10);
        Map<String, Integer> readableData = new HashMap<>();
        for (int i = 0; i < data.get(0).size(); i++) {
            int val;
            if (data.get(1).get(i) == null) {
                val = 0;
            } else {
                val = Integer.parseInt((String) data.get(1).get(i));
            }
            readableData.put((String) data.get(0).get(i), val);
        }
        return readableData;
    }
}
