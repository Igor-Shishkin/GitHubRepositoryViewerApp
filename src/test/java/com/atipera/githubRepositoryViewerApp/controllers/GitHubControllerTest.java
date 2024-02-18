package com.atipera.githubRepositoryViewerApp.controllers;

import com.atipera.githubRepositoryViewerApp.playload.response.MessageResponse;
import com.atipera.githubRepositoryViewerApp.playload.response.RepositoryResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GitHubControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetRepositoriesInfoEndpoint() {
        String username = "user";

        webTestClient.get()
                .uri("api/github/get-repositories-info?username=" + username)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RepositoryResponseDTO.class)
                .consumeWith(response -> {
                    List<RepositoryResponseDTO> repositories = response.getResponseBody();
                    assertNotNull(repositories);
                    assertFalse(repositories.isEmpty());

                    for (RepositoryResponseDTO repository : repositories) {
                        assertNotNull(repository.repositoryName());
                        assertNotNull(repository.ownerLogin());
                        assertNotNull(repository.branches());
                    }

                    //The verification is relevant at the moment, the repository data may change over time!
                    assertThat(
                            repositories.stream()
                            .anyMatch(repository -> repository.repositoryName().equals("crux-ports")))
                            .isTrue();
                    //is forked repositories filtered
                    assertThat(
                            repositories.stream()
                                    .anyMatch(repository -> repository.repositoryName().equals("bitmap-fonts")))
                            .isFalse();
                });
    }
    @Test
    public void testGetRepositoriesInfoEndpointURIException() {
        String username = "us    er";

        webTestClient.get()
                .uri("api/github/get-repositories-info?username=" + username)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(MessageResponse.class)
                .consumeWith(response -> {
                    assertThat(response.getResponseBody()).isNotNull();
                    assertThat(response.getResponseBody().message()).isEqualTo("Error during construction URL");
                });
    }
    @Test
    public void testGetRepositoriesInfoEndpointIOException() {
        String username = "user_____999999888888888!";

        webTestClient.get()
                .uri("api/github/get-repositories-info?username=" + username)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(MessageResponse.class)
                .consumeWith(response -> {
                    assertThat(response.getResponseBody()).isNotNull();
                    assertThat(response.getResponseBody().message()).isEqualTo("User not found");
                });
    }
}