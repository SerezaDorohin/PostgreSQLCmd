/*
 * Copyright (c) 2019. Sergey Dorokhin
 */

package com.java.sqlcmd.controller.commands;

public interface Command {
    public String description();

    public String operation_name();

    public String operation(String[] data);
}
