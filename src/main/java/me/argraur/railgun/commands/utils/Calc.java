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

import java.awt.Color;

import me.argraur.railgun.interfaces.Command;

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.helpers.HelperManager;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class Calc implements Command {
    private String command = "calc";
    private String description = "Simple calculator.";
    private String usage = command + " <expression> (e.g " + command + " 2 * 2 * 2 (with spaces)";

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
    public void call(Message message) {
        String args = message.getContentRaw();
        String[] arr = args.replace(HelperManager.prefix.getPrefixForGuild(message) + command + " ", "").toString().split(" ");
        float tmp = Float.parseFloat(arr[0]); float result = 0;
        for (int i = 1; i < arr.length; i++) {
            try {
                tmp = Float.parseFloat(arr[i]);
            } catch (NumberFormatException nfe) {
                switch (arr[i]) {
                    case "/":
                        result = tmp / Float.parseFloat(arr[i + 1]);
                        break;
                    case "*":
                        result = tmp * Float.parseFloat(arr[i + 1]);
                        break;
                    case "-":
                        result = tmp - Float.parseFloat(arr[i + 1]);
                        break;
                    case "+":
                        result = tmp + Float.parseFloat(arr[i + 1]);
                        break;
                }
                tmp = result;
                i++;
            }
        }
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.PINK);
        embedBuilder.setTitle(args.replace(RailgunBot.COMMAND_PREFIX + command + " ", ""));
        embedBuilder.setDescription("**Result: " + String.valueOf(result) + "**");
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}