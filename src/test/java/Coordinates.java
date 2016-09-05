import io.appium.java_client.ios.IOSDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class Coordinates {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeClass
    public static void iOS() {
        URL url = null;
        try {
            url = new URL("http://127.0.0.1:4723/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        DesiredCapabilities capabilities = DesiredCapabilities.iphone();
        capabilities.setBrowserName("Safari");
        capabilities.setVersion("9.3");
        capabilities.setCapability("deviceName", "iPhone 4s");
        driver = new IOSDriver<>(url, capabilities);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void positionTest() {
        driver.get("http://appium.io/slate/en/master/?java#");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Appium GUI"))).click();
        Locatable heading = (Locatable) driver.findElement(By.cssSelector("#section-appium-gui>h1"));
        System.out.println(heading.getCoordinates().onPage().getY());
    }

    @AfterClass
    public static void quit() {
        driver.quit();
    }
}
