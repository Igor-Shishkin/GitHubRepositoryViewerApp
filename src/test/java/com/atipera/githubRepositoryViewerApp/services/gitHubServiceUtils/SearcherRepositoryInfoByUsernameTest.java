package com.atipera.githubRepositoryViewerApp.services.gitHubServiceUtils;

import com.atipera.githubRepositoryViewerApp.models.RepositoryInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

class SearcherRepositoryInfoByUsernameTest {
    @Mock
    private JSONConnector jsonConnector;

    private SearcherRepositoryInfoByUsername searcherRepositoryInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searcherRepositoryInfo = new SearcherRepositoryInfoByUsername();
        setPrivateField(searcherRepositoryInfo, "jsonConnector", jsonConnector);
    }
    private static JSONArray exampleRepositoriInfoJSONArray;
    private static JSONArray exampleBranchBankSiteJSONArray;

    @Test
    void test_get_repositories_info_success() throws IOException {

        exampleBranchBankSiteJSONArray.remove(2);

        when(jsonConnector.createJSONArrayByUrl(argThat(url -> {
            if (url!=null) {
                return url.toString().equals("https://api.github.com/users/testUsername/repos");
            }
            return false;
        })))
                .thenReturn(exampleRepositoriInfoJSONArray);
        when(jsonConnector.createJSONArrayByUrl
                (argThat(url -> {
                    if (url!=null) {
                        return url.toString().equals("https://api.github.com/repos/testUsername/BankSiteApp/branches");
                    }
                    return false;
                })))
                .thenReturn(exampleBranchBankSiteJSONArray);

        List<RepositoryInfo> repositoriesInfoSet = searcherRepositoryInfo.getRepositoriesInfo("testUsername");

        assertThat(repositoriesInfoSet.size()).isEqualTo(2);
        assertThat(repositoriesInfoSet.get(0).getRepositoryName()).isEqualTo("BankSiteApp");
        assertThat(repositoriesInfoSet.get(0).getOwnerLogin()).isEqualTo("testUsername");
        assertThat(repositoriesInfoSet.get(0).getBranches().size()).isEqualTo(2);
        assertThat(repositoriesInfoSet.get(0).getBranches().get(0).getNameOfBranch())
                .isEqualTo("Add tests");
        assertThat(repositoriesInfoSet.get(0).getBranches().get(1).getNameOfBranch())
                .isEqualTo("Refactoring transferService");
        assertThat(repositoriesInfoSet.get(0).getBranches().get(1).getLastCommitSHA())
                .isEqualTo("lastCommitForRefactoring");
    }

    @Test
    void test_get_repositories_info_without_commit_json_exception() throws IOException {

        JSONObject branchForBankSiteThree = new JSONObject();
        branchForBankSiteThree.put("name", "Only commit name");
        exampleBranchBankSiteJSONArray.put(branchForBankSiteThree);
        when(jsonConnector.createJSONArrayByUrl(argThat(url -> {
            if (url!=null) {
                return url.toString().equals("https://api.github.com/users/testUsername/repos");
            }
            return false;
        })))
                .thenReturn(exampleRepositoriInfoJSONArray);
        when(jsonConnector.createJSONArrayByUrl
                (argThat(url -> {
                    if (url!=null) {
                        return url.toString().equals("https://api.github.com/repos/testUsername/BankSiteApp/branches");
                    }
                    return false;
                })))
                .thenReturn(exampleBranchBankSiteJSONArray);

        List<RepositoryInfo> repositoriesInfoSet = searcherRepositoryInfo.getRepositoriesInfo("testUsername");

        assertThat(repositoriesInfoSet.size()).isEqualTo(2);
        assertThat(repositoriesInfoSet.get(0).getBranches().get(2).getLastCommitSHA()).isNull();
    }



    static {
            exampleRepositoriInfoJSONArray = new JSONArray();

            JSONObject repoOne = new JSONObject();
            repoOne.put("name", "BankSiteApp");
            JSONObject ownerForRepoOne = new JSONObject();
            ownerForRepoOne.put("login", "testUsername");
            repoOne.put("owner", ownerForRepoOne);
            repoOne.put("fork", false);

            JSONObject repoTwo = new JSONObject();
            repoTwo.put("name", "GameOfLifeApp");
            JSONObject ownerForRepoTwo = new JSONObject();
            ownerForRepoTwo.put("login", "testUsername");
            repoTwo.put("owner", ownerForRepoTwo);
            repoTwo.put("fork", false);

            JSONObject repoThree = new JSONObject();
            repoThree.put("name", "RepoWithFork");
            JSONObject ownerForRepoThree = new JSONObject();
            ownerForRepoThree.put("login", "testUsername");
            repoThree.put("owner", ownerForRepoThree);
            repoThree.put("fork", true);

            exampleRepositoriInfoJSONArray.put(repoOne);
            exampleRepositoriInfoJSONArray.put(repoTwo);
            exampleRepositoriInfoJSONArray.put(repoThree);


            exampleBranchBankSiteJSONArray = new JSONArray();

            JSONObject branchForBankSiteOne = new JSONObject();
            branchForBankSiteOne.put("name", "Add tests");
            JSONObject commitsForBankBranchOne = new JSONObject();
            commitsForBankBranchOne.put("sha", "lastCommitForTests");
            branchForBankSiteOne.put("commit", commitsForBankBranchOne);

            JSONObject branchForBankSiteTwo = new JSONObject();
            branchForBankSiteTwo.put("name", "Refactoring transferService");
            JSONObject commitsForBankBranchTwo = new JSONObject();
            commitsForBankBranchTwo.put("sha", "lastCommitForRefactoring");
            branchForBankSiteTwo.put("commit", commitsForBankBranchTwo);

            exampleBranchBankSiteJSONArray.put(branchForBankSiteOne);
            exampleBranchBankSiteJSONArray.put(branchForBankSiteTwo);

    }
    private void setPrivateField(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Error: " + e);
        }
    }
}

