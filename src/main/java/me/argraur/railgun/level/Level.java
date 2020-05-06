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

package me.argraur.railgun.level;

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.interfaces.Command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

/**
 * Levels of command access
 */
public class Level {
    public static final int OWNER = 0;
    public static final int ADMIN = 1;
    public static final int MESSAGE = 2;
    public static final int KICK = 3;
    public static final int BAN = 4;
    public static final int NORMAL = 5;

    /**
     * 
     */
    public static boolean checkLevel(Message message, Command command) {
        if (message.getMember().getId() == RailgunBot.configHelper.getValue("goshujinsama"))
            return true;
        switch (command.getLevel()) {
            case OWNER:
                return false;
            case ADMIN:
                if (message.getMember().hasPermission(Permission.ADMINISTRATOR))
                    return true;
            case MESSAGE:
                if (message.getMember().hasPermission(Permission.MESSAGE_MANAGE))
                    return true;
            case KICK:
                if (message.getMember().hasPermission(Permission.KICK_MEMBERS))
                    return true;
            case BAN:
                if (message.getMember().hasPermission(Permission.BAN_MEMBERS))
                    return true;
            case NORMAL:
                return true;
        }
        return false;
    }
}