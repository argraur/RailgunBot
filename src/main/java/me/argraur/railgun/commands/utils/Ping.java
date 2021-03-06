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

package me.argraur.railgun.commands.utils;

import java.awt.Color;

import me.argraur.railgun.interfaces.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class Ping implements Command {
    private final String command = "ping";
    private final String usage = command;
    private final String description = "Pong!";

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
        final long time = System.currentTimeMillis();
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(description);
        embedBuilder.setColor(Color.pink);
        message.getChannel().sendMessage(embedBuilder.build()).queue((response) -> {
            response.editMessage(new EmbedBuilder(response.getEmbeds().get(0)).setTitle(description + " " + (System.currentTimeMillis() - time) + " ms").build()).queue();
        });
    }
}
