/*
 * Copyright (C) 2018 The Pixel3ROM Project
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

import static me.argraur.railgun.RailgunBot.giphyHelper;

import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import static java.awt.Color.PINK;

public class HugCommand implements RailgunOrder {
    private String hugCommand = "hug";
    private String usage = hugCommand + " <@user>";
    private String description = "Hug a `<@user>` <3";

    @Override
    public String getCommand() {
        return hugCommand;
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
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("HUG YOU!");
        embedBuilder.setDescription("<@" + message.getMentionedMembers().get(0).getId() + ">");
        embedBuilder.setImage(giphyHelper.searchRandomGif("anime hug"));
        embedBuilder.setColor(PINK);
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}
