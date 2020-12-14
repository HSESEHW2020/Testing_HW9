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

public class CensoreTest {
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

    // Создаёт новый публичный пост,после чего выходит из акканта,
    // после чего незалогиненый пользователь заходит в созданный пост,
    // вводит необхзодимые данные и в поле текст вводит слово geek,
    // и нажимает отправить,если комментарий после этого не появился,то тест пройден
    @Test
    public void censore() {
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
        driver.findElement(By.id("post-title-0")).click();
        driver.findElement(By.id("post-title-0")).sendKeys("GeekTest");
        driver.findElement(By.cssSelector(".editor-post-publish-panel__toggle")).click();
        driver.findElement(By.cssSelector(".editor-post-publish-button")).click();
        driver.findElement(By.cssSelector(".edit-post-fullscreen-mode-close path")).click();
        driver.findElement(By.linkText("Testing example")).click();
        driver.findElement(By.linkText("Выйти")).click();
        driver.findElement(By.linkText("← Назад к сайту «Testing example»")).click();
        driver.findElement(By.linkText("GeekTest")).click();
        driver.findElement(By.id("comment")).click();
        driver.findElement(By.id("comment")).sendKeys("geek");
        driver.findElement(By.id("author")).click();
        driver.findElement(By.id("author")).sendKeys("asdasdad");
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys("asdasdasfwdgw@gmail.com");
        driver.findElement(By.id("submit")).click();
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".comment-content > p"));
            assert (elements.size() == 0);
        }
    }
}
