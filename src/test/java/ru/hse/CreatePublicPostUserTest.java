package ru.hse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatePublicPostUserTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @BeforeEach
  public void setUp() {
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap();
  }
  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  //Создаёт новый публичный пост,
  // после чего выходит на страницу с постами,
  // если он там есть,то тест пройдёенн
  @Test
  public void CreatePublicPostUser() {
    driver.get("https://ruswizard.site/test/");
    vars.put("linkText", driver.findElement(By.cssSelector(".widget_meta li:nth-child(1)")).getText());
    if ((Boolean) js.executeScript("return (arguments[0] == \'Регистрация\')", vars.get("linkText"))) {
      driver.findElement(By.linkText("Войти")).click();
      driver.findElement(By.id("user_login")).click();
      driver.findElement(By.id("user_login")).sendKeys("Kotek_Myau");
      driver.findElement(By.id("user_pass")).click();
      driver.findElement(By.id("user_pass")).sendKeys("wPJ3Kdey(py!m9iQ");
      driver.findElement(By.id("wp-submit")).click();
    } else {
    }
    driver.findElement(By.cssSelector("#wp-admin-bar-new-content .ab-label")).click();
    vars.put("articleTitle", js.executeScript("return \"TestPublicV2\";"));
    driver.findElement(By.id("post-title-0")).sendKeys(vars.get("articleTitle").toString());
    vars.put("id", js.executeScript("const alink = document.getElementsByClassName(\"components-external-link edit-post-post-link__link\")[0].text; return (new RegExp(\"p=(\\\\\\d+)\")).exec(alink)[1];"));
    driver.findElement(By.cssSelector(".editor-post-publish-panel__toggle")).click();
    driver.findElement(By.cssSelector(".editor-post-publish-button")).click();
    driver.findElement(By.cssSelector(".edit-post-fullscreen-mode-close > svg")).click();
    driver.findElement(By.linkText("Testing example")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".post-vars.get('id').toString()"));
      assert(elements.size() > 0);
    }
    driver.findElement(By.cssSelector("#post-vars.get('id').toString() .entry-title")).click();
    assertEquals(driver.findElement(By.cssSelector(".entry-title")).getText(), "vars.get(\"articleTitle\").toString()");
  }
}
