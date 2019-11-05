/*
 * Copyright (c) 2019. Sergey Dorokhin
 */

package com.java.sqlcmd.controller;

import com.java.sqlcmd.controller.commands.Command;
import com.java.sqlcmd.controller.commands.Connect;
import com.java.sqlcmd.controller.commands.Exit;
import com.java.sqlcmd.controller.commands.Help;
import com.java.sqlcmd.view.View;
import com.java.sqlcmd.controller.commands.*;
import com.java.sqlcmd.view.Console;

import java.util.LinkedList;
import java.util.List;

public class MainController {
    private static List<Command> commands = new LinkedList<Command>();
    private View console = new Console();

    public static List<Command> getCommands() {
        return commands;
    }

    public void run() {
        this.console = console;

        // add commands
        commands.add(new Help());
        commands.add(new Connect());
        commands.add(new Exit());

        // start app\
        menu();
    }

    private void menu() {
        while (true) {
            console.write("{b}{cyan} -> ");
            operation_chooser(console.read());
        }
    }

    public void operation_chooser(String operation) {
        String[] data = operation.split(" ");
        String name = data[0];
        boolean operation_created = false;

        for (Command command : commands) {
            if (name.equals(command.operation_name())) {
                console.write(command.operation(data));
                operation_created = true;
                break;
            }
        }

        if(!operation_created) {
            console.write(" {b}{red}Введена несуществующая команда. {yellow}/help{red} для помощи.{next}");
        }
    }
}
