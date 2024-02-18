package com.atipera.githubRepositoryViewerApp.playload.response;

import java.util.Set;

public record RepositoryResponseDTO(String repositoryName, String ownerLogin, Set<BranchDTO> branches) {
}
