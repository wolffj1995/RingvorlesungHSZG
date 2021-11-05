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

    @When(value = "enter name $name")
    public void enterName(final String name) {
        IndexPage indexPage = getCurrentPage();
        indexPage.setNameInput(name);
    }

    @When("submit name")
    public void submitName() {
        IndexPage indexPage = getCurrentPage();
        indexPage.submit();
    }

    @Given("the index page is called")
    public void indexPageCalled() {
        webDriverWrapper.getDriver().get(testProperties.getAppHostIncludingPort() + testProperties.getAppContextPath());
    }
}
