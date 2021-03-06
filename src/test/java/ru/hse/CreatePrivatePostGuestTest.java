package ru.hse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Создаёт новый приватный пост, после чего выходит из аккаунта
 * и возвращается на главную страницу, если незалогиненный
 * пользователь не видит пост (нет поста с этим id на странице), то тест пройден
 */
public class CreatePrivatePostGuestTest {
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

    @Test
    public void CreatePrivatePostGuest() {
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
        driver.findElement(By.id("post-title-0")).sendKeys("TestValue");

        try {
            driver.findElement(By.cssSelector(".edit-post-post-link__link"));
        } catch (NoSuchElementException exception) {
            driver.findElement(By.cssSelector(".components-panel__body:nth-child(2) .components-button")).click();
        }

        vars.put("id", js.executeScript("const alink = document.getElementsByClassName(\"components-external-link edit-post-post-link__link\")[0].text; return (new RegExp(\"p=(\\\\\\d+)\")).exec(alink)[1];"));
        driver.findElement(By.cssSelector(".edit-post-post-visibility__toggle")).click();
        driver.findElement(By.id("editor-post-private-0")).click();
        assertEquals(driver.switchTo().alert().getText(), "Вы хотите опубликовать запись как личную?");
        driver.switchTo().alert().accept();
        driver.findElement(By.cssSelector(".interface-complementary-area-header")).click();

        {
            WebDriverWait wait = new WebDriverWait(driver, 50);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Просмотреть запись")));
        }

        driver.findElement(By.cssSelector(".edit-post-fullscreen-mode-close path")).click();
        driver.findElement(By.linkText("Testing example")).click();
        driver.findElement(By.linkText("Выйти")).click();
        driver.findElement(By.linkText("← Назад к сайту «Testing example»")).click();
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".post-" + vars.get("id").toString()));
            assertTrue(elements.isEmpty());
        }
    }
}
