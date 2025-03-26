package org.groupJ.util;

import okhttp3.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;

/**
 * Utility class which creates a zoom meeting using OKHTTP
 */
public class ZoomCreator {

    /**
     * Creates a zoom meeting link
     * @param accessToken the zoom oauth access token.
     * @param topic the title of the meeting
     * @param duration the estimated duration of the meeting
     * @param timezone the specified timezone in UTC
     * @return zoom meeting link
     * @throws IOException throws an ioexception if the server is unreachable
     */
    public static String createZoomMeeting(String accessToken, String topic, int duration, String timezone) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Create JSON request body
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("topic", topic);
        jsonBody.addProperty("type", 1); // 1 for instant meeting, 2 for scheduled
        jsonBody.addProperty("duration", duration); // Duration in minutes
        jsonBody.addProperty("timezone", timezone);

        RequestBody body = RequestBody.create(
                jsonBody.toString(),
                MediaType.parse("application/json")
        );

        // Create HTTP request
        Request request = new Request.Builder()
                .url("https://api.zoom.us/v2/users/me/meetings")
                .post(body)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .build();

        // Execute request
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        // Parse the JSON response and extract the join_url
        JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
        return jsonResponse.get("join_url").getAsString();
    }
}