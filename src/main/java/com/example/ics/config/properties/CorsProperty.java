package com.example.ics.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix ="cors")
public class CorsProperty {
    private List allowedOrigins;
    private List allowedMethods;
    private List allowedHeaders;
    private Long maxAge;
}
