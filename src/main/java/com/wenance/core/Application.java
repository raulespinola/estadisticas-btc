package com.wenance.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableScheduling
public class Application {

    @Bean
    public WebClient.Builder getWebClientBuilder(){
        return WebClient.builder();
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
