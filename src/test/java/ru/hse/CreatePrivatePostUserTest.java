package ru.hse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatePrivatePostUserTest {
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
     * Создаёт новый приватный пост,
     * После чего выходит на главную страницу
     * если пользователь видит пост,то тест пройден
     */
    @Test
    public void CreatePrivatePostUser() {
        driver.get("https://ruswizard.site/test/");
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
        driver.findElement(By.id("post-title-0")).sendKeys("TestPrivate");
        driver.findElement(By.cssSelector(".editor-post-publish-panel__toggle")).click();
        driver.findElement(By.cssSelector(".editor-post-publish-panel__prepublish > .components-panel__body:nth-child(3) .components-button")).click();
        driver.findElement(By.cssSelector(".editor-post-visibility__choice:nth-child(3) > .editor-post-visibility__dialog-label")).click();
        assertEquals(driver.switchTo().alert().getText(), "Вы хотите опубликовать запись как личную?");
        driver.switchTo().alert().accept();
        driver.findElement(By.cssSelector(".is-secondary:nth-child(1)")).click();

        driver.get("https://ruswizard.site/test/");

        {
            WebDriverWait wait = new WebDriverWait(driver, 50);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".entry-title")));
        }

        assertEquals("Личное: TestPrivate", driver.findElement(By.cssSelector(".entry-title")).getText());
    }
}
