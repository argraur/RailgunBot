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

package me.argraur.railgun.helpers;

import java.io.IOException;

import me.argraur.railgun.RailgunBot;

public class HelperManager {
    public static ColorHelper color;
    public static GiphyHelper giphy;
    public static PrefixHelper prefix;

    /**
     * HelperManager initializer method
     * @throws IOException Thrown by ConfigHelper.getValue(...)
     */
    public static void init() throws IOException {
        System.out.println("[HelperManager] Initializing...");
        color = new ColorHelper();
        giphy = new GiphyHelper(RailgunBot.configHelper.getValue("giphy"));
        prefix = new PrefixHelper();
        System.out.println("[HelperManager] Ready!");
    }
}