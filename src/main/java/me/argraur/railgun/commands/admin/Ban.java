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

import me.argraur.railgun.helpers.HelperManager;
import me.argraur.railgun.interfaces.Command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

public class Ban implements Command {
    private final String command = "ban";
    private final String usage = command + " <@user>";
    private final String description = "Bans mentioned user.";

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
     * @param Message object
     */
    @Override
    public void call(final Message message) {
        final String args = message.getContentRaw();
        if (message.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            message.getGuild().ban(message.getMentionedMembers().get(0), 0).queue();
            final EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("BAN!");
            embedBuilder.setDescription("Sayounara, mou kunaide kudasai " + args.split(" ")[1] + "!");
            embedBuilder.setImage(HelperManager.giphy.searchRandomGif("ban"));
            message.getChannel().sendMessage(embedBuilder.build()).queue();
        }
    }
}