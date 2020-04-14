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
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

public class BanCommand implements RailgunOrder {
    private String banCommand = "ban";

    @Override
    public StringBuilder getHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append(banCommand).append(" <mention> - Bans mentioned user");
        return sb;
    }

    @Override
    public String getCommand() {
        return banCommand;
    }

    @Override
    public void call(Message message) {
        String args = message.getContentRaw();
        if (message.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            message.getGuild().ban(message.getMentionedMembers().get(0), 0).queue();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("BAN!");
            embedBuilder.setDescription("Sayounara, mou kunaide kudasai " + args.split(" ")[1] + "!");
            embedBuilder.setImage(giphyHelper.searchRandomGif("ban"));
            message.getChannel().sendMessage(embedBuilder.build()).queue();
        }
    }
}