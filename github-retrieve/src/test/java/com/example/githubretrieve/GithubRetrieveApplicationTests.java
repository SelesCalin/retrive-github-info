package com.example.githubretrieve;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GithubRetrieveApplicationTests {

    @Test
    void contextLoads(ApplicationContext context) {
        assertThat(context).isNotNull();
    }

}
