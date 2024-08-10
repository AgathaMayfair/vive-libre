package com.vivelibre.tokenretriever.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiConfig {

    public static final String SEED = "1274b68a-7f95-47";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
