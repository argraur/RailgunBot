package me.argraur.railgun.commands.admin;

import java.awt.Color;

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class UnMuteCommand implements RailgunOrder {
    private String unMuteCommand = "unmute";
    private String usage = unMuteCommand + " <@user>";
    private String description = "Unmutes given user";

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
        return unMuteCommand;
    }
    
    @Override
    public void call(Message message) {
        RailgunBot.muteHandler.unMute(message);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.PINK);
        embedBuilder.setTitle("You can speak again!");
        embedBuilder.setDescription(message.getAuthor().getAsMention() + " unmuted " + message.getMentionedUsers().get(0).getAsMention() + "!");
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}