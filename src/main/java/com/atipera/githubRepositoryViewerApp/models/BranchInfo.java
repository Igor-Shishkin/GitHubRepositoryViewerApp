package com.atipera.githubRepositoryViewerApp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchInfo {
    String nameOfBranch;
    String lastCommitSHA;
}