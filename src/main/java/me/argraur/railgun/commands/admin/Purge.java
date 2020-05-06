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

package me.argraur.railgun.commands.admin;

import me.argraur.railgun.level.Level;

import java.util.List;

import me.argraur.railgun.interfaces.Command;

import net.dv8tion.jda.api.entities.Message;

public class Purge implements Command {
    private final String command = "purge";
    private final String usage = command + " <n>";
    private final String description = "Removes n last messages.";
    private final int level = Level.MESSAGE;

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Returns command's access level
     * 
     * @return level
     */
    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void call(Message message) {
        try {
            int amount = Integer.parseInt(message.getContentRaw().split(" ")[1]);
            message.getChannel().purgeMessages(message.getChannel().getHistory().retrievePast(amount + 1).complete());
        } catch (NumberFormatException e) {
            final String id = message.getContentRaw().split(" ")[1].split("/")[6];
            List<Message> history = message.getChannel().getHistoryAfter(id, 100).complete().getRetrievedHistory();
            message.getChannel().deleteMessageById(id).queue();
            message.getChannel().purgeMessages(history);
        }
    }
}