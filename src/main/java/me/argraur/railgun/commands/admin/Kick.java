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

package me.argraur.railgun.commands.admin;

import me.argraur.railgun.interfaces.Command;

import me.argraur.railgun.helpers.HelperManager;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

public class Kick implements Command {
    private final String command = "kick";
    private final String usage = command + " <@user>";
    private final String description = "Kick `<@user>` from server";

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
        if (message.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            message.getGuild().kick(message.getMentionedMembers().get(0)).queue();
            final EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("KICK!");
            embedBuilder.setDescription("Sayounara " + message.getContentRaw().split(" ")[1] + "!");
            embedBuilder.setImage(HelperManager.giphy.searchRandomGif("kick"));
            message.getChannel().sendMessage(embedBuilder.build()).queue();
        }
    }
}