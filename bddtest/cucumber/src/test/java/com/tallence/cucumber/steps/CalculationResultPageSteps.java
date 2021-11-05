package com.tallence.cucumber.steps;

import com.tallence.cucumber.pages.CalculationResultPage;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.test.bddwebapp.frontend.steps.SeleniumSteps;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class CalculationResultPageSteps extends SeleniumSteps {
    @Autowired
    protected WebDriverWrapper webDriverWrapper;

    @Then("the name matches $name on resultPage")
    public void nameMatches(final String name) {
        CalculationResultPage calculationResultPage = getCurrentPage();
        assertTrue(calculationResultPage.nameMatches(Pattern.compile(name)));
    }

    @Then("the result matches $result on resultPage")
    public void resultMatches(final String result) {
        CalculationResultPage calculationResultPage = getCurrentPage();
        assertTrue(calculationResultPage.resultMatches(Pattern.compile(result)));
    }

    // Pattern Variant
    @Then("the result {equals|is} $result on resultPage")
    public void resultEquals(final String result) {
        CalculationResultPage calculationResultPage = getCurrentPage();
        assertTrue(calculationResultPage.resultEquals(result));
    }
}
