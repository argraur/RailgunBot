package me.argraur.railgun.commands;

import static me.argraur.railgun.RailgunBot.sauceNAOApi;

import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class SauceCommand implements RailgunOrder {
    private String sauceCommand = "sauce";
    private String usage = sauceCommand + " <url to image> OR attach image";
    private String description = "Search for source of given anime art!";

    @Override
    public String getCommand() {
        return sauceCommand;
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
        MessageEmbed result = sauceNAOApi.toEmbed(sauceNAOApi.search(message));
        message.getChannel().sendMessage(result).queue();
    }
}