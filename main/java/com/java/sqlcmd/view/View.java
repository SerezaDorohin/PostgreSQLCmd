/*
 * Copyright (c) 2019. Sergey Dorokhin
 */

package com.java.sqlcmd.view;

public interface View {
    String read();

    public void write(String msg);
}
