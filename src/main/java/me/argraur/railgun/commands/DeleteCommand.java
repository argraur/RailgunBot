package me.argraur.railgun.commands;

import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

public class DeleteCommand implements RailgunOrder {
    private String deleteCommand = "del";

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
    public StringBuilder getHelp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(deleteCommand).append(" [message link] - Delete given message");
        return stringBuilder;
    }
}