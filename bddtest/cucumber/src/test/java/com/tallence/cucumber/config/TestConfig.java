package com.tallence.cucumber.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"de.telekom.test.bddwebapp", "com.tallence.cucumber"})
@PropertySource("classpath:test_data.properties") // load TestData in SpringContext
public class TestConfig {
}