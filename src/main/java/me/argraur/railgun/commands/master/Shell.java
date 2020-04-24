package me.argraur.railgun.commands.master;

import me.argraur.railgun.interfaces.Command;

import me.argraur.railgun.handlers.ShellHandler;

import net.dv8tion.jda.api.entities.Message;

public class Shell implements Command {
    private final String command = "shell";
    private final String usage = command + " <command>";
    private final String description = "Execute shell command (Only for mine goshujin~!)";

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
    public void call(final Message message) {
        new Thread(new ShellHandler(message, command)).start();
    }
}