package com.tallence.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:"},
        plugin = {"html:target/cucumber-html-report.html", "json:target/cucumber-json-report.json"},
        glue = {"com.tallence.cucumber.steps"},
        tags = "not @ignore")
public class RunAll {
}
