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

package me.argraur.railgun.handlers;

import java.io.IOException;
import java.util.HashMap;

import me.argraur.railgun.RailgunBot;

import static me.argraur.railgun.helpers.SaveHelper.readObject;
import static me.argraur.railgun.helpers.SaveHelper.writeObject;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

public class MuteHandler {
    private HashMap<String, String> muteMap;
    private static String file = "data/mutedUsers.hashmap";

    public MuteHandler() {
        try {
            Object obj = readObject(file);
            if (obj instanceof HashMap)
                muteMap = (HashMap<String, String>) obj;
        } catch (IOException | ClassNotFoundException e) {
            muteMap = new HashMap<>();
            write();
        }
        System.out.println("[MuteHandler] Ready!");
    }

    public void write() {
        try {
            writeObject(muteMap, file);
        } catch (IOException ee) {
            System.out.println("[MuteHandler] [FATAL] Write failed!");
            ee.printStackTrace();
            RailgunBot.error(this);
            return;
        }
    }
    
    public boolean canMute(Message message) {
        if (message.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            return true;
        }
        return false;
    }

    public boolean isSenderMuted(Message message) {
        try {
            if (muteMap.get(message.getChannel().getId()).contains(message.getAuthor().getAsMention()))
                return true;
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean isMuted(Message message) {
        try {
            if (muteMap.get(message.getChannel().getId()).contains(message.getMentionedMembers().get(0).getAsMention()))
                return true;
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public void mute(Message message) {
        if (!isMuted(message) && canMute(message))
            muteMap.put(message.getChannel().getId(), muteMap.get(message.getChannel().getId()) + message.getMentionedUsers().get(0).getAsMention());
        write();
    }

    public void unMute(Message message) {
        if (isMuted(message) && canMute(message)) {
            muteMap.put(message.getChannel().getId(), muteMap.get(message.getChannel().getId()).replace(message.getMentionedUsers().get(0).getAsMention(), ""));
        }
        write();
    }
}