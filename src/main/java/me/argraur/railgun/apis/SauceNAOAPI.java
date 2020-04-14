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

import me.argraur.railgun.RailgunBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SauceNAOAPI {
    private String url = "https://saucenao.com/search.php?";
    private final int db = 999;
    private final int outputType = 2;
    private final int testMode = 0;
    private final int numres = 2;
    private OkHttpClient okHttpClient;

    /**
     * Default constructor
     */
    public SauceNAOAPI() {
        okHttpClient = new OkHttpClient();
        System.out.println("SauceNAOAPI is ready!");
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
     * Search image on SauceNAO
     * @param msg Message bbject from MessageListener
     * @return JSON object from SauceNAO with a result
     */
    public JSONObject search(Message msg) {
        String imageUrl = "";
        try {
            imageUrl = msg.getAttachments().get(0).getUrl();
        } catch (IndexOutOfBoundsException e) {
            imageUrl = msg.getContentRaw().split(" ")[1];
        }
        StringBuilder url = new StringBuilder(this.url);
        url.append("db=" + db);
        url.append("&output_type=" + outputType);
        url.append("&testmode=" + testMode);
        url.append("&numres=" + numres);
        url.append("&url=" + imageUrl.replaceAll(":", "%3A").replaceAll("/", "%2F"));
        String jsonResponse = getResponseJSON(url.toString());
        JSONObject jObj = new JSONObject(jsonResponse);
        int resultsFound = jObj.getJSONObject("header").getInt("results_returned");
        if (resultsFound != 0) {
            int index = 0;
            try {
                jObj.getJSONArray("results").getJSONObject(index).getJSONObject("data").getJSONArray("ext_urls");
            } catch (JSONException e) {
                index++;
            }
            return jObj.getJSONArray("results").getJSONObject(index);
        } else {
            return null;
        }
    }

    /**
     * Convert result to formatted embed
     * @param results JSON object from SauceNAO with a result
     * @return Formatted, ready-for-send embed.
     */
    public MessageEmbed toEmbed(JSONObject result) {
        if (result != null) {
            EmbedBuilder sauce = new EmbedBuilder();
            sauce.setColor(Color.decode("#" + RailgunBot.colorHelper.getColor(result.getJSONObject("header").getString("thumbnail"))));
            try {
                sauce.setAuthor("Authored by: " + result.getJSONObject("data").getString("member_name"));
            } catch (JSONException e) {
                sauce.setAuthor("Authored by: N/A");
            }
            sauce.setDescription("*Similarity* **" + result.getJSONObject("header").getString("similarity") + "%**");
            sauce.setImage(result.getJSONObject("header").getString("thumbnail"));
            try {
                sauce.setTitle(result.getJSONObject("data").getString("title"), result.getJSONObject("data").getJSONArray("ext_urls").getString(0));
            } catch (JSONException e) {
                sauce.setTitle("Title unavailable", result.getJSONObject("data").getJSONArray("ext_urls").getString(0));
            }
            return sauce.build();
        } else {
            EmbedBuilder sauce = new EmbedBuilder();
            sauce.setColor(Color.PINK);
            sauce.setTitle("Sauce not found! :(");
            return sauce.build();
        }
    }
}