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

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Проверяет возможность добавления комментария пользователем.
 * Логинится, если не залогинено, создаёт запись, открывает эту запись,
 * вводит текст комментария, отправляет. Затем проверяет, что отправленный
 * комментарий есть на странице
 */
public class AddCommentaryUserTest {

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
    public void addCommentaryUser() {
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
        driver.findElement(By.id("post-title-0")).sendKeys("CommentaryTest");
        driver.findElement(By.cssSelector(".editor-post-publish-panel__toggle")).click();
        driver.findElement(By.cssSelector(".editor-post-publish-button")).click();
        driver.findElement(By.linkText("Просмотреть запись")).click();
        driver.findElement(By.id("comment")).click();
        driver.findElement(By.id("comment")).sendKeys("a geek over here");
        driver.findElement(By.id("commentform")).click();
        driver.findElement(By.id("submit")).click();
        driver.findElement(By.cssSelector(".comments-wrapper")).click();
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".comment-content > p"));
            assertTrue(elements.size() > 0);
        }
    }
}
