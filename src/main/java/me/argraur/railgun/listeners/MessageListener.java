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

package me.argraur.railgun.listeners;

import static me.argraur.railgun.RailgunBot.commandHandler;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

import me.argraur.railgun.helpers.HelperManager;

public class MessageListener extends ListenerAdapter {
    /**
     * Called whenever the bot gets a message.
     * @param event Event of received message.
     */
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        Message msg = event.getMessage();
        System.out.println("[MessageListener] Guild ID: " + msg.getGuild().getId());
        String msgStr = msg.getContentRaw();
        if (!msgStr.startsWith(HelperManager.prefix.getPrefixForGuild(msg)))
            return;
        System.out.println("[MessageListener] Received message " + msgStr);
        if (msgStr.split(" ")[0].contains("help")) {
            commandHandler.onHelpCommandReceived(msg);
            return;
        }
        commandHandler.onCommandReceived(msgStr, msg);
    }
}
