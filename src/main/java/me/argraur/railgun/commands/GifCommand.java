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

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.helpers.GiphyHelper;
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;
import java.util.Random;

public class GifCommand implements RailgunOrder {
    private String gifCommand = RailgunBot.COMMAND_PREFIX + "gif";
    private MessageChannel messageChannel;
    private Message msg;

    public GifCommand(MessageChannel messageChannel, Message msg) {
        this.messageChannel = messageChannel;
        this.msg = msg;
    }

    @Override
    public String getOutput(String args) {
        return null;
    }

    @Override
    public String getCommand() {
        return gifCommand;
    }

    @Override
    public StringBuilder getHelp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(gifCommand);
        stringBuilder.append(" <query> [mention] - Search gif for a given query");
        return stringBuilder;
    }

    @Override
    public void call(String args) {
        String temp = args.replaceAll(gifCommand + " ", "");
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if (!msg.getMentionedMembers().isEmpty()) {
            embedBuilder.setDescription("<@" + msg.getMentionedMembers().get(0).getId() + ">");
            temp.replaceAll(" <@" + msg.getMentionedMembers().get(0).getId() + ">", "");
        }
        try {
            embedBuilder.setImage(RailgunBot.giphyHelper.searchRandomGif(temp));
        } catch (IllegalStateException e) { return; }
        embedBuilder.setColor(Color.PINK);
        messageChannel.sendMessage(embedBuilder.build()).queue();
    }
}
