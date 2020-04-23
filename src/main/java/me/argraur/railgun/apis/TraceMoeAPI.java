/*
 * Copyright (C) 2020 Arseniy Graur
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.argraur.railgun.apis;

import java.awt.Color;
import java.io.IOException;
import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import me.argraur.railgun.helpers.HelperManager;
import me.argraur.railgun.helpers.ImageHelper;

import static me.argraur.railgun.RailgunBot.kitsuApi;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TraceMoeAPI {
    private OkHttpClient okHttpClient;
    private String baseUrl = "https://trace.moe/api/search?url=";

    /**
     * Default constructor
     */
    public TraceMoeAPI() {
        okHttpClient = new OkHttpClient();
    }

    /**
     *
     * @param url Request target url.
     * @return Raw JSON data.
     */
    public String getResponseJSON(String url) {
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/vnd.api+json")
                .addHeader("Accept", "application/vnd.api+json")
                .url(url)
                .build();
        try {
            try (Response response = okHttpClient.newCall(request).execute()) {
                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException ignored) {}
        return null;
    }

    /**
     * 
     * @param message Discord message
     * @return Response from trace.moe based on given message's query
     */
    public JSONObject search(Message message) {
        JSONObject response;
        String imageUrl = ImageHelper.getImageUrl(message);
        try {
            response = new JSONObject(getResponseJSON(baseUrl + imageUrl));
        } catch (JSONException e) {
            message.getChannel().sendMessage("Request failed.").queue();
            throw new IllegalStateException();
        }
        return response.getJSONArray("docs").getJSONObject(0);
    }

    /**
     * 
     * @param result Response from trace.moe
     * @param message Discord message
     * @return Embed based on response
     */
    public MessageEmbed toEmbed(JSONObject result, Message message) {
        String title = result.getString("title");
        JSONObject animeObject = kitsuApi.searchByQuery(title);
        String status = kitsuApi.getStatus(animeObject);
        String genres = kitsuApi.getGenres(animeObject);
        String ytUrl = kitsuApi.getYTUrl(animeObject);
        int s = (int) result.getFloat("at");
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.decode("#" + HelperManager.color.getColor(kitsuApi.getImage(animeObject))));
        embedBuilder.setTitle(kitsuApi.getEnglishTitle(animeObject) + " | " + title, kitsuApi.getKitsuUrl(animeObject));
        String description = "\n\n" + kitsuApi.bold(kitsuApi.getSource(animeObject) + " // Status: " + status.replace(Character.toString(status.charAt(0)), Character.toString(status.charAt(0)).toUpperCase()) + " // Rating: " + kitsuApi.getRating(animeObject)) + "\n";
        if (!genres.equals("Genres: "))
            description += "\n" + kitsuApi.italic(kitsuApi.getGenres(animeObject)) + "\n";
        description += "\n*Timestamp*  `" + (s - (s %= 60)) / 60 + (s > 9 ? ":" : ":0") + s + "`\n\n";
        description += "*Similarity* `" + String.format("%.2f%%`\n\n", result.getFloat("similarity") * 100) + "";
        description += "```" + kitsuApi.getSynopsis(animeObject) + "\n" + "```" + "\n";
        if (!ytUrl.equals("https://youtu.be/"))
            description += "**YouTube PV/Trailer: **" + ytUrl;
        embedBuilder.setDescription(description);
        embedBuilder.setThumbnail(kitsuApi.getImage(animeObject));
        String imageUrl = ImageHelper.getImageUrl(message);
        embedBuilder.setImage(imageUrl);
        return embedBuilder.build();
    }
}