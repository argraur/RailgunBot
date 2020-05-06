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

package me.argraur.railgun.commands.pseudo;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.LinkedHashMap;

import me.argraur.railgun.interfaces.Command;

public class Help {
    private static String command = "help";
    private static String usage = command + " <command>";
    private static String description = "Bot usage help!";

    /**
     * Creates embed help for given command.
     * @param output Description got from call(...) method
     * @param command Command we need to create embed for
     * @return Ready embed help for given command.
     */
    public static MessageEmbed createEmbed(final String description, final String usage, final String command) {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.pink);
        embedBuilder.setTitle(command);
        embedBuilder.addField("Description", description, false);
        embedBuilder.addField("Usage", "`" + usage + "`", false);
        final String misakaPic = "https://media.discordapp.net/attachments/698965374317625345/699022743542169691/oof.jpg?width=799&height=677";
        embedBuilder.setThumbnail(misakaPic);
        return embedBuilder.build();
    }

    /**
     * Creates embed help for given command.
     * @param output Description got from call(...) method
     * @param command Command we need to create embed for
     * @return Ready embed help for given command.
     */
    public static MessageEmbed createEmbed(final String description, final String usage, final String commands, final String command) {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.pink);
        embedBuilder.setTitle(command);
        embedBuilder.addField("Description", description, false);
        embedBuilder.addField("Usage", "`" + usage + "`", false);
        embedBuilder.addField("Available commands", commands, false);
        final String misakaPic = "https://media.discordapp.net/attachments/698965374317625345/699022743542169691/oof.jpg?width=799&height=677";
        embedBuilder.setThumbnail(misakaPic);
        return embedBuilder.build();
    }

    /**
     * Called by CommandHandler when received message with command
     * 
     * @param commands HashMap of all commands
     * @param prefix Current guild's prefix
     * @param message Message from Discord
     */
    public static void call(final LinkedHashMap<String, Command> commands, final String prefix, final Message message) {
        final LinkedHashMap<String, MessageEmbed> help = new LinkedHashMap<>();
        if (message.getContentDisplay().split(" ").length > 1) {
            for (final Command command : commands.values()) {
                System.out.println("[HelpCommand] Loading help for command " + command.getCommand());
                help.put(command.getCommand(), createEmbed(
                        command.getDescription(),
                        prefix + command.getUsage(),
                        prefix + command.getCommand()
                ));
            }
        }
        final StringBuilder helpCommand = new StringBuilder().append("```fix\n");
        for (final Command command : commands.values())
            helpCommand.append(command.getCommand()).append(" ");
        helpCommand.append("\n```");
        help.put(command, createEmbed(
                        description,
                        prefix + usage,
                        helpCommand.toString(),
                        prefix + command
                ));
        try {
            message.getChannel().sendMessage(help.get(message.getContentDisplay().split(" ")[1])).queue();
        } catch (final IndexOutOfBoundsException e) {
            message.getChannel().sendMessage(help.get(command)).queue();
        } catch (final IllegalArgumentException e) {
            message.getChannel().sendMessage(
                new EmbedBuilder()
                    .setColor(Color.PINK)
                    .setTitle("Command not found!")
                    .setThumbnail("https://media.discordapp.net/attachments/698965374317625345/699022743542169691/oof.jpg?width=799&height=677")
                    .build()
            ).queue();
        }
    }
}
