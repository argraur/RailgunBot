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

package me.argraur.railgun.commands.fun;

import java.awt.Color;
import java.util.Random;

import me.argraur.railgun.interfaces.Command;

import me.argraur.railgun.helpers.HelperManager;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class Ball implements Command {
    private final String command = "8ball";
    private final String usage = command + " <question>";
    private final String description = "Get answers to all of your yes/no questions!";
    private final String[] replies = {
            "It is certain", "It is decidedly so", "Without a doubt", "Yes - definitely",
            "You may rely on it", "As I see it, yes", "Most likely", "Outlook good", "Signs point to yes", "Yes",
            "Reply hazy, try again", "Ask again later", "Better not tell you now", "Cannot predict now",
            "Concentrate and ask again", "Don't count on it", "My reply is no", "My sources say no",
            "Outlook not so good", "Very doubtful"
    };

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
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.PINK);
        embedBuilder.setTitle(replies[new Random().nextInt(replies.length)]);
        embedBuilder.setDescription(message.getContentRaw().replace(HelperManager.prefix.getPrefixForGuild(message) + command + " ", ""));
        message.getChannel().sendMessage(message.getAuthor().getAsMention()).queue(
            (response) -> {
                response.editMessage(embedBuilder.build()).queue();
            }
        );
    }
}