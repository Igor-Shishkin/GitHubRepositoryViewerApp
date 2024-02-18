package com.atipera.githubRepositoryViewerApp.services.gitHubServiceUtils;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class JSONConnector {
    private final String GITHUB_API_URL = "https://api.github.com";
    public Optional<String> getStringJsonForRepositoriesInfo(String username) throws IOException, URISyntaxException {
        String url = GITHUB_API_URL.concat("/users/").concat(username).concat("/repos");

        return getJsonByUrl(url);
    }
    public Optional<String> getStringJsonForBranchesInfo(String username, String repositoryName)
            throws IOException, URISyntaxException {
        String url = GITHUB_API_URL.concat("/repos/").concat(username)
                .concat("/").concat(repositoryName).concat("/branches");

        return getJsonByUrl(url);
    }

    private Optional<String> getJsonByUrl(String url) throws URISyntaxException, IOException {
        URI uri = new URI(url);
        URL urlForBranchesInfo = uri.toURL();

        HttpURLConnection connection = (HttpURLConnection) urlForBranchesInfo.openConnection();
        return Optional.of(IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8));
    }
}
