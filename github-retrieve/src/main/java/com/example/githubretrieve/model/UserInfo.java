package com.example.githubretrieve.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {

    @JsonProperty("public_repos")
    private Integer publicRepos;
}
