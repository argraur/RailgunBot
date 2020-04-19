package me.argraur.railgun.commands.master;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.entities.Message;

public class ShellCommand implements RailgunOrder {
    private String command = "shell";
    private String usage = " <command>";
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
    
    @Override
    public void call(Message message) {
        if (message.getAuthor().getId().equals(RailgunBot.configReader.getValue("goshujinsama"))) {
            String shellCommand = message.getContentDisplay().replace(RailgunBot.prefixHelper.getPrefixForGuild(message) + command + " ", "");
            ProcessBuilder pb = new ProcessBuilder();
            pb.command("cmd.exe", "/c", shellCommand);
            try {
                Process p = pb.start();
                StringBuilder output = new StringBuilder();
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream())
                );
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }
                int exitVal = p.waitFor();
                if (exitVal == 0) {
                    System.out.println("Success!");
                    message.getChannel().sendMessage("```" + output + "```").queue();
                } else {
                    System.out.println("Failed!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}