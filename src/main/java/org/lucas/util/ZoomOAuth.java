package org.lucas.util;

import okhttp3.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.Base64;

/**
 * Utility class that provides the access token for the zoom meeting.
 */
public class ZoomOAuth {
    private static final String ACCOUNT_ID = "I3xVw-4USxmYxw15TByvGQ";
    private static final String CLIENT_ID = "yXD3vVutRhKASvzAOzBjIw";
    private static final String CLIENT_SECRET = "DJfAquKIWQ46N9h7i3ODLEUEwZpUKGT4";
    private static final String TOKEN_URL = "https://zoom.us/oauth/token";

    /**
     * Sends a request to zoom for an oauth token using OKHTTP.
     * @return The zoom oauth token
     * @throws IOException throws an ioexception if the server is unreachable
     */
    public static String getAccessToken() throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Encode Client ID and Client Secret in Base64
        String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        // Create request body
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "account_credentials")
                .add("account_id", ACCOUNT_ID)
                .build();

        // Create HTTP request
        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .post(body)
                .addHeader("Authorization", "Basic " + encodedCredentials)
                .build();

        // Execute request
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        // Parse response to get access token
        JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
        return jsonResponse.get("access_token").getAsString();
    }
}
