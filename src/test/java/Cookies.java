import io.appium.java_client.ios.IOSDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class Cookies {

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


    /**
     * Use a clean simulator or reset the cookies on the device before running this test
     */
    @Test
    public void cookieTest() {
        driver.get("https://accounts.google.com/AccountChooser");
        driver.findElement(By.cssSelector("#Email")).sendKeys("email@gmail.com");   //Update with real email
        driver.findElement(By.cssSelector("input#next")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#Passwd")))).sendKeys("Jobsite1");    //Update with real password
        driver.findElement(By.cssSelector("input#signIn")).click();
        wait.until(ExpectedConditions.titleIs("My Account"));

        int clear = 0;
        int secure = 0;
        Set<Cookie> cookies = driver.manage().getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.isSecure()) {
                secure++;
            } else {
                clear++;
            }
        }
        System.out.println("Found " + secure + " secure cookies");
        System.out.println("Found " + clear + " clear cookies");
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        Set<Cookie> cookies1 = driver.manage().getCookies();
        System.out.println(cookies1.size() + " still found.");
    }

    @AfterClass
    public static void quit() {
        driver.quit();
    }
}
