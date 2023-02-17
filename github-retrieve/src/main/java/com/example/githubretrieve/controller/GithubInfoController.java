package com.example.githubretrieve.controller;

import com.example.githubretrieve.service.GithubInfoService;
import com.example.githubretrieve.dto.GithubRepoInfoDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class GithubInfoController {

    private final GithubInfoService githubInfoService;
    public GithubInfoController(GithubInfoService githubInfoService) {
        this.githubInfoService = githubInfoService;
    }
    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<GithubRepoInfoDTO> getInfoForUser(@RequestParam("username") String username) {
        return githubInfoService.getGithubRepoInfo(username);
    }
}
