package com.themoffster.selenium.pages;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = { "pretty" },
        features = { "src/test/resources/com/themoffster/selenium/pages" },
        tags = "~@wip")
public class RunTest {

}