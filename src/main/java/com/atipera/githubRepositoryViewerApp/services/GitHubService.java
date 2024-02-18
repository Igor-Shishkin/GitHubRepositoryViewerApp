package com.atipera.githubRepositoryViewerApp.services;

import com.atipera.githubRepositoryViewerApp.models.RepositoryInfo;
import com.atipera.githubRepositoryViewerApp.playload.response.RepositoryResponseDTO;
import com.atipera.githubRepositoryViewerApp.services.gitHubServiceUtils.SearcherRepositoryInfoByUsername;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

@Service
public class GitHubService {

    private final SearcherRepositoryInfoByUsername searcherRepositoryInfo;

    public GitHubService(SearcherRepositoryInfoByUsername searcherRepositoryInfo) {
        this.searcherRepositoryInfo = searcherRepositoryInfo;
    }

    public Set<RepositoryResponseDTO> getRepositoriesInfo(String username) throws IOException, URISyntaxException {
        return searcherRepositoryInfo.getRepositoriesDTO(username);
    }
}
