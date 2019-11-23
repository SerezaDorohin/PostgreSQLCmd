/*
 * Copyright (c) 2019. Sergey Dorokhin
 */

package com.java.sqlcmd.controller.commands;

import com.java.sqlcmd.model.DatabaseController;

public class Connect implements Command {
    @Override
    public String description() {
        return "\033[1;33m" + "\u001B[35m" + "/connect" + "\033[0;37m" + " - подключение к базе данных";
    }

    @Override
    public String operation_name() {
        return "/connect";
    }

    @Override
    public String operation(String[] data) {
        new DatabaseController().connect();
        return "{yellow}{split}{next}";
    }


}
