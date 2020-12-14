package ru.hse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Создаёт новый публичный пост,
 * после чего выходит на страницу с постами,
 * если он там есть,то тест пройден
 */
public class CreatePublicPostUserTest {
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
    public void CreatePublicPostUser() {
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
        vars.put("articleTitle", js.executeScript("return \"TestPublicV2\";"));
        driver.findElement(By.id("post-title-0")).sendKeys(vars.get("articleTitle").toString());

        try {
            driver.findElement(By.cssSelector(".edit-post-post-link__link"));
        } catch (NoSuchElementException exception) {
            driver.findElement(By.cssSelector(".components-panel__body:nth-child(2) .components-button")).click();
        }

        vars.put("id", js.executeScript("const alink = document.getElementsByClassName(\"components-external-link edit-post-post-link__link\")[0].text; return (new RegExp(\"p=(\\\\\\d+)\")).exec(alink)[1];"));
        driver.findElement(By.cssSelector(".editor-post-publish-panel__toggle")).click();
        driver.findElement(By.cssSelector(".editor-post-publish-button")).click();
        driver.findElement(By.cssSelector(".edit-post-fullscreen-mode-close > svg")).click();
        driver.findElement(By.linkText("Testing example")).click();
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".post-" + vars.get("id").toString()));
            assertTrue(elements.size() > 0);
        }
        driver.findElement(By.cssSelector("#post-"+ vars.get("id").toString() + " .entry-title")).click();
        assertEquals(driver.findElement(By.cssSelector(".entry-title")).getText(), vars.get("articleTitle").toString());
    }
}
