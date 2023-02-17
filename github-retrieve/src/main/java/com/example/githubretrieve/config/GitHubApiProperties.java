package com.example.githubretrieve.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "github")
@Setter
@Getter
public class GitHubApiProperties {

    private String token;
    private String baseUrl;
    private String versionHeader;
    private String version;
    private String acceptHeader;

}
