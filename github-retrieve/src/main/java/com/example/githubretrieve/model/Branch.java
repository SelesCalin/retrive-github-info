package com.example.githubretrieve.model;

import com.example.githubretrieve.dto.BranchInfoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Branch {

    private String name;
    private Commit commit;

    public BranchInfoDTO toBranchInfoDTO() {
        return BranchInfoDTO.builder()
                .name(name)
                .lastCommitSha(commit.getSha())
                .build();
    }
}
