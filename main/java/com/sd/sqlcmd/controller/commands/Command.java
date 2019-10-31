package com.sd.sqlcmd.controller.commands;

import com.sd.sqlcmd.view.View;

public interface Command {
    public String description();

    public String operation_name();

    public String operation(String[] data);
}
