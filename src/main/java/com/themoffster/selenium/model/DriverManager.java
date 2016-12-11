package com.themoffster.selenium.model;

import org.apache.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DriverManager {

    private static final Logger LOGGER = Logger.getLogger(DriverManager.class);
    private WebDriver driver;
    @Value("${browserType}")
    private BrowserType browserType;
    @Value("${firefox.path}")
    private String firefoxPath;

    @PostConstruct
    public void init() {
        this.driver = initWebDriver();
    }

    private WebDriver initWebDriver() {
        if (driver == null) {
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
                    LOGGER.info("Using default (Safari) browser");
                    driver = new SafariDriver();
            }
            driver.manage().window().maximize();
        }
        return driver;
    }

    /**
     * Gets the current WebDriver assosciated with this DriverManager.
     *
     * @return the current WebDriver
     */
    public WebDriver getWebDriver() {
        return driver;
    }

    /**
     * Makes the WebDriver open the specified URL in the browser.
     *
     * @param url the URL to open in the browser
     */
    public void openURL(String url) {
        driver.get(url);
    }

    /**
     * To verify a page has loaded correctly, a specific WebElement on the page should be looked for.
     * Each page specifies which element it should check for through the AutomatablePage.getPageLoadedElement() and each
     * page's specific timeout comes from the getPageLoadTimeout method.<br>
     * If the WebElement is not found before the timeout expires, the page hasn't loaded in time and a TimeoutException
     * is thrown and caught. The SarafiDriver currently does not cater for waits very well and thrown an
     * UnsupportedCommandException. This exception is also caught and tidily makes the method return a boolean rather
     * than allowing the error to propagate up the stack.
     *
     * @param timeout the number of seconds the page should wait for before throwing a TimeoutException
     * @param element the WebElement to poll the page for
     * @return true if the WebElement was found within the timeout, false otherwise
     */
    public boolean isPageLoaded(int timeout, WebElement element) {
        boolean isLoaded = true;
        Wait wait = new WebDriverWait(driver, timeout);
        ExpectedCondition<WebElement> condition = ExpectedConditions.visibilityOf(element);
        try {
            wait.until(condition);
        } catch (TimeoutException ex) {
            LOGGER.error("Page wasn't loaded after timeout of " + timeout + " secs", ex);
            isLoaded = false;
        } catch (UnsupportedCommandException ex) { //FIXME Safari driver waits aren't working
            LOGGER.error("Unsupported command", ex);
            isLoaded = false;
        }
        return isLoaded;
    }

    /**
     * Closes the WebDriver.
     */
    public void closeDriver() {
        driver.close();
    }

    /**
     * Quits the WebDriver.
     */
    public void quitDriver() {
        driver.quit();
    }
}
