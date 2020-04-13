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
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.entities.Message;

public class IgnoreCommand implements RailgunOrder {
    private String ignoreCommand = RailgunBot.COMMAND_PREFIX + "ignore";
    public String senpaiId = "356723086009171969";
    public IgnoreHelper ignoreHelper;
    public Message msg;

    public IgnoreCommand(IgnoreHelper ignoreHelper, Message msg) {
        this.ignoreHelper = ignoreHelper;
        this.msg = msg;
    }

    @Override
    public String getOutput(String args) {
        return null;
    }

    @Override
    public StringBuilder getHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append(ignoreCommand).append(" <mention> - Ignores given user.");
        return sb;
    }

    @Override
    public void call(String args) {
        if (msg.getAuthor().getId().equals(senpaiId) && !msg.getMentionedUsers().get(0).getId().equals(senpaiId)) {
            ignoreHelper.addIgnored(msg.getMentionedUsers().get(0).getId(), msg);
        }
    }

    @Override
    public String getCommand() {
        return ignoreCommand;
    }
}