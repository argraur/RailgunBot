package me.argraur.railgun.commands;

import java.awt.Color;

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import static java.lang.Math.pow;

public class CalcCommand implements RailgunOrder {
    private String calcCommand = RailgunBot.COMMAND_PREFIX + "calc";
    private MessageChannel messageChannel;

    public CalcCommand(MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }

    @Override
    public String getCommand() {
        return calcCommand;
    }

    @Override
    public void call(String args) {
        String[] arr = args.replace(calcCommand + " ", "").toString().split(" ");
        float tmp = Integer.parseInt(arr[0]); float result = 0;
        for (int i = 1; i < arr.length; i++) {
            try {
                tmp = Integer.parseInt(arr[i]);
            } catch (NumberFormatException nfe) {
                switch (arr[i]) {
                    case "/":
                        result = tmp / Float.parseFloat(arr[i + 1]);
                        break;
                    case "*":
                        result = tmp * Float.parseFloat(arr[i + 1]);
                        break;
                    case "-":
                        result = tmp - Float.parseFloat(arr[i + 1]);
                        break;
                    case "+":
                        result = tmp + Float.parseFloat(arr[i + 1]);
                        break;
                }
                tmp = result;
                i++;
            }
        }
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.PINK);
        embedBuilder.setTitle(args.replace(calcCommand + " ", ""));
        embedBuilder.setDescription("**Result: " + String.valueOf(result) + "**");
        messageChannel.sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getOutput(String args) {
        return null;
    }

    @Override
    public StringBuilder getHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append(calcCommand).append(" - Simple calculator. Example: >calc 2 * 2 * 2 (spaces are mandatory!)");
        return sb;
    }
}