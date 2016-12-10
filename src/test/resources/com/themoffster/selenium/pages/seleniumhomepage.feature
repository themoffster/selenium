Feature: Performing a search on the http://www.yell.com website

  The page requires a business type and location to be entered before performing a search

  Scenario: Open sport homepage from site homepage
    Given I am on the "https://www.yell.com/" webpage
    When I type "plumber" as a business to search for
    And I type "glasgow" as the area
    And I click the Go button
    Then I should be taken to a results page