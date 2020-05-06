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

package me.argraur.railgun.commands.admin;

import me.argraur.railgun.interfaces.Command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.Color;

import me.argraur.railgun.helpers.HelperManager;
import me.argraur.railgun.level.Level;

public class Prefix implements Command {
    private final String command = "prefix";
    private final String usage = command + " <new prefix>";
    private final String description = "Sets new prefix for the guild";
    private final int level = Level.ADMIN;

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
     * Returns command's access level
     * 
     * @return level
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * Called by CommandHandler when received message with command
     * 
     * @param message object
     */
    @Override
    public void call(final Message message) {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        try {
            HelperManager.prefix.setPrefixForGuild(message, message.getContentDisplay().split(" ")[1]);
            embedBuilder.setTitle("New command prefix set!");
        } catch (final ArrayIndexOutOfBoundsException ignored) {
        }
        embedBuilder.setDescription("Current prefix: `" + HelperManager.prefix.getPrefixForGuild(message) + "`");
        embedBuilder.setColor(Color.PINK);
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}