package com.example.githubretrieve.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BranchInfoDTO {

    private String name;
    private String lastCommitSha;

}
