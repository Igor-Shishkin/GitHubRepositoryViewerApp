package com.atipera.githubRepositoryViewerApp.services.gitHubServiceUtils;

import com.atipera.githubRepositoryViewerApp.exceptions.UrlIsUnavailableException;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class JSONConnectorTest {

    @Test
    void test_create_json_array_by_url_success() throws IOException, JSONException {

        String json = "[{\"name\":\"repo1\"}, {\"name\":\"repo2\"}]";
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());

        HttpURLConnection mockConnection = Mockito.mock(HttpURLConnection.class);
        when(mockConnection.getInputStream()).thenReturn(inputStream);
        when(mockConnection.getResponseCode()).thenReturn(200);

        URL mockUrl = Mockito.mock(URL.class);
        when(mockUrl.openConnection()).thenReturn(mockConnection);

        JSONConnector jsonConnector = new JSONConnector();
        JSONArray jsonArray = jsonConnector.createJSONArrayByUrl(mockUrl);


        assertThat(jsonArray.length()).isEqualTo(2);
        assertThat(jsonArray.getJSONObject(0).getString("name")).isEqualTo("repo1");
        assertThat(jsonArray.getJSONObject(1).getString("name")).isEqualTo("repo2");
    }

    @Test
    void test_create_json_array_by_url_unavailable_exception() throws IOException {
        HttpURLConnection mockConnection = Mockito.mock(HttpURLConnection.class);
        when(mockConnection.getResponseCode()).thenReturn(404);

        URL mockUrl = Mockito.mock(URL.class);
        when(mockUrl.openConnection()).thenReturn(mockConnection);

        JSONConnector jsonConnector = new JSONConnector();

        assertThrows(UrlIsUnavailableException.class, () -> {
            jsonConnector.createJSONArrayByUrl(mockUrl);
        });
    }
}
