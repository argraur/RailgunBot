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

import java.util.Random;

import me.argraur.railgun.interfaces.RailgunOrder;

import static me.argraur.railgun.RailgunBot.COMMAND_PREFIX;

import net.dv8tion.jda.api.entities.Message;

public class MockCommand implements RailgunOrder {
    private String command = "mock";
    private String usage = command + " <message>";
    private String description = getOutput("Mock the message!", false);

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public void call(Message message) {
        message.getChannel().sendMessage(getOutput(message.getContentRaw(), true)).queue();
    }

    public String getOutput(String args, boolean replace) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < args.split("").length; i++) {
            if (new Random().nextInt(20) % 2 == 0) {
                result.append(args.split("")[i].toUpperCase());
            } else {
                result.append(args.split("")[i]);
            }
        }
        if (replace) result.replace(0, (COMMAND_PREFIX + command).length(), "");
        return result.toString();
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