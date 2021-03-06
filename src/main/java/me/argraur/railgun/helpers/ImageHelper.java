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

import net.dv8tion.jda.api.entities.Message;

public class ImageHelper {
    /**
     * Gets clear image URL for some circumstances
     * @param message Discord message
     * @return Clear URL
     */
    public static String getImageUrl(Message message) {
        String imageUrl = "";
        try {
            imageUrl = message.getAttachments().get(0).getUrl();
        } catch (IndexOutOfBoundsException e) {
            imageUrl = message.getContentRaw().split(" ")[1];   
        }
        if (imageUrl.contains("?")) {
            imageUrl = imageUrl.substring(0, imageUrl.indexOf("?"));
        }
        return imageUrl;
    }
}