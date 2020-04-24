package me.argraur.railgun.commands.utils;

import java.awt.Color;

import me.argraur.railgun.interfaces.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class Id implements Command {
    private final String command = "id";
    private final String usage = command;
    private final String description = "Gets sender's ID";

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
    public void call(Message message) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.PINK);
        embedBuilder.setDescription(message.getAuthor().getAsMention());
        embedBuilder.addField("Your ID", "`" + message.getAuthor().getId() + "`", false);
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}