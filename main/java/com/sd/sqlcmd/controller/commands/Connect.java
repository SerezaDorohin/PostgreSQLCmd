package com.sd.sqlcmd.controller.commands;

import com.sd.sqlcmd.model.DatabaseController;
import com.sd.sqlcmd.view.View;

public class Connect implements Command {
    private static String USER = "postgres";
    private static String PASS = "root";

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
