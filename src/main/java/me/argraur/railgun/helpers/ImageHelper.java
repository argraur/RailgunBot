package me.argraur.railgun.helpers;

import net.dv8tion.jda.api.entities.Message;

public class ImageHelper {
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