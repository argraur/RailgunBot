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

package me.argraur.railgun.commands;

import static me.argraur.railgun.RailgunBot.ignoreHelper;
import static me.argraur.railgun.commands.IgnoreCommand.senpaiId;

import me.argraur.railgun.interfaces.RailgunOrder;

import net.dv8tion.jda.api.entities.Message;

public class PardonCommand implements RailgunOrder {
    private String pardonCommand = "pardon";
    private String usage = pardonCommand + " <@user>";
    private String description = "Allow previously ignored `<@user>` to use bot.";

    @Override
    public String getCommand() {
        return pardonCommand;
    }

    @Override
    public void call(Message message) {
        if (message.getAuthor().getId().equals(senpaiId) && !message.getMentionedUsers().get(0).getId().equals(senpaiId)) {
            ignoreHelper.delIgnored(message.getMentionedUsers().get(0).getId(), message);
        }
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