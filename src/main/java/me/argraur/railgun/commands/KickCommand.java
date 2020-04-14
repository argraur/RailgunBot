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
import me.argraur.railgun.interfaces.RailgunOrder;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class KickCommand implements RailgunOrder {
    private String kickCommand = RailgunBot.COMMAND_PREFIX + "kick";
    private MessageChannel messageChannel;
    private Message msg;

    public KickCommand (MessageChannel messageChannel, Message msg) {
        this.messageChannel = messageChannel;
        this.msg = msg;
    }

    @Override
    public StringBuilder getHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append(kickCommand).append(" <mention> - Kicks mentioned user");
        return sb;
    }

    @Override
    public String getCommand() {
        return kickCommand;
    }

    @Override
    public String getOutput(String args) {
        return null;
    }

    @Override
    public void call(String args) {
        if (msg.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            msg.getGuild().kick(msg.getMentionedMembers().get(0)).queue();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("KICK!");
            embedBuilder.setDescription("Sayounara " + args.split(" ")[1] + "!");
            embedBuilder.setImage(RailgunBot.giphyHelper.searchRandomGif("kick"));
            messageChannel.sendMessage(embedBuilder.build()).queue();
        }
    }
}