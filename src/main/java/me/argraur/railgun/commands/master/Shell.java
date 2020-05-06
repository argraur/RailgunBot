package me.argraur.railgun.commands.master;

import me.argraur.railgun.interfaces.Command;

import me.argraur.railgun.handlers.ShellHandler;
import me.argraur.railgun.level.Level;
import net.dv8tion.jda.api.entities.Message;

public class Shell implements Command {
    private final String command = "shell";
    private final String usage = command + " <command>";
    private final String description = "Execute shell command (Only for mine goshujin~!)";
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
    public void call(final Message message) {
        new Thread(new ShellHandler(message, command)).start();
    }
}