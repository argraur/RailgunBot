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

import java.awt.Color;
import java.util.Random;

import me.argraur.railgun.interfaces.Command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class CoinFlip implements Command {
    private final String command = "coin";
    private final String usage = command;
    private final String description = "Flip a coin!";

    private final Random random = new Random();
    private final String heads = "https://media.discordapp.net/attachments/698965374317625345/699883972842487879/fMemdeFmPeYAAAAAASUVORK5CYII.png";
    private final String tails = "https://cdn.discordapp.com/attachments/698965374317625345/699884341219557437/Y5vgPrzC7Iazmd78B6LVfyzD7HfwBKvIh3eeptpAAAAABJRU5ErkJggg.png";

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
        embedBuilder.setDescription(message.getAuthor().getAsMention());
        final int side = random.nextInt(1000) % 2;
        embedBuilder.setTitle(side == 0 ? "It's heads!" : "It's tails!");
        embedBuilder.setImage(side == 0 ? heads : tails);
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}