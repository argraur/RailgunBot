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
import net.dv8tion.jda.api.entities.Message;

public class WaitCommand implements RailgunOrder {
    private String waitCommand = "wait";

    @Override
    public String getCommand() {
        return waitCommand;
    }

    @Override
    public StringBuilder getHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append(waitCommand).append(" [attached image] - WAIT: What Anime Is This? Searches for anime using given screenshot from anime!");
        return sb;
    }

    @Override
    public void call(Message message) {
        message.getChannel().sendMessage(RailgunBot.traceMoeApi.toEmbed(RailgunBot.traceMoeApi.search(message))).queue();
    }
}