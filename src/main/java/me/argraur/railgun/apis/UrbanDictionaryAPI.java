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

import org.json.JSONArray;
import org.json.JSONObject;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UrbanDictionaryAPI {
    private OkHttpClient okHttpClient;

    /**
     * Default constructor
     */
    public UrbanDictionaryAPI() {
        okHttpClient = new OkHttpClient();
        System.out.println("UrbanDictionaryAPI is ready!");
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
     * @param result Response from UD in JSON format.
     * @return Word string
     */
    public String getWord(JSONObject result) {
        return result.getString("word");
    } 

    /**
     * 
     * @param result Response from UD in JSON format.
     * @return Definition string
     */
    public String getDefinition(JSONObject result) {
        return result.getString("definition");
    }

    /**
     * 
     * @param result Response from UD in JSON format.
     * @return Author name
     */
    public String getAuthor(JSONObject result) {
        return result.getString("author");
    }

    /**
     * 
     * @param result Response from UD in JSON format.
     * @return Example usage of word
     */
    public String getExample(JSONObject result) {
        return result.getString("example");
    }

    /**
     * 
     * @param query Search query
     * @return Response from UD in JSON format based on given query.
     */
    public JSONObject search(String query) {
        StringBuilder urlSb = new StringBuilder("http://api.urbandictionary.com/v0/define?term=");
        urlSb.append(query);
        JSONObject jObj = new JSONObject(getResponseJSON(urlSb.toString()));
        JSONArray list = jObj.getJSONArray("list");
        JSONObject result = list.getJSONObject(0);
        return result;
    }

    /**
     * 
     * @param result
     * @return
     */
    public MessageEmbed toEmbed(JSONObject result) {
        String word = getWord(result);
        String definition = getDefinition(result);
        String author = getAuthor(result);
        String example = getExample(result);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.PINK);
        embedBuilder.setTitle(word);
        embedBuilder.setAuthor(author);
        embedBuilder.setDescription(definition + "\n\n" + example);
        return embedBuilder.build();
    }
}