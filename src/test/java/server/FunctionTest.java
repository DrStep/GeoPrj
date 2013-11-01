package server;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.sun.istack.internal.NotNull;
import junit.framework.Assert;
import junit.framework.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 26.10.13
 * Time: 8:50
 * To change this template use File | Settings | File Templates.
 */
public class FunctionTest {
    public void testLogin(@NotNull String url, @NotNull String username, @NotNull String password) {
        //WebDriver driver = new HtmlUnitDriver(true);
        WebDriver driver = new FirefoxDriver();
        driver.get(url);
        WebElement element = driver.findElement(By.name("name"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element.submit();
        /*(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            @NotNull
            public Boolean apply(@NotNull WebDriver d) {
                return true;
            }
        });*/
        Alert alert = new WebDriverWait(driver, 1000).until(ExpectedConditions.alertIsPresent());
        String result = alert.getText();
        Assert.assertEquals("Status returned doesn't match the expected one", "ok", result);
        driver.quit();
    }


}