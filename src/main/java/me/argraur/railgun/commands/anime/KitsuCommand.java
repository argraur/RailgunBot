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

package me.argraur.railgun.commands.anime;

import static me.argraur.railgun.RailgunBot.prefixHelper;
import static me.argraur.railgun.RailgunBot.kitsuApi;

import me.argraur.railgun.interfaces.RailgunOrder;

import net.dv8tion.jda.api.entities.Message;

public class KitsuCommand implements RailgunOrder {
    private String kitsuCommand = "kitsu";
    private String usage = kitsuCommand + " genre/search <genre>/<query>";
    private String description = "Search anime by query or genre!";

    @Override
    public String getCommand() {
        return kitsuCommand;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void call(Message message) {
        String temp = message.getContentRaw();
        temp = temp.substring((prefixHelper.getPrefixForGuild(message) + getCommand() + " " + message.getContentRaw().split(" ")[1]).length());
        if (message.getContentRaw().split(" ")[1].equals("genre")) {
            message.getChannel().sendMessage(kitsuApi.toEmbed(kitsuApi.searchRandomByGenre(message.getContentRaw().split(" ")[2]))).queue();
        } else if (message.getContentRaw().split(" ")[1].equals("search")) {
            message.getChannel().sendMessage(kitsuApi.toEmbed(kitsuApi.searchByQuery(temp))).queue();
        }
    }
}
