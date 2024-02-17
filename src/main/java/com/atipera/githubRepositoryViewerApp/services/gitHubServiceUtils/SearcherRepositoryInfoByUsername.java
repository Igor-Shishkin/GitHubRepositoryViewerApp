package com.atipera.githubRepositoryViewerApp.services.gitHubServiceUtils;

import com.atipera.githubRepositoryViewerApp.models.BranchInfo;
import com.atipera.githubRepositoryViewerApp.models.RepositoryInfo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class SearcherRepositoryInfoByUsername {
    private final JSONConnector jsonConnector = new JSONConnector();
    private String username;
    private final String GITHUB_API_URL = "https://api.github.com";

    public List<RepositoryInfo> getRepositoriesInfo(String username) throws IOException {
        this.username = username;
        String url = GITHUB_API_URL.concat("/users/").concat(username).concat("/repos");
        URL urlForRepositoriesInfo = new URL(url);

        JSONArray jsonArray = jsonConnector.createJSONArrayByUrl(urlForRepositoriesInfo);

        List<RepositoryInfo> repositoriesInfo = parseJSONArrayToSetRepositoriesInfo(jsonArray);

        return repositoriesInfo;
    }

    private List<RepositoryInfo> parseJSONArrayToSetRepositoriesInfo(JSONArray jsonArray) throws IOException {
        if (jsonArray == null) { return null; }
        List<RepositoryInfo> repositoryInfoSet = new LinkedList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String repositoryName = null;
            if (jsonObject.has("name")) {
                repositoryName = jsonObject.getString("name");
            }

            if (jsonObject.getBoolean("fork") || repositoryName==null
                    || repositoryName.equals(username)) { continue; }

            RepositoryInfo repositoryInfo = new RepositoryInfo();
            try {
                repositoryInfo.setRepositoryName(repositoryName);
                repositoryInfo.setOwnerLogin(jsonObject.getJSONObject("owner").getString("login"));
            } catch (JSONException e) {
                log.error("Parsing repository info exception: " + e);
            }

            repositoryInfo.setBranches(getBranchesForRepositoryByRepositoryName(repositoryName));
            repositoryInfoSet.add(repositoryInfo);
        }
        return repositoryInfoSet;
    }

    private List<BranchInfo> getBranchesForRepositoryByRepositoryName(String repositoryName) throws IOException {
        String url = GITHUB_API_URL.concat("/repos/").concat(username)
                .concat("/").concat(repositoryName).concat("/branches");
        URL urlForBranchesInfo = new URL(url);
        JSONArray jsonArray = jsonConnector.createJSONArrayByUrl(urlForBranchesInfo);

        List<BranchInfo> branches = parseJSONArrayToSetBranchesInfo(jsonArray);

        return branches;
    }

    private List<BranchInfo> parseJSONArrayToSetBranchesInfo(JSONArray jsonArray) {
        if (jsonArray == null) { return null; }
        List<BranchInfo> branches = new LinkedList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            BranchInfo branchInfo = new BranchInfo();

            try {
                branchInfo.setNameOfBranch(jsonObject.getString("name"));
                branchInfo.setLastCommitSHA(jsonObject.getJSONObject("commit").getString("sha"));
            } catch (JSONException e) {
                log.error("Parsing branch info exception: " + e);
            }

            branches.add(branchInfo);
        }
        return branches;
    }

}
