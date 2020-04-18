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

package me.argraur.railgun.commands.admin;

import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

public class DeleteCommand implements RailgunOrder {
    private String deleteCommand = "del";
    private String usage = deleteCommand + " <message link>";
    private String description = "Delete given message";
    
    @Override
    public void call(Message message) {
        String id = message.getContentRaw().split(" ")[1].split("/")[6];
        if (message.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            System.out.println(id);
            message.getChannel().deleteMessageById(id).queue();
            message.delete().queue();
        }
    }

    @Override
    public String getCommand() {
        return deleteCommand;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getDescription() {
        return description;
    }
}