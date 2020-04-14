package me.argraur.railgun.commands;

import static me.argraur.railgun.RailgunBot.sauceNAOApi;

import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class SauceCommand implements RailgunOrder {
    private String sauceCommand = "sauce";

    @Override
    public String getCommand() {
        return sauceCommand;
    }

    @Override
    public StringBuilder getHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append(sauceCommand).append(" [attach image] - Searches for art sauce");
        return sb;
    }

    @Override
    public void call(Message message) {
        MessageEmbed result = sauceNAOApi.toEmbed(sauceNAOApi.search(message));
        message.getChannel().sendMessage(result).queue();
    }
}