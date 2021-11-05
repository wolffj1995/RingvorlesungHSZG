package com.tallence.jbehave;

import com.tallence.jbehave.config.MetaTag;
import com.tallence.jbehave.config.properties.TestProperties;
import org.jbehave.core.embedder.Embedder;

public class RunAllSmokeTests extends RunAllBddStories {

    @Override
    public Embedder configuredEmbedder() {
        // manipulate to run only Scenarios with meta-tag @smokeTest
        getApplicationContext().getBean(TestProperties.class).jBehaveMetaFilter += getApplicationContext().getBean(TestProperties.class).jBehaveMetaFilter + ";+" + MetaTag.SMOKE_TEST + " *";
        return super.configuredEmbedder();
    }
}
