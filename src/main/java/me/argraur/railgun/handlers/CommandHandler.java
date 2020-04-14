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

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.commands.*;
import me.argraur.railgun.interfaces.RailgunOrder;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.HashMap;

public class CommandHandler {
    private final int COMMANDS_COUNT = 13;
    private HashMap<String, Integer> commandsMap = new HashMap<>();
    private RailgunOrder[] commands = new RailgunOrder[COMMANDS_COUNT];
    private int count = 0;

    /**
     * Registers the command in handler.
     * @param command RailgunOrder command object to register.
     */
    void registerCommand(RailgunOrder command) {
        commands[count] = command;
        commandsMap.put(command.getCommand(), count);
        count++;
    }

    /**
     *
     * @param command Called when command received.
     */
    public void onCommandReceived(String command) {
        commands[commandsMap.get(command.split(" ")[0])].call(command);
    }

    /**
     * Default constructor.
     * @param messageChannel Registers commands.
     */
    public CommandHandler(MessageChannel messageChannel, Message msg) {
        registerCommand(new PingCommand(messageChannel));
        registerCommand(new OCommand(messageChannel));
        registerCommand(new KitsuCommand(messageChannel));
        registerCommand(new SlapCommand(messageChannel, msg));
        registerCommand(new HugCommand(messageChannel, msg));
        registerCommand(new GifCommand(messageChannel, msg));
        registerCommand(new IgnoreCommand(RailgunBot.getIgnoreHelper(), msg));
        registerCommand(new PardonCommand(RailgunBot.getIgnoreHelper(), msg));
        registerCommand(new MockCommand(messageChannel));
        registerCommand(new CalcCommand(messageChannel));
        registerCommand(new KickCommand(messageChannel, msg));
        registerCommand(new BanCommand(messageChannel,  msg));
        registerCommand(new HelpCommand(messageChannel, commands));
    }

    /**
     *
     * @param command Command string received from message
     * @return Whether command exist or not
     */
    public boolean checkIfCommandExists(String command) {
        return commandsMap.containsKey(command);
    }
}
