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

package me.argraur.railgun.helpers;

import static me.argraur.railgun.RailgunBot.COMMAND_PREFIX;
import static me.argraur.railgun.helpers.SaveHelper.readObject;
import static me.argraur.railgun.helpers.SaveHelper.writeObject;

import java.io.IOException;
import java.util.HashMap;

import me.argraur.railgun.RailgunBot;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;

public class PrefixHelper {
    private HashMap<String, String> guildPrefixes;
    private static final String file = "data/prefix.hashmap";
    
    /**
     * Default constructor
     * Reads prefixes from existing file
     * Or creates new if doesn't exist
     */
    public PrefixHelper() {
        try {
            Object obj = readObject(file);
            if (obj instanceof HashMap)
                guildPrefixes = (HashMap<String, String>) obj;
        } catch (IOException | ClassNotFoundException e) {
            guildPrefixes = new HashMap<>();
            write();
        }
        System.out.println("[MuteHandler] Ready!");
    }

    /**
     * Writes guildPrefixes to file
     */
    public void write() {
        try {
            writeObject(guildPrefixes, file);
        } catch (IOException e) {
            System.out.println("[PrefixHelper] [FATAL] Write failed!");
            e.printStackTrace();
            RailgunBot.error(this);
        }
    }

    /**
     * Checks whether guild has custom prefix set or not
     * @param message
     * @return
     */
    public boolean isSet(Message message) {
        return guildPrefixes.get(message.getGuild().getId()) != null;
    }

    /**
     * Gets guild's custom prefix
     * @param message
     * @return Prefix if set, return default if not
     */
    public String getPrefixForGuild(Message message) {
        if (message.getChannelType() == ChannelType.PRIVATE)
            return COMMAND_PREFIX;
        if (isSet(message)) {
            return guildPrefixes.get(message.getGuild().getId());
        }
        return COMMAND_PREFIX;
    }

    /**
     * Sets custom prefix for a guild
     * @param message
     * @param prefix New prefix
     */
    public void setPrefixForGuild(Message message, String prefix) {
        guildPrefixes.put(message.getGuild().getId(), prefix);
        write();
    }
}