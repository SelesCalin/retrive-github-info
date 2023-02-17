package com.example.githubretrieve.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GithubRepoInfoDTO {

    private String name;
    private String ownerLogin;
    private List<BranchInfoDTO> branches;

}
