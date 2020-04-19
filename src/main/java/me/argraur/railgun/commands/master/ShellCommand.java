package me.argraur.railgun.commands.master;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class ShellCommand implements RailgunOrder {
    private String command = "shell";
    private String usage = command + " <command>";
    private String description = "Execute shell command (Only for mine goshujin~!)";

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
    
    public MessageEmbed toEmbed(String shellCommand, String output, String errOutput) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(shellCommand);
        eb.addField("Input Stream", "```fix\n" + output + "\n```", false);
        eb.addField("Error Stream", "```fix\n" + errOutput + "\n```", false);
        eb.setColor(Color.PINK);
        return eb.build();
    }

    @Override
    public void call(Message message) {
        if (message.getAuthor().getId().equals(RailgunBot.configReader.getValue("goshujinsama"))) {
            String shellCommand = message.getContentDisplay().replace(RailgunBot.prefixHelper.getPrefixForGuild(message) + command + " ", "");
            ProcessBuilder pb = new ProcessBuilder();
            if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
                pb.command("cmd.exe", "/c", shellCommand);
            } else {
                pb.command("bash", "-c", shellCommand);
            }
            try {
                Process p = pb.start();
                StringBuilder output = new StringBuilder();
                StringBuilder errOutput = new StringBuilder();
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream())
                );
                BufferedReader errReader = new BufferedReader(
                    new InputStreamReader(p.getErrorStream())
                );
                String line, err;
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }
                while ((err = errReader.readLine()) != null) {
                    errOutput.append(err + "\n");
                }
                p.waitFor();
                if (output.toString().equals(""))
                    output.append("Empty");
                if (errOutput.toString().equals(""))
                    errOutput.append("Empty");
                if (output.toString().length() < 1024) {
                    message.getChannel().sendMessage(toEmbed(shellCommand, output.toString(), errOutput.toString())).queue();
                } else {
                    if (!output.toString().equals("Empty")) {
                        InputStream is = IOUtils.toInputStream(output.toString(), "UTF-8");
                        message.getChannel().sendFile(is, "output.txt").queue();
                    }
                    if (!errOutput.toString().equals("Empty")) {
                        InputStream es = IOUtils.toInputStream(errOutput.toString(), "UTF-8");
                        message.getChannel().sendFile(es, "err.txt").queue();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}