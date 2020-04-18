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

import me.argraur.railgun.interfaces.RailgunOrder;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class CoinFlipCommand implements RailgunOrder {
    private String coinFlipCommand = "coin";
    private String usage = coinFlipCommand;
    private String description = "Flip a coin!";
    private Random random = new Random();
    private String heads = "https://media.discordapp.net/attachments/698965374317625345/699883972842487879/fMemdeFmPeYAAAAAASUVORK5CYII.png";
    private String tails = "https://cdn.discordapp.com/attachments/698965374317625345/699884341219557437/Y5vgPrzC7Iazmd78B6LVfyzD7HfwBKvIh3eeptpAAAAABJRU5ErkJggg.png";

    @Override
    public String getUsage() { return usage; }

    @Override
    public String getDescription() { return description; }

    @Override
    public String getCommand() { return coinFlipCommand; }

    @Override
    public void call(Message message) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.PINK);
        embedBuilder.setDescription(message.getAuthor().getAsMention());
        int side = random.nextInt(1000) % 2;
        embedBuilder.setTitle(side == 0 ? "It's heads!" : "It's tails!");
        embedBuilder.setImage(side == 0 ? heads : tails);
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}