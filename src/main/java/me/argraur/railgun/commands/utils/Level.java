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

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.interfaces.Command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.Color;

import static me.argraur.railgun.level.Level.*;

public class Level implements Command {
    private final String command = "level";
    private final String usage = "level <command>";
    private final String description = "Returns usage level of given command";

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
        String command = message.getContentDisplay().split(" ")[1];
        String level = "";
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.PINK);
        embedBuilder.setDescription(message.getAuthor().getAsMention());
        switch (RailgunBot.commandHandler.getCommand(command).getLevel()) {
            case ADMIN: level = "ADMIN"; break;
            case OWNER: level = "OWNER"; break;
            case MESSAGE: level = "MESSAGE"; break;
            case KICK: level = "KICK"; break;
            case BAN: level = "BAN"; break;
            case NORMAL: level = "NORMAL"; break;
        }
        embedBuilder.addField(command + "'s level", "```fix\n" + level + "\n```", false);
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}