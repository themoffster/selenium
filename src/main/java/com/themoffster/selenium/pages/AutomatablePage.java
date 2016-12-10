package com.themoffster.selenium.pages;

import com.themoffster.selenium.model.BrowserType;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.concurrent.TimeUnit;


public abstract class AutomatablePage {

    private static final Logger LOGGER = Logger.getLogger(AutomatablePage.class);
    protected WebDriver driver;

    /**
     * Ensures that a WebDriver is created for the relevant browser type.<br>
     * If no browser type is specified, or the value is invalid, then a default Safari browser is used.
     * @param browserType the BrowserType to use
     * @return a WebDriver setup for the argument browser type
     */
    protected WebDriver createDriver(BrowserType browserType) {
        WebDriver createdDriver;
        switch(browserType.getBrowserType()) {
            case "safari" :
                createdDriver = new SafariDriver();
                LOGGER.info("Using Safari browser");
                break;
            case "chrome" :
                createdDriver = new ChromeDriver();
                LOGGER.info("Using Chrome browser");
                break;
            case "firefox" :
                createdDriver = new FirefoxDriver();
                LOGGER.info("Using Firefox browser");
                break;
            default :
                LOGGER.info("Using default (Safari) browser");
                createdDriver = new SafariDriver();
        }
        createdDriver.manage().window().maximize();
        createdDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return createdDriver;
    }

    /**
     * Makes the webdriver open the page.
     */
    public void openPage() {
        driver.get(getUrl());
    }

    /**
     * The URL of the page.
     * @return the URL of the page
     */
    public abstract String getUrl();

    /**
     * Closes the webdriver.
     */
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
