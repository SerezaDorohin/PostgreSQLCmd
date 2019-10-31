package com.sd.sqlcmd.controller.commands;

public class Exit implements Command {

    @Override
    public String description() {
        return "\033[1;33m" + "\u001B[35m" + "/exit" + "\033[0;37m" + " - выход из программы / отключение от базы данных";
    }

    @Override
    public String operation_name() {
        return "/exit";
    }

    @Override
    public String operation(String[] data) {
        System.exit(0);
        return "";
    }
}
