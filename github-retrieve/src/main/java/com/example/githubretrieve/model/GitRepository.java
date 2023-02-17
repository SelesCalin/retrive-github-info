package com.example.githubretrieve.model;

import com.example.githubretrieve.dto.GithubRepoInfoDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class GitRepository {

        private String name;
        private GitRepositoryOwner owner;
        private boolean fork;

        public GithubRepoInfoDTO toGitRepoInfoDTO(){
                return GithubRepoInfoDTO.builder()
                        .name(name)
                        .ownerLogin(owner.getLogin())
                        .branches(new ArrayList<>())
                        .build();
        }

}
