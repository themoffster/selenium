package com.themoffster.selenium.pages;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertFalse;

@ContextConfiguration(locations = {"classpath:spring.xml"})
public class YellHomepageStepDefs {

    @Autowired
    private YellHomepage homepage;

    @Given("^I am on the https://www.yell.com/ webpage$")
    public void onSearchPage() throws Throwable {
        homepage.openPage();
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
        homepage.clickGo();
    }

    @Then("^I should be taken to a results page$")
    public void takenToResults() throws Throwable {
        assertFalse(false); //TODO
    }

    @After
    public void tearDown() {
        homepage.closeDriver();
        homepage.quitDriver();
    }
}
