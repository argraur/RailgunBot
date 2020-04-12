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

import at.mukprojects.giphy4j.Giphy;
import at.mukprojects.giphy4j.entity.search.SearchFeed;
import at.mukprojects.giphy4j.exception.GiphyException;
import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;
import java.util.Random;

public class HugCommand implements RailgunOrder {
    private String hugCommand = RailgunBot.COMMAND_PREFIX + "hug";
    private final String token;
    private MessageChannel messageChannel;
    private Message msg;
    private Random random = new Random();

    public HugCommand(MessageChannel messageChannel, Message msg) {
        this.messageChannel = messageChannel;
        this.msg = msg;
        token = RailgunBot.readConfig("giphy");
    }

    @Override
    public String getOutput(String args) {
        return null;
    }

    @Override
    public String getCommand() {
        return hugCommand;
    }

    @Override
    public StringBuilder getHelp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(hugCommand);
        stringBuilder.append(" <mention> - Hugs the mentioned user!");
        return stringBuilder;
    }

    @Override
    public void call(String args) {
        Giphy giphy = new Giphy(token);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("HUG YOU!");
        embedBuilder.setDescription("<@" + msg.getMentionedMembers().get(0).getId() + ">");
        try {
            SearchFeed feed = giphy.search("anime hug", 20, 0);
            embedBuilder.setImage(feed.getDataList().get(random.nextInt(20)).getImages().getOriginal().getUrl());
        } catch (GiphyException ignored) {}
        embedBuilder.setColor(Color.PINK);
        messageChannel.sendMessage(embedBuilder.build()).queue();
    }
}
