package com.themoffster.selenium.pages;

import com.themoffster.selenium.model.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class YellSearchResults extends AutomatablePage {

    private static final int PAGE_LOAD_TIMEOUT = 5;
    @FindBy(id="mapExpandOpen")
    private WebElement viewMapButton;

    public YellSearchResults(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public WebElement getPageLoadedElement() {
        return viewMapButton;
    }

    @Override
    protected int getPageLoadTimeout() {
        return PAGE_LOAD_TIMEOUT;
    }
}
