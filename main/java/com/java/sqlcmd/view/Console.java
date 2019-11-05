/*
 * Copyright (c) 2019. Sergey Dorokhin
 */

package com.java.sqlcmd.view;

import java.util.Scanner;

public class Console implements View {
    @Override
    public String read() {
        try {
            return new Scanner(System.in).nextLine();
        } catch (java.lang.NullPointerException e) {
            return "";
        }
    }

    @Override
    public void write(String msg) {
        String msg_edited = "\u001B[0m" + msg.
                replaceAll("\\{" + "b" + "\\}", "\033[1;33m").
                replaceAll("\\{" + "n" + "\\}", "\033[0;37m").
                replaceAll("\\{" + "red" + "\\}", "\u001B[31m").
                replaceAll("\\{" + "green" + "\\}", "\u001B[32m").
                replaceAll("\\{" + "yellow" + "\\}", "\u001B[33m").
                replaceAll("\\{" + "cyan" + "\\}", "\u001B[36m").
                replaceAll("\\{" + "black" + "\\}",  "\u001B[30m").
                replaceAll("\\{" + "white" + "\\}", "\u001B[37m").
                replaceAll("\\{" + "blue" + "\\}", "\u001B[34m").
                replaceAll("\\{" + "rs" + "\\}", "\u001B[0m").
                replaceAll("\\{" + "split" + "\\}", "---------------------------------------------------------------------------").
                replaceAll("\\{" + "next" + "\\}","\n")
                + "\u001B[0m";

        System.out.print(msg_edited);
    }
}
