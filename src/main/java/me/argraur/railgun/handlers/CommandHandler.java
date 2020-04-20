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

import static me.argraur.railgun.RailgunBot.prefixHelper;

import me.argraur.railgun.commands.admin.*;
import me.argraur.railgun.commands.anime.*;
import me.argraur.railgun.commands.fun.*;
import me.argraur.railgun.commands.master.ShellCommand;
import me.argraur.railgun.commands.pseudo.HelpCommand;
import me.argraur.railgun.commands.utils.*;

import me.argraur.railgun.interfaces.RailgunOrder;

import net.dv8tion.jda.api.entities.Message;

import java.util.LinkedHashMap;

public class CommandHandler {
    private LinkedHashMap<String, RailgunOrder> commandsMap = new LinkedHashMap<>();

    /**
     * Registers the command in handler.
     * @param command RailgunOrder command object to register.
     */
    void registerCommand(RailgunOrder command) {
        commandsMap.put(command.getCommand(), command);
        System.out.println("[CommandHandler] Loaded command " + command.getCommand());
    }

    /**
     *
     * @param command Called when command received.
     */
    public void onCommandReceived(String command, Message message) {
        String temp = command.split(" ")[0].replace(prefixHelper.getPrefixForGuild(message), "");
        System.out.println("[CommandHandler] Calling command " + temp);
        commandsMap.get(temp).call(message);
    }

    /**
     * 
     * @param message
     */
    public void onHelpCommandReceived(Message message) {
        System.out.println("[CommandHandler] Calling temporary help command");
        HelpCommand.call(commandsMap, prefixHelper.getPrefixForGuild(message), message);
    }

    /**
     * Default constructor.
     * @param messageChannel Registers commands.
     */
    public CommandHandler() {
        registerCommand(new BallCommand());
        registerCommand(new BanCommand());
        registerCommand(new CalcCommand());
        registerCommand(new CoinFlipCommand());
        registerCommand(new ColorCommand());
        registerCommand(new DeleteCommand());
        registerCommand(new GifCommand());
        registerCommand(new HugCommand());
        registerCommand(new IgnoreCommand());
        registerCommand(new JuggleCommand());
        registerCommand(new KickCommand());
        registerCommand(new KitsuCommand());
        registerCommand(new MockCommand());
        registerCommand(new MuteCommand());
        registerCommand(new OCommand());
        registerCommand(new PardonCommand());
        registerCommand(new PingCommand());
        registerCommand(new PrefixCommand());
        registerCommand(new SauceCommand());
        registerCommand(new ShellCommand());
        registerCommand(new SlapCommand());
        registerCommand(new UDCommand());
        registerCommand(new UnMuteCommand());
        registerCommand(new WaitCommand());
        System.out.println("[CommandHandler] Ready");
    }

    /**
     *
     * @param command Command string received from message
     * @return Whether command exist or not
     */
    public boolean checkIfCommandExists(Message msg) {
        return commandsMap.containsKey(msg.getContentDisplay().replace(prefixHelper.getPrefixForGuild(msg), ""));
    }
}