package server;

import junit.framework.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created with IntelliJ IDEA.
 * User: sofia
 * Date: 01.11.13
 * Time: 18:58
 * To change this template use File | Settings | File Templates.
 */
public class SeleniumTest {
    @Test
    public void startWebDriver()
    {
        WebDriver driver = new FirefoxDriver();
        driver.get("http://localhost:8090/");
        Assert.assertTrue("title should statr differently", driver.getTitle().startsWith("Selenium Simplified"));
        driver.close();
    }
}
