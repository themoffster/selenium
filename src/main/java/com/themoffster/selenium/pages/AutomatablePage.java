package com.themoffster.selenium.pages;

import com.google.common.base.Function;
import com.sun.istack.internal.NotNull;
import com.themoffster.selenium.model.DriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


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

    public abstract WebElement getPageLoadedElement();

    protected int getPageLoadTimeout() {
        return DEFAULT_PAGE_LOAD_TIMEOUT;
    }

    /**
     * Makes the webdriver open the page.
     * @param url the URL to open in the browser
     */
    public void openPage(String url) {
        driverManager.openURL(url);
    }

    /**
     * Closes the webdriver.
     */
    public void closeDriver() {
        driverManager.closeDriver();
    }

    /**
     * Quits the webdriver.
     */
    public void quitDriver() {
        driverManager.quitDriver();
    }
}
