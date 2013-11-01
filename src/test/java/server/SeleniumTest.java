package server;

import com.sun.istack.internal.NotNull;
import junit.framework.Assert;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        String url = "http://localhost:8090/authform";
        String username="sonya";
        String password="sonya";
        driver.get(url);
        //driver.navigate().to("http://localhost:8090/");

        WebElement element = driver.findElement(By.name("name"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element.submit();

           // boolean bl = (new WebDriverWait(driver, 1000)).until(new ExpectedCondition<Boolean>() {
        Alert alert = new WebDriverWait(driver, 1000).until(ExpectedConditions.alertIsPresent());
        String result = alert.getText();
        Assert.assertEquals("Status returned doesn't match the expected one", "ok", result );
        driver.quit();
    }
}
