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

import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.entities.Message;

import java.util.Random;

public class OCommand implements RailgunOrder {
    private String oCommand = "long";
    private Random random = new Random();
    private String usage = oCommand + " <message>";
    private String description = "Make your message looooooooooonger";

    @Override
    public void call(Message message) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < message.getContentRaw().split(" ").length; i++) {
            sb.append(message.getContentRaw().split(" ")[i]).append(" ");
        }
        message.getChannel().sendMessage(getOutput(sb.toString())).queue();
    }

    public String getOutput(String args) {
        String[] temp = args.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder oes = new StringBuilder();
        for (String s : temp) {
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

    @Override
    public String getCommand() {
        return oCommand;
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
