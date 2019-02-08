package it.ding.webdriver.util;

import static it.ding.webdriver.DriverFactory.getDriver;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;

public class BrowserUtil {

    private static GlobalProperties globalProperties = GlobalProperties.getInstance();
    private static final String SCREENSHOT_FOLDER = globalProperties.getString("screenshot.folder");

    private BrowserUtil() {

    }

    public static void refreshPage() {
        getDriver().navigate().refresh();
    }

    public static String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public static void resizeBrowser(int width, int height) {
        Dimension dimension = new Dimension(width,height);
        getDriver().manage().window().setSize(dimension);
    }

    public static String takeScreenshot(String fileName) throws IOException {
        File srcFile = getDriver().getScreenshotAs(OutputType.FILE);
        String id = UUID.randomUUID().toString();
        String targetFileLocation = String.format("%s/%s-%s.jpg", SCREENSHOT_FOLDER, fileName, id);
        File targetFile = new File(targetFileLocation);
        FileUtils.copyFile(srcFile, targetFile);

        return targetFileLocation;
    }

}
