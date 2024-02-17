package com.atipera.githubRepositoryViewerApp.services;

import com.atipera.githubRepositoryViewerApp.models.RepositoryInfo;
import com.atipera.githubRepositoryViewerApp.services.gitHubServiceUtils.SearcherRepositoryInfoByUsername;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GitHubService {

    private final SearcherRepositoryInfoByUsername searcherRepositoryInfo = new SearcherRepositoryInfoByUsername();;

    public List<RepositoryInfo> getRepositoriesInfo(String username) throws IOException {
        List<RepositoryInfo> repositories = searcherRepositoryInfo.getRepositoriesInfo(username);

        return repositories;
    }
}
