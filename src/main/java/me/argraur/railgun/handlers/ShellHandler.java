/*
 * Copyright (C) 2020 Arseniy Graur
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.argraur.railgun.handlers;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

import me.argraur.railgun.helpers.HelperManager;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class ShellHandler implements Runnable {
    private Message message;
    private String command;

    /**
     * Default constructor
     * @param message Discord message
     * @param command Command name
     */
    public ShellHandler(Message message, String command) {
        this.message = message;
        this.command = command;
    }

    /**
     * Creates embed based on input, error streams
     * and time elapsed.
     * @param shellCommand Shell command that was executed
     * @param output Input stream result
     * @param errOutput Error stream result
     * @param time Time elapsed
     * @return Ready embed with shell command result
     */
    public MessageEmbed toEmbed(String shellCommand, String output, String errOutput, long time) {
        if (output.equals("")) output = "Empty";
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(shellCommand);
        eb.addField("Input Stream", "```fix\n" + output + "\n```", false);
        eb.addField("Error Stream", "```fix\n" + errOutput + "\n```", false);
        eb.addField("Time Elapsed", "```fix\n" + (System.currentTimeMillis() - time) + "ms\n```", false);
        eb.setColor(Color.PINK);
        return eb.build();
    }

    /**
     * Creates new Thread and executes the given command
     * Then sends result as an embed generated from toEmbed(...) method.
     */
    @Override
    public void run() {
        String shellCommand = message.getContentDisplay().replace(HelperManager.prefix.getPrefixForGuild(message) + command + " ", "");
        ProcessBuilder pb = new ProcessBuilder();
        if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
            pb.command("cmd.exe", "/c", shellCommand);
        } else {
            pb.command("bash", "-c", shellCommand);
        }
        try {
            long time = System.currentTimeMillis();
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
            if (errOutput.toString().equals(""))
                errOutput.append("Empty");
            if (output.toString().length() < 1024) {
                message.getChannel().sendMessage(toEmbed(shellCommand, output.toString(), errOutput.toString(), time)).queue();
            } else {
                output.append("\nTime Elapsed: " + (System.currentTimeMillis() - time) + "ms\n");
                InputStream is = IOUtils.toInputStream(output.toString(), "UTF-8");
                message.getChannel().sendFile(is, "output.txt").queue();
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