package com.atipera.githubRepositoryViewerApp.controllers;

import com.atipera.githubRepositoryViewerApp.exceptions.UrlIsUnavailableException;
import com.atipera.githubRepositoryViewerApp.models.RepositoryInfo;
import com.atipera.githubRepositoryViewerApp.playload.response.MessageResponse;
import com.atipera.githubRepositoryViewerApp.services.GitHubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    @Operation(summary = "Get user's repositories information by username", tags = { "repositories", "info" })
    public ResponseEntity<?> getRepositoriesInfoByUsername(@RequestParam String username) {
        try {
            List<RepositoryInfo> repositories = gitHubService.getRepositoriesInfo(username);
            return ResponseEntity.ok(repositories);
        } catch (UrlIsUnavailableException e) {
            log.error("Error loading data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Error loading data: " + e.getMessage()));
        }catch (IOException e) {
            log.error("Error loading data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error loading data: " + e.getMessage()));
        }
    }
}
