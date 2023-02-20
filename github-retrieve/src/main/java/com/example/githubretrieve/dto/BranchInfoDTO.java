package com.example.githubretrieve.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class BranchInfoDTO {

    private String name;
    private String lastCommitSha;

}
