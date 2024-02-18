package com.atipera.githubRepositoryViewerApp.services;

import com.atipera.githubRepositoryViewerApp.playload.response.RepositoryResponseDTO;
import com.atipera.githubRepositoryViewerApp.services.gitHubServiceUtils.SearcherRepositoryInfoByUsername;
import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class GitHubService {

    private final SearcherRepositoryInfoByUsername searcherRepositoryInfo;

    public GitHubService(SearcherRepositoryInfoByUsername searcherRepositoryInfo) {
        this.searcherRepositoryInfo = searcherRepositoryInfo;
    }

    public ImmutableSet<RepositoryResponseDTO> getRepositoriesInfo(String username) throws IOException, URISyntaxException {
        return searcherRepositoryInfo.getRepositoriesDTO(username);
    }
}
