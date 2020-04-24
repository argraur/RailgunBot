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

package me.argraur.railgun.apis.jenkins;

import java.awt.Color;
import java.io.IOException;
import java.util.Scanner;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.JenkinsTriggerHelper;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

/**
 * API-type class.
 * Used to build given Jenkins job.
 * With result sent in embed format.
 * URL, name, size, md5sum is specially
 * For PixelROM Project CI
 */
public class Build implements Runnable {
    private final Message message;
    private final String jobName;
    private final JenkinsTriggerHelper jenkinsTriggerHelper;
    private JobWithDetails job;

    /**
     * Default constructor
     * @param message Discord message
     * @param jenkins Jenkins Server connection
     */
    public Build(final Message message, final JenkinsServer jenkins) {
        this.message = message;
        this.jobName = message.getContentDisplay().split(" ")[2];
        this.jenkinsTriggerHelper = new JenkinsTriggerHelper(jenkins);
        try {
            this.job = jenkins.getJob(jobName);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void run() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Building " + jobName + " #" + job.getNextBuildNumber());
        eb.addField("URL", job.getUrl() + job.getNextBuildNumber(), false);
        eb.addField("Console", job.getUrl() + job.getNextBuildNumber() + "/console", false);
        eb.setDescription(message.getMember().getAsMention() + ", the links will be available in a couple of seconds!");
        eb.setColor(Color.PINK);
        message.getChannel().sendMessage(eb.build()).queue((response) -> {
            try {
                message.delete().queue();
                final BuildWithDetails build = jenkinsTriggerHelper.triggerJobAndWaitUntilFinished(jobName);
                final EmbedBuilder embedBuilder = new EmbedBuilder();
                String status = "";

                if (build.getResult() == BuildResult.FAILURE) {
                    embedBuilder.setColor(Color.RED);
                    status = "Failed";
                } else if (build.getResult() == BuildResult.SUCCESS) {
                    status = "Successful";
                    embedBuilder.setColor(Color.GREEN);
                } else if (build.getResult() == BuildResult.ABORTED) {
                    status = "Aborted";
                    embedBuilder.setColor(Color.GRAY);
                }
                long duration = System.currentTimeMillis() - build.getTimestamp();
                String durationStr = String.format("%d minutes", duration / 1000 / 60);
                embedBuilder.addField("Duration", "`" + durationStr + "`", false);
                Scanner sc = new Scanner(build.getConsoleOutputText());
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (line.startsWith("__RAILGUN__")) {
                        String workLine = line.replace("__RAILGUN__", "");
                        if (workLine.startsWith("url=")) {
                            embedBuilder.addField("URL", workLine.replace("url=", ""), false);
                        } else if (workLine.startsWith("name=")) {
                            embedBuilder.addField("Filename", "`" + workLine.replace("name=", "") + "`", false);
                        } else if (workLine.startsWith("size=")) {
                            embedBuilder.addField("Size", "`" + workLine.replace("size=", "") + " GB`", false);
                        } else if (workLine.startsWith("md5sum=")) {
                            embedBuilder.addField("MD5SUM", "`" + workLine.replace("md5sum=", "") + "`", false);
                        } else {
                            embedBuilder.addField("Output message", "`" + workLine + "`", false);
                        }
                    }
                }
                embedBuilder.setTitle(build.getFullDisplayName() + " " + status, build.getUrl());
                response.editMessage(embedBuilder.build()).queue();
            } catch (final Exception e) {
                e.printStackTrace();
                message.delete().queue();
                System.out.println("[Build] Failed to run job " + jobName);
            }
        });
    }
}