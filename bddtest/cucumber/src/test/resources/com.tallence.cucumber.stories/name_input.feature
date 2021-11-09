Feature: Name Input

    @smokeTest
    Scenario: 01 - dummy

        Given call index page
        Then the "IndexPage" is shown
        When enter name Spongebob
        When submit name
        Then the "CalculationInputPage" is shown
        Then the name matches Spongebob on inputPage

    Scenario: 02 - Enter name and goto Calculation page

        Given call index page
        Then the "IndexPage" is shown
        When enter name Maik
        When submit name
        Then the "CalculationInputPage" is shown
        Then the name matches Maik on inputPage

    Scenario: 03 - StoryInteraction

        Given call index page
        Given the cache filled with jsonValues
    """
    {"coolName":"Eugene H. Krabs"}
    """
        Then the "IndexPage" is shown
        When enter name $coolName
        When submit name
        Then the "CalculationInputPage" is shown
        Then the name matches $coolName on inputPage

    Scenario: 04 - Composites

        Given call index page
        Then the "IndexPage" is shown
        When enter and submit name $test_data.properties.name2 on indexPage
        Then the "CalculationInputPage" is shown
        Then the name matches $test_data.properties.name2 on inputPage