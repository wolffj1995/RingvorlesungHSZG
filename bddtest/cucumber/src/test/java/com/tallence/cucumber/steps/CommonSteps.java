package com.tallence.cucumber.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import de.telekom.test.bddwebapp.frontend.page.Page;
import de.telekom.test.bddwebapp.frontend.steps.SeleniumSteps;
import de.telekom.test.bddwebapp.interaction.StoryInteraction;
import io.cucumber.java.Before;
import io.cucumber.java.DocStringType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CommonSteps extends SeleniumSteps {

    private static final String TEST_PROPERTIES_NAME = "test";
    private static final String TEST_DATA_PROPERTIES_NAME = "test_data";
    @Autowired
    private StoryInteraction storyInteraction;

    @Autowired
    private Environment environment;

    @Before
    public void beforeScenario() {
        String[] activeProfileConfig = environment.getActiveProfiles();
        List<String> activeProfiles = Arrays.asList(activeProfileConfig);

        // copy spring enviroment properties in storyInteraction parameter to be accessible there
        copyPropertySource("test.properties", TEST_PROPERTIES_NAME + ".properties");

        copyPropertySource("test_data.properties", TEST_DATA_PROPERTIES_NAME + ".properties");

        for (String activeProfile : activeProfiles) {
            copyPropertySource("test.properties", TEST_PROPERTIES_NAME + "-" + activeProfile + ".properties");
        }

        webDriverWrapper.getDriver().manage().deleteAllCookies();
        webDriverWrapper.getDriver().manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }

    @Then("the {string} is shown")
    public void waitUntilPageIsShown(String pageClassName) {
        try {
            createExpectedPage((Class<? extends Page>) this.getClass().forName("com.tallence.cucumber.pages." + pageClassName));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("A Page class with the given Name does not exist.", e);
        }
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    @DocStringType
    public JsonNode json(String docString) throws JsonProcessingException {
        return objectMapper.readValue(docString, JsonNode.class);
    }

    @Given("the cache filled with jsonValues")
    public void cacheFilled(JsonNode jsonNode) {
        saveJsonObject("", jsonNode);
    }

    private void saveJsonObject(String prefix, JsonNode currentNode) {
        if (currentNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) currentNode;
            Iterator<JsonNode> node = arrayNode.elements();
            int index = 1;
            while (node.hasNext()) {
                saveJsonObject(!prefix.isEmpty() ? prefix + "-" + index : String.valueOf(index), node.next());
                index += 1;
            }
        } else if (currentNode.isObject()) {
            currentNode.fields().forEachRemaining(entry -> saveJsonObject(!prefix.isEmpty() ? prefix + "-" + entry.getKey() : entry.getKey(), entry.getValue()));
        } else {
            String value = currentNode.toString().replaceAll("\"", "");
            if (value.startsWith("$") && value.endsWith("$"))
                value = storyInteraction.recall(value.replace("$",""));
            log.info(prefix + ": " + value);
            storyInteraction.remember(prefix, value);
        }
    }

    private void copyPropertySource(String prefix, String propertySourceName) {
        PropertiesPropertySource propertySource = getPropertySourceByResourceName(propertySourceName);
        storyInteraction.remember(prefix, propertySource.getSource());
    }

    private PropertiesPropertySource getPropertySourceByResourceName(String resourceName) {
        return (PropertiesPropertySource) ((ConfigurableEnvironment) environment).getPropertySources().stream().filter(propertySource -> propertySource.getName().contains(resourceName)).findFirst().orElseThrow();
    }
}
