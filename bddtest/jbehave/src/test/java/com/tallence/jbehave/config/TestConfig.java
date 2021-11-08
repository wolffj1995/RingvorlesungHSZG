package com.tallence.jbehave.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"de.telekom.test.bddwebapp", "com.tallence.jbehave"
})
@EnableAspectJAutoProxy
public class TestConfig {
}
