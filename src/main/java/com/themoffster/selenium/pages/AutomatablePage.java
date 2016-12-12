package com.themoffster.selenium.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class AutomatablePage {

    private static final Logger LOGGER = Logger.getLogger(AutomatablePage.class);
    /**
     * The default timeout used if a page doesn't specify it's own limit on page load time
     */
    private static final int DEFAULT_PAGE_LOAD_TIMEOUT = 10;
    protected WebDriver driver;

    public AutomatablePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Allows the page to determine whether it is loaded or not.<br>
     * The logic for this is to ensure that a specified element is present on the screen.
     *
     * @return true if the designated element is present on the screen, false otherwise
     * @see #getPageLoadedElement()
     */
    public boolean isPageLoaded() {
        return isPageLoaded(getPageLoadTimeout(), getPageLoadedElement());
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
    private boolean isPageLoaded(int timeout, WebElement element) {
        boolean isLoaded = true;
        Wait<WebDriver> wait = new WebDriverWait(driver, timeout);
        ExpectedCondition<WebElement> condition = ExpectedConditions.visibilityOf(element);
        try {
            wait.until(condition);
        } catch (TimeoutException ex) {
            LOGGER.error("Page wasn't loaded after timeout of " + timeout + " secs", ex);
            isLoaded = false;
        } catch (UnsupportedCommandException ex) { //FIXME Safari driver waits don't work
            LOGGER.error("Unsupported command", ex);
            isLoaded = false;
        }
        return isLoaded;
    }

    /**
     * Each page should specify a WebElement which can be checked for presence which will validate whether a page is loaded or not.
     *
     * @return a WebElement which should exist on a page
     */
    public abstract WebElement getPageLoadedElement();

    protected int getPageLoadTimeout() {
        return DEFAULT_PAGE_LOAD_TIMEOUT;
    }

    /**
     * Gets the WebDriver.
     *
     * @return the WebDriver for this class
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Makes the WebDriver open the page.
     *
     * @param url the URL to open in the browser
     */
    public void openPage(String url) {
        driver.get(url);
    }

    /**
     * Closes the WebDriver.
     */
    public void closeDriver() {
        driver.close();
    }
}
