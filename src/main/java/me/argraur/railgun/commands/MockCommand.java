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

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.entities.MessageChannel;

public class MockCommand implements RailgunOrder {
    private String command = RailgunBot.COMMAND_PREFIX + "mock";
    private MessageChannel messageChannel;

    public MockCommand(MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public void call(String args) {
        messageChannel.sendMessage(getOutput(args)).queue();
    }

    @Override
    public String getOutput(String args) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < args.split("").length; i++) {
            if (new Random().nextInt(20) % 2 == 0) {
                result.append(args.split("")[i].toUpperCase());
            } else {
                result.append(args.split("")[i]);
            }
        }
        result.replace(0, command.length(), "");
        return result.toString();
    }

    @Override
    public StringBuilder getHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append(command).append(" - " + getOutput(command + " mocks given message"));
        return sb;
    }
}