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

import me.argraur.railgun.commands.admin.*;
import me.argraur.railgun.commands.anime.*;
import me.argraur.railgun.commands.fun.*;
import me.argraur.railgun.commands.utils.*;

import me.argraur.railgun.commands.fun.Long;
import me.argraur.railgun.commands.jenkins.Jenkins;
import me.argraur.railgun.commands.master.Shell;
import me.argraur.railgun.commands.pseudo.Help;

import me.argraur.railgun.helpers.HelperManager;
import me.argraur.railgun.interfaces.Command;

import net.dv8tion.jda.api.entities.Message;

import java.util.LinkedHashMap;

import javax.annotation.Nonnull;

public class CommandHandler {
    private LinkedHashMap<String, Command> commandsMap = new LinkedHashMap<>();

    /**
     * Registers the command in handler.
     * @param command RailgunOrder command object to register.
     */
    void registerCommand(Command command) {
        commandsMap.put(command.getCommand(), command);
        System.out.println("[CommandHandler] Loaded command " + command.getCommand());
    }

    /**
     * Called when command received.
     * @param command Received command name
     * @param message Discord Message
     */
    public void onCommandReceived(String command, Message message) {
        String temp = command.split(" ")[0].replace(HelperManager.prefix.getPrefixForGuild(message), "");
        System.out.println("[CommandHandler] Calling command " + temp);
        Command toCall = getCommand(temp);
        if (me.argraur.railgun.level.Level.checkLevel(message, toCall))
            toCall.call(message);
    }

    /**
     * Called when help command received
     * @param message Discord Message
     */
    public void onHelpCommandReceived(Message message) {
        System.out.println("[CommandHandler] Calling temporary help command");
        Help.call(commandsMap, HelperManager.prefix.getPrefixForGuild(message), message);
    }

    /**
     * Default constructor.
     */
    public CommandHandler() {
        registerCommand(new Ball());
        registerCommand(new Ban());
        registerCommand(new Calc());
        registerCommand(new CoinFlip());
        registerCommand(new Color());
        registerCommand(new Delete());
        registerCommand(new Gif());
        registerCommand(new GuildId());
        registerCommand(new Hug());
        registerCommand(new Id());
        registerCommand(new Jenkins());
        registerCommand(new Juggle());
        registerCommand(new Kick());
        registerCommand(new Kitsu());
        registerCommand(new Mirror());
        registerCommand(new Mock());
        registerCommand(new Mute());
        registerCommand(new Level());
        registerCommand(new Long());
        registerCommand(new Ping());
        registerCommand(new Prefix());
        registerCommand(new Purge());
        registerCommand(new Sauce());
        registerCommand(new Shell());
        registerCommand(new Slap());
        registerCommand(new UrbanDict());
        registerCommand(new Unmute());
        registerCommand(new Wait());
        System.out.println("[CommandHandler] Ready");
    }

    /**
     *
     * @param message Discord message
     * @return Whether command exist or not
     */
    public boolean checkIfCommandExists(Message message) {
        return commandsMap.containsKey(message.getContentDisplay().replace(HelperManager.prefix.getPrefixForGuild(message), ""));
    }

    public Command getCommand(@Nonnull String command) {
        try {
            return commandsMap.get(command);
        } catch (Exception e) {
            System.out.println("[CommandHandler] Command not found!");
        }
        return null;
    }
}