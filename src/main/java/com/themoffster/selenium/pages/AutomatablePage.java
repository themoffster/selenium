package com.themoffster.selenium.pages;

import com.themoffster.selenium.model.DriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;


public abstract class AutomatablePage {

    private static final Logger LOGGER = Logger.getLogger(AutomatablePage.class);
    private static final int DEFAULT_PAGE_LOAD_TIMEOUT = 10;
    protected DriverManager driverManager;

    public AutomatablePage(DriverManager driverManager) {
        this.driverManager = driverManager;
    }

    public boolean isPageLoaded() {
        return driverManager.isPageLoaded(getPageLoadTimeout(), getPageLoadedElement());
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
     * Makes the WebDriver open the page.
     *
     * @param url the URL to open in the browser
     */
    public void openPage(String url) {
        driverManager.openURL(url);
    }

    /**
     * Closes the WebDriver.
     */
    public void closeDriver() {
        driverManager.closeDriver();
    }

    /**
     * Quits the WebDriver.
     */
    public void quitDriver() {
        driverManager.quitDriver();
    }
}
