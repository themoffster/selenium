package com.themoffster.selenium.pages;

import com.themoffster.selenium.model.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class YellHomepage extends AutomatablePage {

    @FindBy(id="search_keyword")
    private WebElement businessTextfield;
    @FindBy(id="search_location")
    private WebElement areaTextfield;
    @FindBy(xpath="//button[contains(text(),'Search')]")
    private WebElement searchButton;

    public YellHomepage(DriverManager driverManager) {
        super(driverManager);
        PageFactory.initElements(driverManager.getWebDriver(), this);
    }

    @Override
    public WebElement getPageLoadedElement() {
        return businessTextfield;
    }

    public void typeBusiness(String text) {
        businessTextfield.sendKeys(text);
    }

    public void typeArea(String text) {
        areaTextfield.sendKeys(text);
    }

    public YellSearchResults clickGo() {
        searchButton.click();
        return new YellSearchResults(driverManager);
    }
}
