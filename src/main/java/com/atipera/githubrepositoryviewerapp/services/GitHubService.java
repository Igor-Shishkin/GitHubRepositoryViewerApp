package com.atipera.githubrepositoryviewerapp.services;

import com.atipera.githubrepositoryviewerapp.models.RepositoryInfo;
import com.atipera.githubrepositoryviewerapp.services.gitHubServiceUtils.SearcherRepositoryInfoByUsername;
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
