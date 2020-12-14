package ru.hse;// Generated by Selenium IDE

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class CreatePrivatePostGuestTest {
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
  public void createprivatepostguest() {
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
    driver.findElement(By.id("post-title-0")).sendKeys("TestValue");
    vars.put("id", js.executeScript("const alink = document.getElementsByClassName(\"components-external-link edit-post-post-link__link\")[0].text; return (new RegExp(\"p=(\\\\\\d+)\")).exec(alink)[1];"));
    driver.findElement(By.cssSelector(".edit-post-post-visibility__toggle")).click();
    driver.findElement(By.id("editor-post-private-0")).click();
    assertThat(driver.switchTo().alert().getText(), is("Вы хотите опубликовать запись как личную?"));
    driver.switchTo().alert().accept();
    driver.findElement(By.cssSelector(".interface-complementary-area-header")).click();
    driver.findElement(By.cssSelector(".edit-post-fullscreen-mode-close path")).click();
    driver.findElement(By.linkText("Testing example")).click();
    driver.findElement(By.linkText("Выйти")).click();
    driver.findElement(By.linkText("← Назад к сайту «Testing example»")).click();
    {
      List<WebElement> elements = driver.findElements(By.cssSelector(".post-vars.get("id").toString()"));
      assert(elements.size() == 0);
    }
  }
}
