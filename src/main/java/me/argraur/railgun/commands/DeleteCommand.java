package me.argraur.railgun.commands;

import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

public class DeleteCommand implements RailgunOrder {
    private String deleteCommand = "del";
    private String usage = deleteCommand + " <message link>";
    private String description = "Delete given message";
    
    @Override
    public void call(Message message) {
        String id = message.getContentRaw().split(" ")[1].split("/")[6];
        if (message.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            System.out.println(id);
            message.getChannel().deleteMessageById(id).queue();
            message.delete().queue();
        }
    }

    @Override
    public String getCommand() {
        return deleteCommand;
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