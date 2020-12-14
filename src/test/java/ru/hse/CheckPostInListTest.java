package ru.hse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckPostInListTest {
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

    //Создаёт новый пост и проверяет его наличие в списке постов пользователя,
    //если он там найден,то тест пройден
    @Test
    public void CheckPostInList() {
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
        driver.findElement(By.id("post-title-0")).sendKeys("TestCheckPost");
        vars.put("id", js.executeScript("const alink = document.getElementsByClassName(\"components-external-link edit-post-post-link__link\")[0].text; return (new RegExp(\"p=(\\\\\\d+)\")).exec(alink)[1];"));
        driver.findElement(By.cssSelector(".block-editor-writing-flow__click-redirect")).click();
        driver.findElement(By.cssSelector(".editor-post-publish-panel__toggle")).click();
        driver.findElement(By.cssSelector(".editor-post-publish-button")).click();
        driver.findElement(By.cssSelector(".edit-post-fullscreen-mode-close path")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, 50);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".mine")));
        }
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".post-vars.get('id').toString()"));
            assert (elements.size() > 0);
        }
    }
}
