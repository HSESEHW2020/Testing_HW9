package ru.hse;// Generated by Selenium IDE
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatePrivatePostUserTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @BeforeEach
  public void setUp() {
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @AfterEach
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void createprivatepostuser() {
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
    driver.findElement(By.id("post-title-0")).sendKeys("TestPrivate");
    driver.findElement(By.cssSelector(".editor-post-publish-panel__toggle")).click();
    driver.findElement(By.cssSelector(".editor-post-publish-panel__prepublish > .components-panel__body:nth-child(3) .components-button")).click();
    driver.findElement(By.cssSelector(".editor-post-visibility__choice:nth-child(3) > .editor-post-visibility__dialog-label")).click();
    assertEquals(driver.switchTo().alert().getText(), is("Вы хотите опубликовать запись как личную?"));
    driver.switchTo().alert().accept();
    driver.findElement(By.cssSelector(".is-secondary:nth-child(1)")).click();
    assertEquals(driver.findElement(By.cssSelector(".entry-title")).getText(), is("Личное: TestPrivate"));
  }
}