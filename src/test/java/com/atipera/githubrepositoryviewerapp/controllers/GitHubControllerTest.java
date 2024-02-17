package com.atipera.githubrepositoryviewerapp.controllers;

import com.atipera.githubrepositoryviewerapp.exceptions.UrlIsUnavailableException;
import com.atipera.githubrepositoryviewerapp.models.RepositoryInfo;
import com.atipera.githubrepositoryviewerapp.playload.response.MessageResponse;
import com.atipera.githubrepositoryviewerapp.services.GitHubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GitHubControllerTest {
    private GitHubController gitHubController;
    private GitHubService gitHubService;

    @BeforeEach
    void setUp() {
        gitHubService = mock(GitHubService.class);
        gitHubController = new GitHubController(gitHubService);
    }

    @Test
    void testAuthenticateUser_Success() throws IOException, IOException {
        String username = "testUser";
        List<RepositoryInfo> repositories = createSampleRepositories();

        when(gitHubService.getRepositoriesInfo(username)).thenReturn(repositories);

        ResponseEntity<?> responseEntity = gitHubController.getRepositoriesInfoByUsername(username);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(repositories);
    }

    @Test
    void testAuthenticateUser_UrlIsUnavailableException() throws IOException {
        // Arrange
        String username = "testUser";
        when(gitHubService.getRepositoriesInfo(username)).thenThrow(new UrlIsUnavailableException("URL is unavailable"));

        ResponseEntity<?> responseEntity = gitHubController.getRepositoriesInfoByUsername(username);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isInstanceOf(MessageResponse.class);
    }

    @Test
    void testAuthenticateUser_IOException() throws IOException {
        String username = "testUser";
        when(gitHubService.getRepositoriesInfo(username)).thenThrow(new IOException("IO Exception"));

        ResponseEntity<?> responseEntity = gitHubController.getRepositoriesInfoByUsername(username);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseEntity.getBody()).isInstanceOf(MessageResponse.class);
    }

    private List<RepositoryInfo> createSampleRepositories() {
        return List.of(
                new RepositoryInfo(),
                new RepositoryInfo()
        );
    }

}