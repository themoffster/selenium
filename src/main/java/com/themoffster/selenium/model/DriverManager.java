package com.themoffster.selenium.model;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DriverManager {

    private static final Logger LOGGER = Logger.getLogger(DriverManager.class);
    private WebDriver driver;
    @Value("${browserType}")
    private BrowserType browserType;
    @Value("${firefox.path}")
    private String firefoxPath;

    /**
     * Instantiates a new WebDriver of the appropriate browser type, registers a shutdown hook so that the browser can
     * be cleanly quit and maximises the browser to full screen.
     *
     * @return the newly created WebDriver
     */
    public WebDriver createWebDriver() {
        try {
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
                    System.setProperty("webdriver.gecko.driver", firefoxPath);
                    driver = new FirefoxDriver();
                    LOGGER.info("Using Firefox browser");
                    break;
                default:
                    LOGGER.info("Using default (Firefox) browser");
                    driver = new FirefoxDriver();
            }
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup(driver)));
        }
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * Closes the WebDriver.
     */
    public void closeDriver() {
        driver.close();
    }


    /**
     * A private inner class who's sole job is to quit the WebDriver at the correct time.
     */
    private static class BrowserCleanup implements Runnable {

        private static WebDriver driver;

        private BrowserCleanup(WebDriver driver) {
            BrowserCleanup.driver = driver;
        }

        @Override
        public void run() {
            LOGGER.info("Quitting the browser");
            quitDriver();
        }

        /**
         * Quits the WebDriver.
         */
        public void quitDriver() {
            driver.quit();
        }
    }
}
