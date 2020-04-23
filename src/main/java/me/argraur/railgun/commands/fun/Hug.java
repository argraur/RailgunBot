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

package me.argraur.railgun.commands.fun;

import me.argraur.railgun.interfaces.Command;

import me.argraur.railgun.helpers.HelperManager;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import static java.awt.Color.PINK;

public class Hug implements Command {
    private final String hugCommand = "hug";
    private final String usage = hugCommand + " <@user>";
    private final String description = "Hug a `<@user>` <3";

    /**
     * Returns command name.
     * 
     * @return command name
     */
    @Override
    public String getCommand() {
        return hugCommand;
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
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("HUG YOU!");
        embedBuilder.setDescription("<@" + message.getMentionedMembers().get(0).getId() + ">");
        embedBuilder.setImage(HelperManager.giphy.searchRandomGif("anime hug"));
        embedBuilder.setColor(PINK);
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}
