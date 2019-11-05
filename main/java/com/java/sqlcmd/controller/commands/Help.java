/*
 * Copyright (c) 2019. Sergey Dorokhin
 */

package com.java.sqlcmd.controller.commands;

import com.java.sqlcmd.controller.MainController;

public class Help implements Command {
    @Override
    public String description() {
        return "\033[1;33m" + "\u001B[35m" + "/help" + "\033[0;37m" + " - вывод всех доступных команд";
    }

    @Override
    public String operation_name() {
        return "/help";
    }

    @Override
    public String operation(String[] data) {
        StringBuilder result = new StringBuilder();
        result.append("{yellow}{split}{b}{cyan}{next}  Список всех команд: {n}");
        int pos = 0;

        for (Command command : MainController.getCommands()) {
            result.append("{next}");
            result.append("    ");
            result.append("{white}{b}" + (pos + 1) + ". " + "{n}");
            result.append("{n}{white}" + command.description());

            pos++;
        }

        result.append("{next}{yellow}{split}{next}");

        return result.toString();
    }
}