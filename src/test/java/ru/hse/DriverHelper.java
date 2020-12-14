package ru.hse;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DriverHelper {

    private static FirefoxDriver nullableFirefoxDriver() {

        Path path = Paths.get("lib", "geckodriver");
        if (!path.toFile().exists()) return null;

        final String pathToDriver = path.toAbsolutePath().toString();
        System.setProperty("webdriver.gecko.driver", pathToDriver);

        try {
            return new FirefoxDriver();
        } catch (Exception e) {
            return null;
        }
    }

    private static ChromeDriver nullableChromeDriver() {
        Path path = Paths.get("lib", "chromedriver");
        if (!path.toFile().exists()) return null;

        final String pathToDriver = path.toAbsolutePath().toString();
        System.setProperty("webdriver.chrome.driver", pathToDriver);

        try {
            return new ChromeDriver();
        } catch (Exception e) {
            return null;
        }
    }

    private static SafariDriver nullableSafariDriver() {
        try {
            return new SafariDriver();
        } catch (Exception e) {
            return null;
        }
    }

    private static EdgeDriver nullableEdgeDriver() {
        try {
            return new EdgeDriver();
        } catch (Exception e) {
            return null;
        }
    }

    private static OperaDriver nullableOperaDriver() {
        try {
            return new OperaDriver();
        } catch (Exception e) {
            return null;
        }
    }

    private static InternetExplorerDriver nullableExplorerDriver() {
        try {
            return new InternetExplorerDriver();
        } catch (Exception e) {
            return null;
        }
    }

    public static WebDriver getInstalledDriver() {

        WebDriver driver;

        if ((driver = nullableFirefoxDriver()) != null) return driver;
        if ((driver = nullableChromeDriver()) != null) return driver;
        if ((driver = nullableSafariDriver()) != null) return driver;


        throw new IllegalStateException("Cannot determine your browser/driver");
    }

}
