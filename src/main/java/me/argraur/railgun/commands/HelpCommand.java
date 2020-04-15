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

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.interfaces.RailgunOrder;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.LinkedHashMap;

public class HelpCommand implements RailgunOrder {
    private String helpCommand = "help";
    private String usage = helpCommand;
    private String description = "Bot usage help!";
    private String help;

    public HelpCommand(LinkedHashMap<String, RailgunOrder> commands) {
        StringBuilder output = new StringBuilder();
        for (RailgunOrder command : commands.values()) {
            System.out.println("HelpCommand_Constructor: Loading help for command " + command.getCommand());
            output.append("```fix\n" + RailgunBot.COMMAND_PREFIX + command.getCommand() + "\n```");
            output.append("**Description:** *" + command.getDescription() + "*\n");
            output.append("**Usage:** `" + RailgunBot.COMMAND_PREFIX + command.getUsage() + "`");
            output.append("\n\n");
        }
        output.append("Current command prefix: `" + RailgunBot.COMMAND_PREFIX + "`");
        this.help = output.toString();
    }

    public MessageEmbed createEmbed() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.pink);
        embedBuilder.setTitle("Misaka-chan!");
        embedBuilder.setDescription("I can't do many fancy stuff. BUT! Here is what I can do *currently*!\n\n" + help);
        String misakaPic = "https://media.discordapp.net/attachments/698965374317625345/699022743542169691/oof.jpg?width=799&height=677";
        embedBuilder.setThumbnail(misakaPic);
        return embedBuilder.build();
    }

    @Override
    public void call(Message message) {
        message.getChannel().sendMessage(createEmbed()).queue();
    }

    @Override
    public String getCommand() {
        return helpCommand;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
