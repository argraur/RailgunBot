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

package me.argraur.railgun.helpers;

import java.util.Random;

import at.mukprojects.giphy4j.Giphy;
import at.mukprojects.giphy4j.entity.search.SearchFeed;
import at.mukprojects.giphy4j.exception.GiphyException;

public class GiphyHelper {
    private String token;
    private Giphy giphy;
    private Random random = new Random();

    /**
     * Default constructor
     * @param token Giphy developer token
     */
    public GiphyHelper(String token) {
        this.token = token;
        System.out.println("GiphyReader is ready!");
    }

    /**
     * Searches and returns GIF URL
     * @param query Search query
     * @return Gif URL
     */
    public String searchRandomGif(String query) {
        giphy = new Giphy(token);
        try {
            SearchFeed feed = giphy.search(query, 20, 0);
            return feed.getDataList().get(random.nextInt(feed.getDataList().size())).getImages().getOriginal().getUrl();
        } catch (GiphyException ignored) {}
        throw new IllegalStateException();
    }
}