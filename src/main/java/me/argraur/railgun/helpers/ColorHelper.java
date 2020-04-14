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

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import de.androidpit.colorthief.ColorThief;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ColorHelper {
    private OkHttpClient okHttpClient;

    public ColorHelper() {
        okHttpClient = new OkHttpClient();
        System.out.println("ColorHelper is ready!");
    }

    public String getColor(String url) {
        InputStream inputStream = null;
        BufferedImage imBuff;
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            try (Response response = okHttpClient.newCall(request).execute()) {
                inputStream = Objects.requireNonNull(response.body()).byteStream();
                imBuff = Objects.requireNonNull(ImageIO.read(inputStream));
                int[] rgb = ColorThief.getColor(imBuff);
                String hex = String.format("%02x%02x%02x", rgb[0], rgb[1], rgb[2]);
                return hex;
            }
        } catch (IOException ignored) {}
        return null;
    }
}