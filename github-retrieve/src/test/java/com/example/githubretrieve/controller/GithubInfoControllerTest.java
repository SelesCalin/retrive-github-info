package com.example.githubretrieve.controller;

import com.example.githubretrieve.dto.ErrorResponse;
import com.example.githubretrieve.controller.advice.ErrorCatalog;
import com.example.githubretrieve.dto.BranchInfoDTO;
import com.example.githubretrieve.dto.GithubRepoInfoDTO;
import com.example.githubretrieve.exception.UserNotFoundException;
import com.example.githubretrieve.service.GithubInfoService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;


@WebFluxTest(GithubInfoController.class)
class GithubInfoControllerTest {

    public static final String PATH = "/github/users/{username}";
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GithubInfoService service;

    @Test
    void getGithubInfo_shouldReturn406WhenFormatIsNotAcceptable() {
        webTestClient.get()
                .uri(PATH, "user")
                .accept(MediaType.APPLICATION_XML)
                .exchange()
                .expectStatus()
                .isEqualTo(406);
    }

    @Test
    void getGithubInfo_shouldReturn404WhenUserIsNotFound() {
        when(service.getGithubRepoInfo("user")).thenThrow(UserNotFoundException.class);

        webTestClient.get()
                .uri(PATH, "user")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isEqualTo(404)
                .expectBody(ErrorResponse.class)
                .isEqualTo(ErrorCatalog.WRONG_USERNAME.getError());
    }

    @Test
    void getGithubInfo_returnUser() {
        var username = "user";
        var response = createMockedResponse(username);

        when(service.getGithubRepoInfo(username)).thenReturn(response);

        webTestClient.get()
                .uri(PATH, username)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(GithubRepoInfoDTO.class)
                .hasSize(1);
    }

    @NotNull
    private Flux<GithubRepoInfoDTO> createMockedResponse(String username) {
        GithubRepoInfoDTO infoDTO = GithubRepoInfoDTO.builder()
                .name("Github")
                .ownerLogin(username)
                .branches(List.of(BranchInfoDTO.builder().name("branch")
                        .build()))
                .build();

        return Flux.fromStream(Stream.of(infoDTO));
    }
}
