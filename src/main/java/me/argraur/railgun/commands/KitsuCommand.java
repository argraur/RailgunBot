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
import me.argraur.railgun.apis.KitsuAPI;
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.entities.MessageChannel;

public class KitsuCommand implements RailgunOrder {
    private KitsuAPI kitsuAPI;
    private MessageChannel messageChannel;

    private String kitsuCommand = RailgunBot.COMMAND_PREFIX + "kitsu";

    public KitsuCommand(MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
        kitsuAPI = new KitsuAPI();
    }

    @Override
    public String getOutput(String args) {
        // This command returns MessageEmbed, so we don't need this method.
        return null;
    }

    @Override
    public String getCommand() {
        return kitsuCommand;
    }

    @Override
    public StringBuilder getHelp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(kitsuCommand).append(" genre <genre> - Sends random anime of a given genre.").append("\n");
        stringBuilder.append(kitsuCommand).append(" search <query> - Searches anime by given query.");
        return stringBuilder;
    }

    @Override
    public void call(String args) {
        String temp = args;
        temp = temp.substring((getCommand() + " " + args.split(" ")[1]).length());
        switch (args.split(" ")[1]) {
            case "genre":
                messageChannel.sendMessage(kitsuAPI.toEmbed(kitsuAPI.searchRandomByGenre(args.split(" ")[2]))).queue();
                break;
            case "search":
                messageChannel.sendMessage(kitsuAPI.toEmbed(kitsuAPI.searchByQuery(temp))).queue();
            default: break;
        }
    }
}
