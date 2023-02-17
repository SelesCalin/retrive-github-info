package com.example.githubretrieve.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
    public static final String BEARER = "Bearer ";

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder, GitHubApiProperties properties) {
        return webClientBuilder.baseUrl(properties.getBaseUrl())
                .defaultHeaders(httpHeaders -> {
                    if (properties.getToken() != null) {
                        httpHeaders.add(HttpHeaders.AUTHORIZATION, BEARER + properties.getToken());
                    }
                    httpHeaders.add(HttpHeaders.ACCEPT, properties.getAcceptHeader());
                    httpHeaders.add(properties.getVersionHeader(), properties.getVersion());
                })
                .build();
    }
}
