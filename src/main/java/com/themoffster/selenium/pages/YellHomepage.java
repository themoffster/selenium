package com.themoffster.selenium.pages;

import com.themoffster.selenium.model.BrowserType;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class YellHomepage extends AutomatablePage {

    @FindBy(id="search_keyword")
    private WebElement businessTextfield;
    @FindBy(id="search_location")
    private WebElement areaTextfield;
    @FindBy(xpath="//button[contains(text(),'Search')]")
    private WebElement searchButton;

    @Autowired
    public YellHomepage(@Value("${browserType}") BrowserType browserType) { //TODO ensure this can be overridden by -D switch
        this.driver = createDriver(browserType);
        PageFactory.initElements(driver, this);
    }

    @Override
    public String getUrl() {
        return "https://www.yell.com/";
    }

    public void typeBusiness(String text) {
        businessTextfield.sendKeys(text);
    }

    public void typeArea(String text) {
        areaTextfield.sendKeys(text);
    }

    public void clickGo() {
        searchButton.click();
    }
}
