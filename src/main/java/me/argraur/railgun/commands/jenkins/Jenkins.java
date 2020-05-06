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

package me.argraur.railgun.commands.jenkins;

import me.argraur.railgun.interfaces.Command;

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.commands.jenkins.subcommands.JenkinsBuild;
import me.argraur.railgun.commands.jenkins.subcommands.JenkinsBuildStatus;
import me.argraur.railgun.level.Level;
import net.dv8tion.jda.api.entities.Message;

public class Jenkins implements Command {
    private final String command = "jenkins";
    private final String usage = command + " status/build <jobname>";
    private final String description = "Builds given Jenkins job or gets status for it";
    private final int level = Level.OWNER;

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
     * Returns command's access level
     * 
     * @return level
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * Called by CommandHandler when received message with command
     * 
     * @param message object
     */
    @Override
    public void call(Message message) {
        if (message.getAuthor().getId().equals(RailgunBot.configHelper.getValue("goshujinsama"))) {
            switch (message.getContentDisplay().split(" ")[1]) {
                case "build":
                    Command build = new JenkinsBuild();
                    build.call(message);
                    break;
                case "status":
                    Command status = new JenkinsBuildStatus();
                    status.call(message);
                    break;
            }
        }
    }
}