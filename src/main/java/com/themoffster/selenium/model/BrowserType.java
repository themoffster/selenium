package com.themoffster.selenium.model;

public enum BrowserType {

    FIREFOX("firefox"),
    SAFARI("safari"),
    CHROME("chrome");

    private String browserType;

    BrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getBrowserType() {
        return browserType;
    }
}
