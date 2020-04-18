package me.argraur.railgun.commands.admin;

import java.awt.Color;

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class MuteCommand implements RailgunOrder {
    private String muteCommand = "mute";
    private String usage = muteCommand + " <@user>";
    private String description = "Mutes given user";

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getCommand() {
        return muteCommand;
    }
    
    @Override
    public void call(Message message) {
        RailgunBot.muteHandler.mute(message);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.PINK);
        embedBuilder.setTitle("SILENCE!");
        embedBuilder.setDescription(message.getAuthor().getAsMention() + " muted " + message.getMentionedUsers().get(0).getAsMention() + "!");
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}