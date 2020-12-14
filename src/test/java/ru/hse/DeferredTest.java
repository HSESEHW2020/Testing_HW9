package ru.hse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;

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
        }
        driver.findElement(By.cssSelector("#wp-admin-bar-new-content .ab-label")).click();
        driver.findElement(By.id("post-title-0")).sendKeys("Selenium - игрушка дьявола");
        driver.findElement(By.cssSelector(".edit-post-post-schedule__toggle")).click();

        WebElement minutesElement = driver.findElement(By.cssSelector(".components-datetime__time-field-minutes-input"));
        int currentMinute = Integer.parseInt(minutesElement.getAttribute("value"));

        if (currentMinute < 58) {

            String newMinute = String.valueOf(currentMinute + 2);
            if (newMinute.length() < 2) {
                newMinute = "0" + newMinute;
            }

            driver.findElement(By.cssSelector(".components-datetime__time-field-minutes-input")).sendKeys(newMinute);

        } else {
            driver.findElement(By.cssSelector(".components-datetime__time-field-minutes-input")).sendKeys("00");
            int currentHour = Integer.parseInt(driver.findElement(By.cssSelector(".components-datetime__time-field-hours-input")).getAttribute("value"));
            if (currentHour < 23) {
                String newHour = String.valueOf(currentHour + 1);
                if (newHour.length() < 2) {
                    newHour = "0" + newHour;
                }

                driver.findElement(By.cssSelector(".components-datetime__time-field-hours-input")).sendKeys(newHour);
            } else {
                driver.findElement(By.cssSelector(".components-datetime__time-field-hours-input")).sendKeys("01");
                int currentDay = Integer.parseInt(driver.findElement(By.cssSelector(".components-datetime__time-field-day-input")).getAttribute("value"));

                String newDay = String.valueOf(currentDay + 1);
                if (newDay.length() < 2) {
                    newDay = "0" + newDay;
                }

                driver.findElement(By.cssSelector(".components-datetime__time-field-day-input")).sendKeys(newDay);
            }
        }

        driver.findElement(By.cssSelector(".edit-post-post-schedule__toggle")).click();

        try {
            driver.findElement(By.cssSelector(".edit-post-post-link__link"));
        } catch (NoSuchElementException exception) {
            driver.findElement(By.cssSelector(".components-panel__body:nth-child(2) .components-button")).click();
        }

        vars.put("id", js.executeScript("const alink = document.getElementsByClassName(\"components-external-link edit-post-post-link__link\")[0].text; return (new RegExp(\"p=(\\\\\\d+)\")).exec(alink)[1];"));
        driver.findElement(By.cssSelector(".editor-post-publish-panel__toggle")).click();
        driver.findElement(By.cssSelector(".editor-post-publish-button")).click();
        driver.get("https://ruswizard.site/test/wp-admin/edit.php");
        driver.findElement(By.cssSelector(".future")).click();
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".post-" + vars.get("id").toString()));
            assertFalse(elements.isEmpty());
        }
    }
}
