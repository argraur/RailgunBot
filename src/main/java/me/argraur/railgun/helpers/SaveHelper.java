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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveHelper {
    /**
     * Writes an Object to a file
     * @param o Object to be written to file
     * @param file Target file for object
     * @throws IOException If file stream fails
     */
    public static void writeObject(Object o, String file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(o);
        System.out.println("[SaveHelper]: Wrote object to file " + file);
        objectOutputStream.close();
    }

    /**
     * Reads an object from a file
     * @param file File path
     * @return Read Object from file
     * @throws IOException If file stream failed
     * @throws ClassNotFoundException If read data is not an Object
     */
    public static Object readObject(String file) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object obj = objectInputStream.readObject();
        objectInputStream.close();
        System.out.println("[SaveHelper]: Read object from file " + file);
        return obj;
    }
}