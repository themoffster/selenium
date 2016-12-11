package com.themoffster.selenium.pages;

import com.themoffster.selenium.model.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class YellHomepage extends AutomatablePage {

    @FindBy(id = "search_keyword")
    private WebElement businessTextfield;
    @FindBy(id = "search_location")
    private WebElement areaTextfield;
    @FindBy(xpath = "//button[contains(text(),'Search')]")
    private WebElement searchButton;

    public YellHomepage(DriverManager driverManager) {
        super(driverManager);
        PageFactory.initElements(driverManager.getWebDriver(), this);
    }

    @Override
    public WebElement getPageLoadedElement() {
        return businessTextfield;
    }

    /**
     * Puts forcus on the business textfield, clears it, then types in the argument text.
     *
     * @param text the text to type in the business textfield
     */
    public void typeBusiness(String text) {
        businessTextfield.clear();
        businessTextfield.sendKeys(text);
    }

    /**
     * Puts forcus on the area textfield, clears it, then types in the argument text.
     *
     * @param text the text to type in the area textfield
     */
    public void typeArea(String text) {
        areaTextfield.clear();
        areaTextfield.sendKeys(text);
    }

    /**
     * Clicks the search button on the form.
     *
     * @return the YellSearch results page
     */
    public YellSearchResults clickGo() {
        searchButton.click();
        return new YellSearchResults(driverManager);
    }
}
