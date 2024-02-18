package com.atipera.githubRepositoryViewerApp.controllers;

import com.atipera.githubRepositoryViewerApp.playload.response.RepositoryResponseDTO;
import com.atipera.githubRepositoryViewerApp.services.GitHubService;
import com.google.common.collect.ImmutableSet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

@RestController
@RequestMapping("api/github/")
@Tag(name = "GitHub controller", description = "CitHub management APIs")
@Slf4j

public class GitHubController {

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("get-repositories-info")
    @Operation(summary = "Get user's repositories information by username")
    public ResponseEntity<Set<RepositoryResponseDTO>> getRepositoriesInfoByUsername
            (@RequestParam String username) throws IOException, URISyntaxException {

        ImmutableSet<RepositoryResponseDTO> repositoriesInfo = gitHubService.getRepositoriesInfo(username.trim());

        return ResponseEntity.ok(repositoriesInfo);
    }
}