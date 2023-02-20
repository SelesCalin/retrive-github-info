package com.example.githubretrieve.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode()
public class GithubRepoInfoDTO {

    private String name;
    private String ownerLogin;
    private List<BranchInfoDTO> branches;

}
