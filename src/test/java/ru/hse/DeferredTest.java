package ru.hse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeferredTest {
    JavascriptExecutor js;
    private WebDriver driver;
    private Map<String, Object> vars;

    @BeforeEach
    public void setUp() {
        driver = DriverHelper.getInstalledDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<>();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
    /*
     * Создаёт новый пост с задержкой 2 минуты,
     * после чего проверяет список отлложенных записей,
     * если она там есть то тест пройдён
     */
    @Test
    public void deferred() {
        driver.get("https://ruswizard.site/test");
        vars.put("linkText", driver.findElement(By.cssSelector(".widget_meta li:nth-child(1)")).getText());
        if ((Boolean) js.executeScript("return (arguments[0] == 'Регистрация')", vars.get("linkText"))) {
            driver.findElement(By.linkText("Войти")).click();
            driver.findElement(By.id("user_login")).click();
            driver.findElement(By.id("user_login")).sendKeys("Kotek_Myau");
            driver.findElement(By.id("user_pass")).click();
            driver.findElement(By.id("user_pass")).sendKeys("wPJ3Kdey(py!m9iQ");
            driver.findElement(By.id("wp-submit")).click();
        } else {
        }
        driver.findElement(By.cssSelector("#wp-admin-bar-new-content .ab-label")).click();
        driver.findElement(By.id("post-title-0")).sendKeys("Selenium - игрушка дьявола");
        driver.findElement(By.cssSelector(".edit-post-post-schedule__toggle")).click();
        driver.findElement(By.cssSelector(".components-datetime__time-field-minutes-input")).click();
        vars.put("currentMinute", driver.findElement(By.cssSelector(".components-datetime__time-field-minutes-input")).getAttribute("value"));
        if ((Boolean) js.executeScript("return (arguments[0] < 58)", vars.get("currentMinute"))) {
            vars.put("currentMinute", js.executeScript("return parseInt(arguments[0]) + 2", vars.get("currentMinute")));
            driver.findElement(By.cssSelector(".components-datetime__time-field-minutes-input")).sendKeys(vars.get("currentMinute").toString());
        } else {
            driver.findElement(By.cssSelector(".components-datetime__time-field-minutes-input")).sendKeys("00");
            vars.put("currentHour", driver.findElement(By.cssSelector(".components-datetime__time-field-hours-input")).getAttribute("value"));
            if ((Boolean) js.executeScript("return (arguments[0] < 23)", vars.get("currentHour"))) {
                vars.put("currentHour", js.executeScript("return parseInt(arguments[0]) +1", vars.get("currentHour")));
                driver.findElement(By.cssSelector(".components-datetime__time-field-hours-input")).sendKeys(vars.get("currentHour").toString());
            } else {
                driver.findElement(By.cssSelector(".components-datetime__time-field-hours-input")).sendKeys("00");
                vars.put("dayOfMonth", driver.findElement(By.cssSelector(".components-datetime__time-field-day-input")).getAttribute("value"));
                vars.put("dayOfMonth", js.executeScript("return parseInt(arguments[0]) +1", vars.get("dayOfMonth")));
                driver.findElement(By.cssSelector(".components-datetime__time-field-day-input")).sendKeys(vars.get("dayOfMonth").toString());
            }
        }
        vars.put("id", js.executeScript("const alink = document.getElementsByClassName(\"components-external-link edit-post-post-link__link\")[0].text; return (new RegExp(\"p=(\\\\\\d+)\")).exec(alink)[1];"));
        driver.findElement(By.cssSelector(".editor-post-publish-panel__toggle")).click();
        driver.findElement(By.cssSelector(".editor-post-publish-button")).click();
        driver.get("https://ruswizard.site/test/wp-admin/edit.php");
        driver.findElement(By.cssSelector(".future")).click();
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".post-vars.get('id').toString()"));
            assert (elements.size() > 0);
        }
    }
}
