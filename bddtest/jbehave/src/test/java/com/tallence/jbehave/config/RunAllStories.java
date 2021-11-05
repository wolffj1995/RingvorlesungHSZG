package com.tallence.jbehave.config;

import com.github.valfirst.jbehave.junit.monitoring.JUnitReportingRunner;
import de.telekom.test.bddwebapp.jbehave.steps.ScannedStepsFactory;
import de.telekom.test.bddwebapp.jbehave.stories.config.FaultTolerantStoryPathResolver;
import de.telekom.test.bddwebapp.jbehave.stories.config.ScannedStoryPaths;
import de.telekom.test.bddwebapp.jbehave.stories.config.ScreenshotStoryReporterBuilder;
import de.telekom.test.bddwebapp.jbehave.stories.customizing.CurrentStoryEmbedderMonitor;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.MarkUnmatchedStepsAsPending;
import org.jbehave.core.steps.StepFinder;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;

import java.util.List;

@RunWith(JUnitReportingRunner.class)
public abstract class RunAllStories extends JUnitStories implements ScannedStepsFactory, ScreenshotStoryReporterBuilder, FaultTolerantStoryPathResolver, ScannedStoryPaths {
    public RunAllStories() {
    }

    public Configuration configuration() {
        Configuration configuration = new MostUsefulConfiguration();
        configuration.useStepCollector(
                new MarkUnmatchedStepsAsPending(
                        new StepFinder(
                                new StepFinder.ByLevenshteinDistance())));

        configuration.useStoryReporterBuilder(new JBehaveContextStoryReporterBuilder(configuration.storyReporterBuilder()));

        configuration.useStoryPathResolver(
                this.removeStoryFromClassNameStoryPathResolver());
        return configuration;
    }

    public InjectableStepsFactory stepsFactory() {
        return this.scannedStepsFactory();
    }

    public List<String> storyPaths() {
        return this.scannedStoryPaths();
    }

    public Embedder configuredEmbedder() {
        Embedder embedder = super.configuredEmbedder();
        embedder.useEmbedderMonitor(new CurrentStoryEmbedderMonitor(this.getApplicationContext()));
        return embedder;
    }

    public void run() {
        // TODO - jonas.wolff - 04.11.21: todo fix after bib update
//        if (this.apiOnly()) {
//            CustomizingStories storyClasses = (CustomizingStories) this.getApplicationContext().getBean(CustomizingStories.class);
//            storyClasses.setApiOnlyForAllStories(true);
//        }

        super.run();
    }

    public boolean apiOnly() {

        // TODO - jonas.wolff - 04.11.21: todo fix after bib update
//        return Arrays.stream(this.getClass().getAnnotations()).anyMatch((annotation) -> {
//            return annotation.annotationType().equals(ApiOnly.class);
//        });
        return false;
    }

    public abstract ApplicationContext getApplicationContext();
}

