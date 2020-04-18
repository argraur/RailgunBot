package me.argraur.railgun.commands.admin;

import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import static me.argraur.railgun.RailgunBot.prefixHelper;

import me.argraur.railgun.RailgunBot;

public class PrefixCommand implements RailgunOrder {
    private String command = "prefix";
    private String usage = command + " <new prefix>";
    private String description = "Sets new prefix for the guild";

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
        EmbedBuilder embedBuilder = new EmbedBuilder();
        try {
            prefixHelper.setPrefixForGuild(message, message.getContentDisplay().split(" ")[1]);
            embedBuilder.setTitle("New command prefix set!");
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        embedBuilder.setDescription("Current prefix: `" + RailgunBot.prefixHelper.getPrefixForGuild(message) + "`");
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}