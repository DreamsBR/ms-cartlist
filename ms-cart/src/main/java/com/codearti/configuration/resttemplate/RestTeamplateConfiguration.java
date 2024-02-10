package com.codearti.configuration.resttemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTeamplateConfiguration {

    @Bean
    public RestTemplate registrarResTemplate(){
        return new RestTemplate();
    }

}
