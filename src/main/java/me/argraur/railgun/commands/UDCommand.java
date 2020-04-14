package me.argraur.railgun.commands;

import static me.argraur.railgun.RailgunBot.urbanDictionaryApi;

import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.entities.Message;

public class UDCommand implements RailgunOrder {
    private String udCommand = "ud";

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
    public StringBuilder getHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append(udCommand).append(" <word or expression> - Search for given word or expression on UrbanDictionary");
        return sb;
    }
}