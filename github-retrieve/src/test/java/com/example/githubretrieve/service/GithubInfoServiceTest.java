package com.example.githubretrieve.service;


import com.example.githubretrieve.config.GitHubApiProperties;
import com.example.githubretrieve.dto.BranchInfoDTO;
import com.example.githubretrieve.dto.GithubRepoInfoDTO;
import com.example.githubretrieve.exception.UserNotFoundException;
import com.example.githubretrieve.model.*;
import com.example.githubretrieve.service.GithubInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class GithubInfoServiceTest {

    @Autowired
    public GithubInfoService githubInfoService;
    @Autowired
    public GitHubApiProperties properties;

    private static MockWebServer mockWebServer;

    private static ObjectMapper objectMapper;


    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry r) {
        r.add("github.baseUrl", () -> "http://localhost:" + mockWebServer.getPort());
    }

    @BeforeAll
    static void setUp() throws IOException {
        objectMapper = new ObjectMapper();
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getGithubInfo_userNotFound() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(404));

        var exception = assertThrows(UserNotFoundException.class, () -> githubInfoService.getGithubRepoInfo("test"));

        assertNotNull(exception);
    }

    @Test
    void getGithubInfo_shouldReturnRepoInfo() throws IOException {
        //given
        var username = "test";
        UserInfo userInfoMock = new UserInfo();
        userInfoMock.setPublicRepos(5);
        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(userInfoMock))
                .addHeader("Content-Type", "application/json"));
        GitRepositoryOwner owner = new GitRepositoryOwner();
        owner.setLogin(username);
        GitRepository gitRepository = new GitRepository();
        gitRepository.setName("repo-test");
        gitRepository.setFork(false);
        gitRepository.setOwner(owner);

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(List.of(gitRepository)))
                .addHeader("Content-Type", "application/json"));

        Commit commit = new Commit();
        commit.setSha("sha1");
        Branch branch = new Branch();
        branch.setName("master");
        branch.setCommit(commit);

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(List.of(branch)))
                .addHeader("Content-Type", "application/json"));

        GithubRepoInfoDTO expectedResult = GithubRepoInfoDTO.builder().name("repo-test").ownerLogin(username)
                .branches(List.of(BranchInfoDTO.builder()
                        .name("master")
                        .lastCommitSha("sha1")
                        .build()))
                .build();

        //when
        Flux<GithubRepoInfoDTO> response = githubInfoService.getGithubRepoInfo(username);


        //then
        StepVerifier.create(response)
                .expectNextMatches(expectedResult::equals)
                .verifyComplete();
    }

    @Test
    void getGithubInfo_shouldNotReturnForkRepoInfo() throws IOException {
        //given
        var username = "test";
        UserInfo userInfoMock = new UserInfo();
        userInfoMock.setPublicRepos(5);
        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(userInfoMock))
                .addHeader("Content-Type", "application/json"));
        GitRepositoryOwner owner = new GitRepositoryOwner();
        owner.setLogin(username);
        GitRepository gitRepository = new GitRepository();
        gitRepository.setName("repo-test");
        gitRepository.setFork(true);
        gitRepository.setOwner(owner);

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(List.of(gitRepository)))
                .addHeader("Content-Type", "application/json"));

        //when
        Flux<GithubRepoInfoDTO> response = githubInfoService.getGithubRepoInfo(username);


        //then
        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();
    }
}
