package com.themoffster.selenium.model;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DriverManager {

    private static final Logger LOGGER = Logger.getLogger(DriverManager.class);
    private WebDriver driver;

    public DriverManager(@Value("${browserType}") BrowserType browserType) {
        this.driver = initWebDriver(browserType);
    }

    private WebDriver initWebDriver(BrowserType browserType) {
        if(driver == null) {
            switch (browserType.getBrowserType()) {
                case "safari":
                    driver = new SafariDriver();
                    LOGGER.info("Using Safari browser");
                    break;
                case "chrome":
                    driver = new ChromeDriver();
                    LOGGER.info("Using Chrome browser");
                    break;
                case "firefox":
                    System.setProperty("webdriver.gecko.driver","/Applications/Firefox.app/Contents/MacOS/firefox-bin");
                    driver = new FirefoxDriver();
                    LOGGER.info("Using Firefox browser");
                    break;
                default:
                    LOGGER.info("Using default (Safari) browser");
                    driver = new SafariDriver();
            }
            driver.manage().window().maximize();
        }
        return driver;
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public void openURL(String url) {
        driver.get(url);
    }

    public boolean isPageLoaded(int timeout, WebElement element) {
        boolean isLoaded = true;
        Wait wait = new WebDriverWait(driver, timeout);
        ExpectedCondition<WebElement> condition = ExpectedConditions.visibilityOf(element);
        try {
            wait.until(condition);
        } catch(TimeoutException ex) {
            LOGGER.error("Page wasn't loaded after timeout of "+timeout+" secs", ex);
            isLoaded = false;
        } catch(UnsupportedCommandException ex) { //FIXME Safari driver waits aren't working
            LOGGER.error("Unsupported command", ex);
            isLoaded = false;
        }
        return isLoaded;
    }

    public void closeDriver() {
        driver.close();
    }

    /**
     * Quits the webdriver.
     */
    public void quitDriver() {
        driver.quit();
    }
}
