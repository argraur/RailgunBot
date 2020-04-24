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

import java.util.List;
import java.util.Random;

import me.argraur.railgun.interfaces.Command;

import me.argraur.railgun.RailgunBot;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class Juggle implements Command {
    public final String command = "juggle";
    public final String usage = command;
    public final String description = "JUGGLE VOICE CHANNEL MEMBERS";

    /**
     * Returns command name.
     * 
     * @return command name
     */
    @Override
    public String getCommand() {
        return command;
    }

    /**
     * Returns command's usage.
     * 
     * @return usage
     */
    @Override
    public String getUsage() {
        return usage;
    }

    /**
     * Returns command's description.
     * 
     * @return description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Called by CommandHandler when received message with command
     * 
     * @param message object
     */
    @Override
    public void call(final Message message) {
        if (message.getMember().hasPermission(Permission.ADMINISTRATOR)
                || message.getMember().getId().equals(RailgunBot.configHelper.getValue("goshujinsama"))) {
            final List<VoiceChannel> vcList = message.getGuild().getVoiceChannels();
            for (int i = 0; i < vcList.size(); i++) {
                for (final VoiceChannel vc : vcList) {
                    for (final Member member : vc.getMembers()) {
                        member.getGuild().moveVoiceMember(member, vcList.get(new Random().nextInt(vcList.size()))).queue();
                    }
                }
            }
        }
    }
}