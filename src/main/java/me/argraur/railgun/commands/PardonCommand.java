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

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.helpers.IgnoreHelper;

import net.dv8tion.jda.api.entities.Message;

public class PardonCommand extends IgnoreCommand {
    private String pardonCommand = RailgunBot.COMMAND_PREFIX + "pardon";

    public PardonCommand(IgnoreHelper ignoreHelper, Message msg) {
        super(ignoreHelper, msg);
    }

    @Override
    public String getCommand() {
        return pardonCommand;
    }

    @Override
    public void call(String args) {
        if (super.msg.getAuthor().getId().equals(senpaiId) && !super.msg.getMentionedUsers().get(0).getId().equals(senpaiId)) {
            super.ignoreHelper.delIgnored(super.msg.getMentionedUsers().get(0).getId(), super.msg);
        }
    }

    @Override
    public StringBuilder getHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append(pardonCommand).append(" <mention> - Stops ignoring given user.");
        return sb;
    }
}