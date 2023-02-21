package com.example.githubretrieve.service;

import com.example.githubretrieve.dto.BranchInfoDTO;
import com.example.githubretrieve.dto.GithubRepoInfoDTO;
import com.example.githubretrieve.model.Branch;
import com.example.githubretrieve.model.GitRepository;
import com.example.githubretrieve.model.UserInfo;
import com.example.githubretrieve.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static com.example.githubretrieve.util.Utils.calculateNumberOfPages;


@Service
public class GithubInfoService {

    public static final Integer PAGE_SIZE = 30;
    private static final int FIRST_PAGE = 1;
    public static final String USER_URL = "/users/{username}";
    public static final String REPOSITORY_URL = "/users/{username}/repos?per_page={page_size}&page={page}";
    public static final String BRANCH_URL = "/repos/{username}/{repoName}/branches?per_page={per_page}&page={page}";
    private final WebClient webClient;

    public GithubInfoService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<GithubRepoInfoDTO> getGithubRepoInfo(String username) {
        var userInfo = getUserInfo(username);
        if (userInfo == null) {
            return Flux.empty();
        }
        var numberOfPages = calculateNumberOfPages(userInfo.getPublicRepos(), PAGE_SIZE);
        return Flux.fromStream(IntStream.rangeClosed(FIRST_PAGE, numberOfPages).boxed())
                .flatMap(page -> getGithubRepos(username, page))
                .filter(g -> !g.isFork())
                .map(GitRepository::toGitRepoInfoDTO)
                .flatMap(repo -> getBranches(username, repo.getName())
                        .map(branchInfoDTO -> {
                            repo.getBranches().add(branchInfoDTO);
                            return repo;
                        }));
    }

    private UserInfo getUserInfo(String username) {
        return this.webClient
                .get()
                .uri(USER_URL, username)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        response -> Mono.error(UserNotFoundException::new))
                .bodyToMono(UserInfo.class).block();
    }

    private Flux<GitRepository> getGithubRepos(String username, Integer page) {

        return webClient.get()
                .uri(REPOSITORY_URL, username, PAGE_SIZE, page)
                .retrieve()
                .bodyToFlux(GitRepository.class);
    }

    private Flux<BranchInfoDTO> getBranches(String username, String repoName) {
        return getBranchesOfRepo(username, repoName, FIRST_PAGE)
                .map(Branch::toBranchInfoDTO);
    }

    private Flux<Branch> getBranchesOfRepo(String username, String repoName, final int page) {
        return webClient.get()
                .uri(BRANCH_URL, username, repoName, PAGE_SIZE, page)
                .retrieve()
                .bodyToMono(Branch[].class)
                .flatMapMany(branches -> {
                    if (branches.length < PAGE_SIZE) {
                        return Flux.fromStream(Arrays.stream(branches));
                    } else {
                        var nextPage = new AtomicInteger(page).incrementAndGet();
                        return Flux.fromStream(Arrays.stream(branches))
                                .concatWith(getBranchesOfRepo(username, repoName, nextPage));
                    }
                });
    }
}
