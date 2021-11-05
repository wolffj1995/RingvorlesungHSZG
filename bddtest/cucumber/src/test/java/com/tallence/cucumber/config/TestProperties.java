package com.tallence.cucumber.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class TestProperties {
    @Value("${app.hostIncludingPort}")
    private String appHostIncludingPort;
    @Value("${app.contextPath}")
    private String appContextPath = "";
}
