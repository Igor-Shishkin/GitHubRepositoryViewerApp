package com.atipera.githubRepositoryViewerApp.services.gitHubServiceUtils;

import com.atipera.githubRepositoryViewerApp.exceptions.UrlIsUnavailableException;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONConnector {
    JSONArray createJSONArrayByUrl(URL urlForRepositoriesInfo) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) urlForRepositoriesInfo.openConnection();

        if (connection.getResponseCode() == 404) {
            throw new UrlIsUnavailableException("User not found");
        }

        JSONTokener jsonTokener = new JSONTokener(connection.getInputStream());
        return new JSONArray(jsonTokener);
    }
}
