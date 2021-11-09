Feature: Advanced BDD

Scenario: 01 - StoryInteraction

    Given call index page
    Given the cache filled with jsonValues
    """
    { "coolName":"Eugene H. Krabs" }
    """
    Then the "IndexPage" is shown
    When enter name $coolName
    When submit name
    Then the "CalculationInputPage" is shown
    Then the name matches $coolName on inputPage

Scenario: 02 - Composites

    Given call index page
    Then the "IndexPage" is shown
    When enter and submit name $test_data.properties.name2 on indexPage
    Then the "CalculationInputPage" is shown
    Then the name matches $test_data.properties.name2 on inputPage

Scenario Outline: 03 - ExampleTables

    Given call index page
    Then the "IndexPage" is shown
    When enter name <name>
    When submit name
    Then the "CalculationInputPage" is shown
    Then the name matches <name> on inputPage
    When input field 1 "<firstAdd>"
    And input field 2 "<secondAdd>"
    And input field 3 "<thridAdd>"
    And submit fields
    Then the "CalculationResultPage" is shown
    Then the name matches <name> on resultPage
    Then the result equals "<total>" on resultPage

    Examples:
        | name       | firstAdd | secondAdd | thridAdd | total   |
        | Bugs Bunny | 1        | 1         | 1        | 3       |
        | Taz        | 10       | 20        | 30       | 60      |
        | Tweety     | 100      | 500       | 400      | 1000    |
        | Daffy Duck | 10000    | 900000    | 8000000  | 8910000 |

@smokeTest
Scenario: 04 - Meta Info as filter parameter

    Given call index page
    Then the "IndexPage" is shown
    When enter name Spongebob
    When submit name
    Then the "CalculationInputPage" is shown
    Then the name matches Spongebob on inputPage

Scenario: 05 - Parameter Converter

    Given call index page
    Given the cache filled with jsonValues
    """
    { "coolName": "$test_data.properties.name1$" }
    """
    Then the "IndexPage" is shown
    When enter name $coolName
    When submit name
    Then the "CalculationInputPage" is shown
    Then the name matches $coolName on inputPage