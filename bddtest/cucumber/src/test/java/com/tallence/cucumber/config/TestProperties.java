package com.tallence.cucumber.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
@ConfigurationProperties
@PropertySource("classpath:test.properties")
public class TestProperties {
    @Value("${app.hostIncludingPort}")
    private String appHostIncludingPort;

    @Value("${app.contextPath}")
    private String appContextPath = "";
}
