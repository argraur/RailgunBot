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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private final Properties properties;

    /**
     *
     * @param key Key requested
     * @return Returns property for corresponding key in config.properties
     */
    public String getValue(String key) {
        return properties.getProperty(key);
    }

    /**
     *
     * @throws IOException If inputStream is dead
     */
    public ConfigReader() throws IOException {
        properties = new Properties();
        String FILENAME = "config.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILENAME);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException(FILENAME + " not found");
        }
    }
}
