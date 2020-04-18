package me.argraur.railgun.commands.utils;

import static me.argraur.railgun.RailgunBot.urbanDictionaryApi;

import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.entities.Message;

public class UDCommand implements RailgunOrder {
    private String udCommand = "ud";
    private String usage = udCommand + " <query>";
    private String description = "Search <query> on Urban Dictionary";
    
    @Override
    public String getCommand() {
        return udCommand;
    }

    @Override
    public void call(Message message) {
        String msg = message.getContentRaw();
        msg = msg.substring((udCommand + " ").length() + 1);
        message.getChannel().sendMessage(urbanDictionaryApi.toEmbed(urbanDictionaryApi.search(msg))).queue();
    }
    
    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getDescription() {
        return description;
    }
}