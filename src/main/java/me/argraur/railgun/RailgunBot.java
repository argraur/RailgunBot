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

package me.argraur.railgun;

import me.argraur.railgun.helpers.ConfigReader;
import me.argraur.railgun.helpers.IgnoreHelper;
import me.argraur.railgun.helpers.GiphyHelper;

import me.argraur.railgun.listeners.MessageListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashMap;

public class RailgunBot {
    public JDA discordBot;
    public static ConfigReader configReader;
    public static String COMMAND_PREFIX;
    public static MessageListener messageListener;
    public static IgnoreHelper ignoreHelper;
    public static GiphyHelper giphyHelper;
    public static HashMap<String, String> channels = new HashMap<>();

    /**
     * Sets command prefix by reading from config.
     */
    private static void setCommandPrefix() {
        COMMAND_PREFIX = readConfig("command_prefix");
    }

    /**
     * Function for configuring the bot.
     * Sets activity and creates default message listener.
     * @return Returns JDABuilder with token and set activity.
     */
    private JDABuilder configureBot() {
        messageListener = new MessageListener();
        JDABuilder jb = new JDABuilder(readConfig("token"));
        jb.setActivity(Activity.playing(readConfig("activity")));
        jb.addEventListeners(messageListener);
        return jb;
    }

    /**
     * Default constructor for the Railgun bot.
     */
    private RailgunBot() {
        try {
            discordBot = configureBot().build();
        } catch (LoginException e) {
            System.exit(1);
        }
    }

    /**
     *
     * @param config Key to search for in config.
     * @return Returns value for given config from config.properties
     */
    public static String readConfig(String config) {
        return configReader.getValue(config);
    }

    /**
     * Init function.
     */
    public static void init() {
        try {
            configReader = new ConfigReader();
            ignoreHelper = new IgnoreHelper();
            giphyHelper = new GiphyHelper(readConfig("giphy"));
        } catch (IOException e) {
            error();
        }
        setCommandPrefix();
        new RailgunBot();
    }

    /**
     * Main function
     * @param args Standard function argument.
     */
    public static void main(String[] args) {
        init();
    }

    /**
     * Error function.
     */
    public static void error() {
        System.exit(1);
    }

    public static IgnoreHelper getIgnoreHelper() {
        return ignoreHelper;
    }

    public static HashMap<String, String> getChannels() {
        return channels;
    }
}
