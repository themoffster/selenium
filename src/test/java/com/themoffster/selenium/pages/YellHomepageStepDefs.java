package com.themoffster.selenium.pages;

import com.themoffster.selenium.model.DriverManager;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertTrue;

@ContextConfiguration(locations = {"classpath:spring.xml"})
public class YellHomepageStepDefs {

    @Autowired
    private DriverManager driverManager;
    private YellHomepage homepage;
    private YellSearchResults resultsPage;

    @Given("^I am on the \"([^\"]*)\" webpage$")
    public void onSearchPage(String url) throws Throwable {
        homepage = new YellHomepage(driverManager);
        homepage.openPage(url);
        //assertTrue(homepage.isPageLoaded()); //FIXME Safari driver waits aren't working
    }

    @When("^I type \"([^\"]*)\" as a business to search for$")
    public void typeBusiness(String text) throws Throwable {
        homepage.typeBusiness(text);
    }

    @And("^I type \"([^\"]*)\" as the area$")
    public void typeArea(String text) throws Throwable {
        homepage.typeArea(text);
    }

    @And("^I click the Go button$")
    public void clickGo() throws Throwable {
        resultsPage = homepage.clickGo();
    }

    @Then("^I should be taken to a results page$")
    public void takenToResults() throws Throwable {
        assertTrue(resultsPage.isPageLoaded()); //FIXME this fails currently because the Safari driver can't deal with waits
    }

    @After
    public void tearDown() {
        homepage.closeDriver();
        homepage.quitDriver();
    }
}
