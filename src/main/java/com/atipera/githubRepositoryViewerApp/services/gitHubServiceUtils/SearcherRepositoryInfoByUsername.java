package com.atipera.githubRepositoryViewerApp.services.gitHubServiceUtils;

import com.atipera.githubRepositoryViewerApp.models.Branch;
import com.atipera.githubRepositoryViewerApp.models.RepositoryInfo;
import com.atipera.githubRepositoryViewerApp.playload.response.BranchDTO;
import com.atipera.githubRepositoryViewerApp.playload.response.RepositoryResponseDTO;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import com.google.common.reflect.TypeToken;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.net.URISyntaxException;



@Slf4j
@Component
public class SearcherRepositoryInfoByUsername {
    private final JSONConnector jsonConnector;

    public SearcherRepositoryInfoByUsername(JSONConnector jsonConnector) {
        this.jsonConnector = jsonConnector;
    }

    public Set<RepositoryResponseDTO> getRepositoriesDTO(String username)
            throws IOException, URISyntaxException {

        String json = jsonConnector.getStringJsonForRepositoriesInfo(username).orElseThrow(IOException::new);
        Type type = new TypeToken<Set<RepositoryInfo>>(){}.getType();
        Set<RepositoryInfo> repositories = new Gson().fromJson(json, type);

        return repositories.stream()
                .filter(repository -> !repository.fork())
                .map(repositoryInfo -> {
                    try {
                        return new RepositoryResponseDTO(
                                repositoryInfo.name(),
                                repositoryInfo.owner().login(),
                                getBranches(repositoryInfo.name(), username));
                    } catch (IOException | URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());
    }

    private Set<BranchDTO> getBranches(String repositoryName, String username) throws IOException, URISyntaxException {
        String json = jsonConnector.getStringJsonForBranchesInfo(username, repositoryName)
                .orElseThrow(IOException::new);
        Type setType = new TypeToken<Set<Branch>>(){}.getType();
        Set<Branch> branches = new Gson().fromJson(json, setType);

        return branches.stream()
                .map(branch -> new BranchDTO(branch.name(), branch.commit().sha()))
                .collect(Collectors.toSet());
    }
}
