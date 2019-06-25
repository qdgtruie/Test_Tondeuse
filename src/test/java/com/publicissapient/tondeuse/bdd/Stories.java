package com.publicissapient.tondeuse.bdd;


import com.github.valfirst.jbehave.junit.monitoring.JUnitReportingRunner;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.failures.PassingUponPendingStep;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.junit.runner.RunWith;

@RunWith(JUnitReportingRunner.class)
public class Stories extends JUnitStory {


    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new Steps());
    }

    @Override
    public Configuration configuration() {
        Configuration configuration = new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath())
                .useStoryReporterBuilder(
                        new StoryReporterBuilder()
                                .withDefaultFormats()
                                .withFormats(Format.CONSOLE, Format.TXT))
                .usePendingStepStrategy(new FailingUponPendingStep());

        configuration
                .parameterConverters()
                .addConverters(new ParameterConverters.EnumConverter());

        return configuration;
    }

}
