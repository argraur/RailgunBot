package me.argraur.railgun.commands.fun;

import java.util.List;
import java.util.Random;

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class JuggleCommand implements RailgunOrder {
    public String command = "juggle";
    public String usage = command;
    public String description = "JUGGLE VOICE CHANNEL MEMBERS";

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
        if (message.getMember().hasPermission(Permission.ADMINISTRATOR) || message.getMember().getId().equals(RailgunBot.configReader.getValue("goshujinsama"))) {
            List<VoiceChannel> vcList = message.getGuild().getVoiceChannels();
            for (int i = 0; i < vcList.size(); i++) {
                for (VoiceChannel vc : vcList) {
                    for (Member member : vc.getMembers()) {
                        member.getGuild().moveVoiceMember(member, vcList.get(new Random().nextInt(vcList.size()))).queue();
                    }
                }
            }
        }
    }
}