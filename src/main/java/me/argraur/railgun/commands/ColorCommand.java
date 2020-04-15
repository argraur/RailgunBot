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

package me.argraur.railgun.commands;

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.interfaces.RailgunOrder;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.Color;

public class ColorCommand implements RailgunOrder {
    private String colorCommand = "color";
    private String usage = colorCommand + " <url to image> OR attach image";
    private String description = "Get dominant color out of an image";

    @Override
    public void call(Message message) {
        String url = "";
        try {
            url = message.getAttachments().get(0).getUrl();
        } catch (IndexOutOfBoundsException e) {
            url = message.getContentRaw().split(" ")[1];
        }
        String hex = RailgunBot.colorHelper.getColor(url);
        String imageUrl = "https://dummyimage.com/100x100/" + hex + "/" + hex + ".jpg";
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("#" + hex);
        embedBuilder.setColor(Color.decode("#" + hex));
        embedBuilder.setImage(imageUrl);
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getCommand() {
        return colorCommand;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getDescription() {
        return description;
    }
}