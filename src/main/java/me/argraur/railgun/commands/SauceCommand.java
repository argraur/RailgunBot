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

import static me.argraur.railgun.RailgunBot.sauceNAOApi;

import java.awt.Color;

import me.argraur.railgun.helpers.ImageHelper;
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class SauceCommand implements RailgunOrder {
    private String sauceCommand = "sauce";
    private String usage = sauceCommand + " <url to image> OR attach image";
    private String description = "Search for source of given anime art!";

    @Override
    public String getCommand() {
        return sauceCommand;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void call(Message message) {
        String imageUrl = ImageHelper.getImageUrl(message);
        System.out.println(imageUrl);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Processing image...");
        embedBuilder.setDescription(message.getAuthor().getAsMention());
        embedBuilder.setImage(imageUrl);
        embedBuilder.setColor(Color.PINK);
        message.getChannel().sendMessage(embedBuilder.build()).queue(
            (response) -> {
                try {
                    MessageEmbed result = sauceNAOApi.toEmbed(sauceNAOApi.search(message));
                    message.delete().queue();
                    response.editMessage(result).queue();
                } catch (Exception e) {
                    e.printStackTrace();
                    response.editMessage(embedBuilder.setTitle("Processing failed. No source links were found.").build()).queue();
                }     
            }
        );
    }
}