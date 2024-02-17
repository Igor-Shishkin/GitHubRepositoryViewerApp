package com.atipera.githubrepositoryviewerapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryInfo {
    String RepositoryName;
    String OwnerLogin;
    List<BranchInfo> branches;
}
