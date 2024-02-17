package com.atipera.githubrepositoryviewerapp.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.atipera.githubrepositoryviewerapp.models.BranchInfo;
import com.atipera.githubrepositoryviewerapp.models.RepositoryInfo;
import com.atipera.githubrepositoryviewerapp.services.gitHubServiceUtils.SearcherRepositoryInfoByUsername;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class GitHubServiceTest {

    private GitHubService gitHubService;
    private SearcherRepositoryInfoByUsername searcherMock;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        searcherMock = mock(SearcherRepositoryInfoByUsername.class);
        gitHubService = new GitHubService();
        injectDependency(gitHubService, "searcherRepositoryInfo", searcherMock);
    }

    @Test
    void test_repositories_info_success() throws IOException {
        String username = "testUser";
        List<RepositoryInfo> expectedRepositories = createSampleRepositories();
        when(searcherMock.getRepositoriesInfo(username)).thenReturn(expectedRepositories);

        List<RepositoryInfo> repositories = gitHubService.getRepositoriesInfo(username);

        assertThat(repositories.size()).isEqualTo(2);
        assertThat(repositories.get(0).getRepositoryName()).isEqualTo("Repo1");
        assertThat(repositories.get(1).getOwnerLogin()).isEqualTo("Owner2");
        assertThat(repositories.get(1).getBranches().size()).isEqualTo(2);
        assertThat(repositories.get(0).getBranches().get(0).getNameOfBranch()).isEqualTo("Branch1");
    }

    @Test
    void test_get_repositories_info_io_exception() throws IOException {
        // Arrange
        String username = "testUser";
        IOException ioException = new IOException("Connection timeout");
        when(searcherMock.getRepositoriesInfo(username)).thenThrow(ioException);

        // Act & Assert
        assertThrows(IOException.class, () -> {
            gitHubService.getRepositoriesInfo(username);
        });
    }

    private List<RepositoryInfo> createSampleRepositories() {
        return List.of(
                new RepositoryInfo("Repo1", "Owner1",
                        List.of(new BranchInfo("Branch1", "SHA1"))),
                new RepositoryInfo("Repo2", "Owner2",
                        List.of(new BranchInfo("Branch1", "SHA2"),
                                new BranchInfo("Branch2", "SHA3")))
        );
    }

    private void injectDependency(Object target, String fieldName, Object dependency)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, dependency);
    }
}
