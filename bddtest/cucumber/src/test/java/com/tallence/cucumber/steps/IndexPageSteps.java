package com.tallence.cucumber.steps;

import com.tallence.cucumber.config.TestProperties;
import com.tallence.cucumber.pages.IndexPage;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.test.bddwebapp.frontend.steps.SeleniumSteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class IndexPageSteps extends SeleniumSteps {
    @Autowired
    protected WebDriverWrapper webDriverWrapper;
    @Autowired
    private TestProperties testProperties;

    @When(value = "enter name {interactionKey}")
    public void enterName(final String name) {
        IndexPage indexPage = getCurrentPage();
        indexPage.setNameInput(name);
    }

    @When("submit name")
    public void submitName() {
        IndexPage indexPage = getCurrentPage();
        indexPage.submit();
    }

    @When("enter and submit name {interactionKey} on indexPage")
    public void enterAndSubmitName(final String name) {
        enterName(name);
        submitName();
    }

    @Given("call index page")
    public void indexPageCalled() {
        webDriverWrapper.getDriver().get(testProperties.getAppHostIncludingPort() + testProperties.getAppContextPath());
    }
}
