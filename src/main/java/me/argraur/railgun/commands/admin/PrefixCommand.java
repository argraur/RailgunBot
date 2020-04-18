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

package me.argraur.railgun.commands.admin;

import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import static me.argraur.railgun.RailgunBot.prefixHelper;

import me.argraur.railgun.RailgunBot;

public class PrefixCommand implements RailgunOrder {
    private String command = "prefix";
    private String usage = command + " <new prefix>";
    private String description = "Sets new prefix for the guild";

    @Override
    public String getCommand() {
        return command;
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
        EmbedBuilder embedBuilder = new EmbedBuilder();
        try {
            prefixHelper.setPrefixForGuild(message, message.getContentDisplay().split(" ")[1]);
            embedBuilder.setTitle("New command prefix set!");
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        embedBuilder.setDescription("Current prefix: `" + RailgunBot.prefixHelper.getPrefixForGuild(message) + "`");
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}