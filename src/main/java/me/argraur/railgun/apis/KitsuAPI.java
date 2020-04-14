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

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.awt.Color;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import me.argraur.railgun.RailgunBot;

public class KitsuAPI {
    private OkHttpClient okHttpClient;
    private String url = "https://kitsu.io/api/edge";
    private Random random;

    /**
     * Default constructor.
     */
    public KitsuAPI() {
        okHttpClient = new OkHttpClient();
        random = new Random();
        System.out.println("KitsuAPI is ready!");
    }

    /**
     *
     * @param toBeBold String that should be bold in embed.
     * @return Bolded string.
     */
    public String bold(String toBeBold) {
        return "**" + toBeBold + "**";
    }

    /**
     *
     * @param toBeItalic String that should be italic in embed.
     * @return Italic string.
     */
    public String italic(String toBeItalic) {
        return "*" + toBeItalic + "*";
    }

    /**
     *
     * @param jsonObject Raw JSON data from API.
     * @return Random "anime" JSON data object.
     */
    public JSONObject getRandomAnimeObject(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        return jsonArray.getJSONObject(random.nextInt(jsonArray.length()));
    }

    /**
     *
     * @param jsonObject Raw JSON data from API.
     * @return Specific "anime" JSON data object.
     */
    public JSONObject getAnimeObject(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        return jsonArray.getJSONObject(0);
    }

    /**
     *
     * @param animeObject Specific "anime" JSON data object.
     * @return en_jp title
     */
    public String getEnglishTitle(JSONObject animeObject) {
        return animeObject.getJSONObject("attributes").getJSONObject("titles").getString("en_jp");
    }

    /**
     *
     * @param animeObject Specific "anime" JSON data object.
     * @return Synopsis, in English.
     */
    public String getSynopsis(JSONObject animeObject) {
        return animeObject.getJSONObject("attributes").getString("synopsis");
    }

    /**
     *
     * @param animeObject Specific "anime" JSON data object.
     * @return Rating
     */
    public String getRating(JSONObject animeObject) {
        return animeObject.getJSONObject("attributes").getString("averageRating");
    }

    /**
     *
     * @param animeObject Specific "anime" JSON data object.
     * @return String of genres. Example: "Genres: Action, Comedy"
     */
    public String getGenres(JSONObject animeObject) {
        String genresResponse = getResponseJSON(animeObject.getJSONObject("relationships").getJSONObject("genres").getJSONObject("links").getString("related"));
        StringBuilder genresBuilder = new StringBuilder("Genres: ");
        JSONObject genresObject = new JSONObject(genresResponse);
        JSONArray genresArray = genresObject.getJSONArray("data");
        for (int i = 0; i < genresArray.length(); i++) {
            genresBuilder.append(genresArray.getJSONObject(i).getJSONObject("attributes").getString("name"));
            if (i != genresArray.length() - 1) genresBuilder.append(", ");
        }
        return genresBuilder.toString();
    }

    /**
     *
     * @param animeObject Specific "anime" JSON data object.
     * @return Status of anime. Finished or Airing.
     */
    public String getStatus(JSONObject animeObject) {
        return animeObject.getJSONObject("attributes").getString("status");
    }

    /**
     *
     * @param animeObject Specific "anime" JSON data object.
     * @return Type of source where anime is being aired. (e.g. TV, Movie, OVA, etc.)
     */
    public String getSource(JSONObject animeObject) {
        return animeObject.getJSONObject("attributes").getString("subtype");
    }

    /**
     *
     * @param animeObject Specific "anime" JSON data object.
     * @return Anime page on Kitsu.
     */
    public String getKitsuUrl(JSONObject animeObject) {
        return "https://kitsu.io/anime/" + animeObject.getJSONObject("attributes").getString("slug");
    }

    /**
     *
     * @param animeObject Specific "anime" JSON data object.
     * @return Poster image URL for embed.
     */
    public String getImage(JSONObject animeObject) {
        return animeObject.getJSONObject("attributes").getJSONObject("posterImage").getString("small");
    }

    /**
     *
     * @param animeObject Specific "anime" JSON data object.
     * @return YouTube URL for PVs and Trailers
     */
    public String getYTUrl(JSONObject animeObject) {
        return "https://youtu.be/" + animeObject.getJSONObject("attributes").getString("youtubeVideoId");
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
     * @param query Genre
     * @return JSONObject with result for given genre.
     */
    public JSONObject searchRandomByGenre(String query) {
        return getRandomAnimeObject(new JSONObject(getResponseJSON(url + String.format(("/anime?filter[genres]=%s&sort=-favoritesCount"), query))));
    }

    /**
     *
     * @param query Search query.
     * @return JSONObject with result for given query.
     */
    public JSONObject searchByQuery(String query) {
        return getAnimeObject(new JSONObject(getResponseJSON(url + String.format(("/anime?filter[text]=%s"), query.replaceAll(" ", "%20")))));
    }

    /**
     *
     * @param animeObject Specific "anime" JSON data object.
     * @return Creates and returns Discord Embed out of given animeObject JSON data.
     */
    public MessageEmbed toEmbed(JSONObject animeObject) {
        String status = getStatus(animeObject);
        String genres = getGenres(animeObject);
        String ytUrl = getYTUrl(animeObject);

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.decode("#" + RailgunBot.colorHelper.getColor(getImage(animeObject))));
        embedBuilder.setTitle(getEnglishTitle(animeObject), getKitsuUrl(animeObject));
        String description = "\n\n" + bold(getSource(animeObject) + " // Status: " + status.replace(Character.toString(status.charAt(0)), Character.toString(status.charAt(0)).toUpperCase()) + " // Rating: " + getRating(animeObject)) + "\n";
        if (!genres.equals("Genres: "))
            description += "\n" + italic(getGenres(animeObject)) + "\n";
        description += "```" + getSynopsis(animeObject) + "\n" + "```" + "\n";
        if (!ytUrl.equals("https://youtu.be/"))
            description += "**YouTube PV/Trailer: **" + ytUrl;
        embedBuilder.setDescription(description);
        embedBuilder.setThumbnail(getImage(animeObject));

        return embedBuilder.build();
    }
}