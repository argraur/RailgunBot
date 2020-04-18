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

package me.argraur.railgun.commands.anime;

import java.awt.Color;

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.helpers.ImageHelper;
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class WaitCommand implements RailgunOrder {
    private String waitCommand = "wait";
    private String usage = waitCommand + " <url to image> OR attach image";
    private String description = "What Anime Is This? Find anime with just a screenshot!";

    @Override
    public String getCommand() {
        return waitCommand;
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
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Processing image...");
        embedBuilder.setDescription(message.getAuthor().getAsMention());
        embedBuilder.setImage(imageUrl);
        embedBuilder.setColor(Color.decode("#" + RailgunBot.colorHelper.getColor(imageUrl)));
        message.getChannel().sendMessage(embedBuilder.build()).queue(
            (response) -> {
                MessageEmbed result = RailgunBot.traceMoeApi.toEmbed(RailgunBot.traceMoeApi.search(message), message);
                message.delete().queue();
                response.editMessage(result).queue();
            }
        );
        
    }
}