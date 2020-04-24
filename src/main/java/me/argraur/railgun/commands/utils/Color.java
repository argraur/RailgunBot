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

package me.argraur.railgun.commands.utils;

import me.argraur.railgun.interfaces.Command;

import me.argraur.railgun.helpers.HelperManager;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import static java.awt.Color.decode;

public class Color implements Command {
    private final String command = "color";
    private final String usage = command + " <url to image> OR attach image";
    private final String description = "Get dominant color out of an image";

    /**
     * Returns command name.
     * 
     * @return command name
     */
    @Override
    public String getCommand() {
        return command;
    }

    /**
     * Returns command's usage.
     * 
     * @return usage
     */
    @Override
    public String getUsage() {
        return usage;
    }

    /**
     * Returns command's description.
     * 
     * @return description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Called by CommandHandler when received message with command
     * 
     * @param message object
     */
    @Override
    public void call(final Message message) {
        String url = "";
        try {
            url = message.getAttachments().get(0).getUrl();
        } catch (final IndexOutOfBoundsException e) {
            url = message.getContentRaw().split(" ")[1];
        }
        final String hex = HelperManager.color.getColor(url);
        final String imageUrl = "https://dummyimage.com/100x100/" + hex + "/" + hex + ".jpg";
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("#" + hex);
        embedBuilder.setColor(decode("#" + hex));
        embedBuilder.setImage(imageUrl);
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}