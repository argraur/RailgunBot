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

package me.argraur.railgun.commands.jenkins.subcommands;

import me.argraur.railgun.interfaces.Command;

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.apis.jenkins.Status;

import net.dv8tion.jda.api.entities.Message;

public class JenkinsBuildStatus implements Command {
    private final String command = "jenkins status";
    private final String usage = command + " <jobname>";
    private final String description = "Gets status of latest build in given job";

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
    public void call(Message message) {
        if (!RailgunBot.jenkins.isJenkinsUp()) {
            message.getChannel().sendMessage("Connection to Jenkins is down. Please restart me.").queue();
            return;
        }
        Thread thread = new Thread(new Status(message, RailgunBot.jenkins.getServer()));
        thread.start();
    }
}