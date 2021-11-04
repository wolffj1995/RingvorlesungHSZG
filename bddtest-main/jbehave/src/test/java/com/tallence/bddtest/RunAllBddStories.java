package com.tallence.bddtest;

import com.tallence.bddtest.config.RunAllStories;
import com.tallence.bddtest.config.AbstractBddStory;
import com.tallence.bddtest.config.ApplicationContextProvider;
import com.tallence.bddtest.config.ProxyInstanceStepsFactory;
import de.telekom.test.bddwebapp.jbehave.steps.Steps;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class RunAllBddStories extends RunAllStories {

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.getApplicationContext();
    }

    @Override
    public Configuration configuration() {
        Configuration configuration = super.configuration();
        configuration = AbstractBddStory.configureConfiguration(configuration, getApplicationContext());
        return configuration;
    }


    @Override
    public Embedder configuredEmbedder() {
        Embedder embedder = super.configuredEmbedder();
        embedder = AbstractBddStory.configureEmbedder(embedder, getApplicationContext());
        return embedder;
    }


    @Override
    public InjectableStepsFactory stepsFactory() {
        List<Object> steps = new ArrayList(this.getApplicationContext().getBeansWithAnnotation(Steps.class).values());
        return new ProxyInstanceStepsFactory(this.configuration(), steps);
    }
}
