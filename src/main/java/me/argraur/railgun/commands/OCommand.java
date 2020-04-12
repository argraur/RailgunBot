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
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.Random;

public class OCommand implements RailgunOrder {
    private String oCommand = RailgunBot.COMMAND_PREFIX + "long";
    private String oHelp = oCommand + " - loooooooooooooooooong";
    private Random random = new Random();
    private MessageChannel messageChannel;
    private String[] oArr = {
            "oo",
            "ooooo",
            "ooooooooo",
            "ooooooooooooo"
    };

    public OCommand(MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }

    @Override
    public void call(String args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.split(" ").length; i++) {
            sb.append(args.split(" ")[i]).append(" ");
        }
        messageChannel.sendMessage(getOutput(sb.toString())).queue();
    }

    @Override
    public String getOutput(String args) {
        String[] temp = args.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : temp) {
            stringBuilder.append(s.replaceAll(
                    "o",
                    oArr[random.nextInt(3)]
            )).append(" ");
        }
        return stringBuilder.toString();
    }

    @Override
    public String getCommand() {
        return oCommand;
    }

    @Override
    public StringBuilder getHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append(oHelp);
        return sb;
    }
}
