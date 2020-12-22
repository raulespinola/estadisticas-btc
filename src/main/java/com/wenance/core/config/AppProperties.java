package com.wenance.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("app.services")
public class AppProperties {
    private String exchangeUrl;
    private String exchangeEndpoint;
}
