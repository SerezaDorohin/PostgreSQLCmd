/*
 * Copyright (c) 2019. Sergey Dorokhin
 */

package com.java.sqlcmd.model;

import com.java.sqlcmd.view.Console;

import java.sql.Connection;

public class DatabaseController {
    private Console console = new Console();

    private Connection connection;
    private DatabaseManager db_manager;

    private String DATABASE_NAME;

    public void connect() {
        console.write("{yellow}{split}{next}");

        DATABASE_NAME = getInfo("название БД");

        db_manager = new JDBCDatabaseManager(DATABASE_NAME);
        connection = db_manager.connect();

        if (connection == null) {
            console.write(" {b}{red}Ошибка подключения.{next}");
        } else {
            console.write(" {b}{green}Подключение к [{n}{yellow}" + DATABASE_NAME + "{b}{green}] успешно.{next}");
            database_menu();
        }
    }

    private void create_table() {
        console.write("{b}{black} >{split}{next}");
        String table_name = getInfo("название таблицы");
        String schema_name = getInfo("название схемы");
        String[] columns = getInfo("название/тип/параметры колонок [{n}{cyan}col1/TYPE/PARAM,col2/TYPE.....{b}{black}]").split(",");
        String[] primary_keys = getInfo("поля PRIMARY KEY [{n}{cyan}col1,col2,col3,.....{b}{black}]").split(",");

        if (test_isNull(table_name, columns, primary_keys)) return;

        try {
            test_rightSyntax(columns);
        } catch (Error e) {
            console.write(" {b}{red}В описании колонок была допущена ошибка. Примерное местоположение: {n}{red}[" + e.getMessage() + "]{next}");
            return;
        }

        if (primary_keys.length != 1) {
            try {
                test_coincidences(columns, primary_keys);
            } catch (Error e) {
                console.write(" {b}{red}В поле PRIMARY KEY указаны несущетсвующие колонки.{next}");
                console.write("{b}{black} >{split}{next}");
                return;
            }
        }

        db_manager.create_table(table_name, schema_name, columns, primary_keys);

        console.write("{b}{black} >{split}{next}");
    }

    private void test_rightSyntax(String[] columns) {
        for (String column : columns) {
            String[] data = column.split("/");
            if (!(data.length >= 2 && data.length <= 3)) {
                throw new Error(column);
            }
        }
    }

    private boolean test_isNull(String table_name, String[] columns, String[] primary_keys) {
        if (table_name.equals("") || columns.length == 0 || primary_keys.length == 0) {
            console.write("{b}{red}Ошибка: не все поля заполнены.{next}");
            console.write("{b}{black} >{split}{next}");
            return true;
        } else if (table_name.contains(" ")) {
            console.write("{b}{red}В названии БД присутствуют пробелы.{next}");
            console.write("{b}{black} >{split}{next}");
            return true;
        }
        return false;
    }

    private void test_coincidences(String[] columns, String[] primary_keys) {
        int coincidences_count = 0;
        for (String primaryKey : primary_keys) {
            for (String column : columns) {
                String column_name = column.split("/")[0];

                if (column_name.equals(primaryKey)) {
                    coincidences_count += 1;
                }
            }
        }
        if (coincidences_count != primary_keys.length) {
            throw new Error();
        }
    }

    private String getInfo(String field_name) {
        while (true) {
            console.write("{n}{black} Введите {b}{black}" + field_name + "{n}: ");
            String input = console.read();
            if (input.equals(" ")) {
                console.write("{b}{red} Зполните данное поле!{next}");
            } else {
                return input;
            }
        }
    }

    private void database_menu() {
        while (true) {
            console.write(" {b}{blue}" + DATABASE_NAME + "{cyan} -> ");

            String input = console.read();
            if (input.equals("/exit")) {
                console.write(" {b}{green}Отключение от [{n}{yellow}" + DATABASE_NAME + "{b}{green}] прошло успешно.{next}");
                connection = null;
                break;
            }

            operation_chooser(input.split(" "));
        }
    }

    private void operation_chooser(String[] data) {
        String operation = data[0];

        switch (operation) {
            case "/create":
                create_table();
                break;
            case "/createSchema":
                create_schema();
                break;
            default:
                console.write(" {b}{red}Введена несуществующая команда. {yellow}/help{red} для помощи.{next}");
        }
    }

    private void create_schema() {
        console.write("{b}{black} >{split}{next}");
        while (true) {
            String schema_name = getInfo("название схемы");
            if (schema_name.equals("")) {
                console.write("{b}{green}Необходимо заполнить данное поле!");
            } else {
                db_manager.create_schema(schema_name);
                break;
            }
        }
        console.write("{b}{black} >{split}{next}");
    }
}
