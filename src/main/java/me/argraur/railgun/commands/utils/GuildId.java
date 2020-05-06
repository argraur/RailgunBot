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

package me.argraur.railgun.commands.utils;

import me.argraur.railgun.interfaces.Command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.Color;

public class GuildId implements Command {
    private String command = "guildid";
    private String usage = command;
    private String description = "Returns guild ID";

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
        embedBuilder.setColor(Color.PINK);
        embedBuilder.setDescription(message.getAuthor().getAsMention());
        embedBuilder.addField(message.getGuild().getName() + "'s ID", "`" + message.getGuild().getId() + "`", false);
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}