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
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.UnderscoredToCapitalized;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.FreemarkerViewGenerator;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

@RunWith(JUnitReportingRunner.class)
public abstract class RunAllStories extends JUnitStories implements ScannedStepsFactory, ScreenshotStoryReporterBuilder, FaultTolerantStoryPathResolver, ScannedStoryPaths {
    public RunAllStories() {
    }

    private static final CrossReference xref = new CrossReference();

    public Configuration configuration() {
        Configuration configuration = new MostUsefulConfiguration();
        Properties viewResources = new Properties();
        viewResources.put("decorateNonHtml", "false");
        return configuration
                .useViewGenerator(new FreemarkerViewGenerator(new UnderscoredToCapitalized(), FreemarkerViewGenerator.class, StandardCharsets.UTF_8))
                .useStoryReporterBuilder(
                        new StoryReporterBuilder()
                                .withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass()))
                                .withDefaultFormats().withPathResolver(new FilePrintStreamFactory.ResolveToPackagedName())
                                .withViewResources(viewResources).withFormats(Format.CONSOLE, Format.HTML)
                                .withFailureTrace(true).withFailureTraceCompression(true).withCrossReference(xref));
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

    public abstract ApplicationContext getApplicationContext();
}

