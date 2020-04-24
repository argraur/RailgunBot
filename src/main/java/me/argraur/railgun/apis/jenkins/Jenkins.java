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

import java.net.URI;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;

import me.argraur.railgun.RailgunBot;

public class Jenkins {
    private JenkinsHttpClient httpClient;
    private JenkinsServer jenkinsServer;
    private boolean jenkinsUp = false;

    /**
     * Default constructor.
     * Connects to Jenkins server using
     * Given credentials in config.properties
     */
    public Jenkins() {
        URI jenkinsUri = null;
        try {
            jenkinsUri = new URI(RailgunBot.configHelper.getValue("jenkins.url"));
        } catch (Exception e) {
            System.out.println("[Jenkins] Failed to parse URL.");
            return;
        }
        httpClient = new JenkinsHttpClient(jenkinsUri, RailgunBot.configHelper.getValue("jenkins.user"), RailgunBot.configHelper.getValue("jenkins.pass"));
        jenkinsServer = new JenkinsServer(httpClient);
        jenkinsUp = true;
    }

    /**
     * Whether connection to Jenkins is established or not
     * @return boolean
     */
    public boolean isJenkinsUp() {
        return jenkinsUp;
    }

    /**
     * Method to get private jenkinsServer var
     * @return JenkinsServer
     */
    public JenkinsServer getServer() {
        return jenkinsServer;
    }
}