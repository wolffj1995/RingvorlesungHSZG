package com.tallence.jbehave.config;

import com.gargoylesoftware.htmlunit.WebClient;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomHtmlUnitDriver extends HtmlUnitDriver {
    @Override
    protected WebClient modifyWebClient(WebClient client) {
        log.info("uses modified htmlunit driver");
        WebClient modifiedClient = super.modifyWebClient(client);
        modifiedClient.getOptions().setThrowExceptionOnScriptError(false); // see here
        return modifiedClient;
    }
}
