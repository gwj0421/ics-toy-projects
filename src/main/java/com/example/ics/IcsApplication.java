package com.example.ics;

import com.example.ics.config.properties.AppProperty;
import com.example.ics.config.properties.CorsProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AppProperty.class, CorsProperty.class})
public class IcsApplication {

    public static void main(String[] args) {
        SpringApplication.run(IcsApplication.class, args);
    }

}
