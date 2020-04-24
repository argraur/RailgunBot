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
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

/**
 * API-type class.
 * Used to get status of Jenkins job in embed format
 * From existing RailgunBot.jenkinsServer connection.
 */
public class Status implements Runnable {
    private Message message;
    private JenkinsServer jenkins;
    private String jobName;

    /**
     * Default constructor
     * @param message Discord message
     * @param jenkins Jenkins Server connection
     */
    public Status(Message message, JenkinsServer jenkins) {
        this.message = message;
        this.jenkins = jenkins;
        this.jobName = message.getContentDisplay().split(" ")[2];
    }

    @Override
    public void run() {
        try {
            BuildWithDetails build = jenkins.getJob(jobName).getLastBuild().details();
            BuildResult result = build.getResult();
            final EmbedBuilder eb = new EmbedBuilder();
            String status = "";
            if (result == BuildResult.FAILURE) {
                eb.setColor(Color.RED);
                status = "**Failed**";
            } else if (result == BuildResult.SUCCESS) {
                status = "**Successful**";
                eb.setColor(Color.GREEN);
            } else if (result == BuildResult.ABORTED) {
                status = "**Aborted**";
                eb.setColor(Color.GRAY);
            } else if (result == BuildResult.BUILDING || result == null) {
                status = "**In Progress**";
                eb.setColor(Color.YELLOW);
            }
            long duration = System.currentTimeMillis() - build.getTimestamp();
            String durationStr = String.format("%d minutes", duration / 1000 / 60);
            eb.setDescription("Status: " + status);
            eb.setTitle("Build #" +  build.getNumber() + " of job " + message.getContentDisplay().split(" ")[2]);
            eb.addField("Timestamp", "`" + new SimpleDateFormat("dd MMM yyyy HH:mm Z").format(new Date(build.getTimestamp())) + "`", false);
            eb.addField("Started by", "`" + build.getCauses().get(0).getUserName() + "`", false);
            eb.addField("Duration", "`" + durationStr + "`", false);
            eb.addField("URL", build.getUrl(), false);
            eb.addField("Console", build.getUrl() + "console", false);
            message.getChannel().sendMessage(eb.build()).queue();
        } catch (IOException e) {
            return;
        }
    }
}