package me.argraur.railgun.commands.master;

import me.argraur.railgun.handlers.ShellHandler;
import me.argraur.railgun.interfaces.RailgunOrder;

import net.dv8tion.jda.api.entities.Message;

public class ShellCommand implements RailgunOrder {
    private String command = "shell";
    private String usage = command + " <command>";
    private String description = "Execute shell command (Only for mine goshujin~!)";

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void call(Message message) {
        new Thread(new ShellHandler(message, command)).start();
    }
}