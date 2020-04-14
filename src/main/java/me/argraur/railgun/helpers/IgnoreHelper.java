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

import me.argraur.railgun.RailgunBot;
import net.dv8tion.jda.api.entities.Message;

public class IgnoreHelper {
    
    public IgnoreHelper() {
        System.out.println("IgnoreHelper is ready!");
    }

    /**
     * 
     * @param id
     */
    public void addIgnored(final String id, final Message msg) {
        RailgunBot.channels.put(msg.getChannel().getId(), RailgunBot.channels.get(msg.getChannel().getId()) + id + ",");
    }

    public void delIgnored(final String id, final Message msg) {
        RailgunBot.channels.put(msg.getChannel().getId(), RailgunBot.channels.get(msg.getChannel().getId()).replaceAll(id + ",", ""));
    }

    /**
     * 
     * @param msg
     * @return
     */
    public boolean checkIfIgnored(final Message msg) {
        try {
            final String[] ignoredIds = RailgunBot.channels.get(msg.getChannel().getId()).split(",");
            for (int i = 0; i < ignoredIds.length; i++) {
                if (ignoredIds[i].equals(msg.getAuthor().getId())) {
                    return true;
                }
            }
        } catch (NullPointerException e) {
            RailgunBot.channels.put(msg.getChannel().getId(), "");
        }
        return false;
    }
}