package com.tallence.cucumber.steps;

import de.telekom.test.bddwebapp.frontend.page.Page;
import de.telekom.test.bddwebapp.frontend.steps.SeleniumSteps;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class CommonSteps extends SeleniumSteps {

    @Autowired
    private StoryInteraction storyInteraction;


    @Before
    public void beforeScenario() {
        webDriverWrapper.getDriver().manage().deleteAllCookies();
        webDriverWrapper.getDriver().manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }

    @Then("the $pageClassName is shown")
    public void waitUntilPageIsShown(String pageClassName) {
        try {
            createExpectedPage((Class<? extends Page>) this.getClass().forName("com.tallence.bddtest.pages." + pageClassName));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("A Page class with the given Name does not exist.", e);
        }
    }

//    @Given("the cache filled with $map")
//    public void cacheFilled(JsonMap jsonMap) {
//        jsonMap.keySet().forEach(key -> {
//            storyInteraction.remember((String) key, jsonMap.get(key));
//        });
//    }
}
