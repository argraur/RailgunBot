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

package me.argraur.railgun.commands.utils;

import me.argraur.railgun.interfaces.RailgunOrder;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.LinkedHashMap;

public class HelpCommand implements RailgunOrder {
    private String helpCommand = "help";
    private String usage = helpCommand + " <command>";
    private String description = "Bot usage help!";
    private LinkedHashMap<String, MessageEmbed> help = new LinkedHashMap<>();

    public HelpCommand(LinkedHashMap<String, RailgunOrder> commands, String prefix) {
        for (RailgunOrder command : commands.values()) {
            System.out.println("[HelpCommand] Loading help for command " + command.getCommand());
            StringBuilder tmp = new StringBuilder()
                .append("**Description:** *")
                .append(command.getDescription())
                .append("*\n")
                .append("**Usage:** `")
                .append(prefix)
                .append(command.getUsage())
                .append("`\n\n");
            help.put(command.getCommand(), createEmbed(tmp, prefix + command.getCommand()));
        }
        addHelp(commands, prefix);
    }

    private void addHelp(LinkedHashMap<String, RailgunOrder> commands, String prefix) {
        StringBuilder helpCommand = new StringBuilder()
            .append("**Description:** *")
            .append(this.getDescription())
            .append("*\n")
            .append("**Usage: ** `")
            .append(prefix)
            .append(this.getUsage())
            .append("`\n")
            .append("**Available commands: ** ```fix\n");
        for (RailgunOrder command: commands.values())
            helpCommand.append(command.getCommand()).append("\n");
        helpCommand.append("\n```\n");
        help.put(this.getCommand(), createEmbed(helpCommand, prefix + this.getCommand()));
    }

    public MessageEmbed createEmbed(StringBuilder output, String command) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.pink);
        embedBuilder.setTitle(command);
        embedBuilder.setDescription(output.toString());
        String misakaPic = "https://media.discordapp.net/attachments/698965374317625345/699022743542169691/oof.jpg?width=799&height=677";
        embedBuilder.setThumbnail(misakaPic);
        return embedBuilder.build();
    }

    @Override
    public void call(Message message) {
        try {
            message.getChannel().sendMessage(help.get(message.getContentDisplay().split(" ")[1])).queue();
        } catch (IndexOutOfBoundsException e) {
            message.getChannel().sendMessage(help.get(helpCommand)).queue();
        }
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
