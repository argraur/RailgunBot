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

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.util.Random;

public class Slap implements Command {
    private final String command = "slap";
    private final String usage = command + " <@user>";
    private final String description = "Slap a user!";

    private final Random rnd = new Random();
    private final String[] urlArr = {
            "https://cdn.discordapp.com/attachments/356835182818623489/699024624121282661/Slap_9.gif",
            "https://media.discordapp.net/attachments/698965374317625345/699023483266662461/Slap_1.gif",
            "https://media.giphy.com/media/AlsIdbTgxX0LC/giphy.gif",
            "https://media.giphy.com/media/iVKn5ZEhadTHy/giphy.gif",
            "https://media1.tenor.com/images/7437caf9fb0bea289a5bb163b90163c7/tenor.gif?itemid=13595529",
            "https://media1.tenor.com/images/4a6b15b8d111255c77da57c735c79b44/tenor.gif?itemid=10937039",
            "https://media.tenor.com/images/d63b9fcb5b77728c29427d27f142b096/tenor.gif",
            "https://media1.tenor.com/images/047e78a99ba8c1ea4bfa6237d9f484aa/tenor.gif?itemid=8065158" };

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
        embedBuilder.setTitle("SLAP!");
        embedBuilder.setDescription("<@" + message.getAuthor().getId() + ">" + " slaps " + message.getContentRaw().split(" ")[1] + "!");
        embedBuilder.setImage(urlArr[rnd.nextInt(8)]);
        embedBuilder.setColor(Color.PINK);
        message.delete().queue();
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}
