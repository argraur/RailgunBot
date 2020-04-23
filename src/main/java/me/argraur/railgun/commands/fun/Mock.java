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

import java.util.Random;

import me.argraur.railgun.interfaces.Command;

import me.argraur.railgun.helpers.HelperManager;

import net.dv8tion.jda.api.entities.Message;

public class Mock implements Command {
    private final String command = "mock";
    private final String usage = command + " <message>";
    private final String description = "Mock the message!";

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
     * @param Message object
     */
    @Override
    public void call(final Message message) {
        message.getChannel().sendMessage(getOutput(message, true)).queue();
    }

    /**
     * Mocks the message.
     * @param message Discord message
     * @param replace Whether should replace prefix or not
     * @return Mocked message string
     */
    public String getOutput(final Message message, final boolean replace) {
        final String args = message.getContentRaw();
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < args.split("").length; i++) {
            if (new Random().nextInt(20) % 2 == 0) {
                result.append(args.split("")[i].toUpperCase());
            } else {
                result.append(args.split("")[i]);
            }
        }
        if (replace) result.replace(0, (HelperManager.prefix.getPrefixForGuild(message) + command).length(), "");
        return result.toString();
    }
}