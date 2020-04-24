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

import net.dv8tion.jda.api.entities.Message;

import java.util.Random;

public class Long implements Command {
    private final String command = "long";
    private final Random random = new Random();
    private final String usage = command + " <message>";
    private final String description = "Make your message looooooooooonger";

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
        final StringBuilder sb = new StringBuilder();
        for (int i = 1; i < message.getContentRaw().split(" ").length; i++) {
            sb.append(message.getContentRaw().split(" ")[i]).append(" ");
        }
        message.getChannel().sendMessage(getOutput(sb.toString())).queue();
    }

    public String getOutput(final String args) {
        final String[] temp = args.split(" ");
        final StringBuilder stringBuilder = new StringBuilder();
        StringBuilder oes = new StringBuilder();
        for (final String s : temp) {
            for (int i = 0; i < random.nextInt(100); i++) {
                oes.append("o");
            }
            stringBuilder.append(s.replaceAll(
                    "o", oes.toString()
            )).append(" ");
            oes = new StringBuilder();
        }
        return stringBuilder.toString();
    }
}
