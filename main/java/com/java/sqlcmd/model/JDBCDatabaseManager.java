/*
 * Copyright (c) 2019. Sergey Dorokhin
 */

package com.java.sqlcmd.model;

import com.java.sqlcmd.view.Console;

import java.sql.*;
import java.util.ArrayList;

public class JDBCDatabaseManager implements DatabaseManager {
    private Connection connection;
    private Statement statement;
    private Console console;

    private String DATABASE_NAME = "task_database";
    private static final String USER = "postgres";
    private static final String PASS = "root";

    public JDBCDatabaseManager(String DATABASE_NAME) {
        this.DATABASE_NAME = DATABASE_NAME;
    }

    @Override
    public Connection connect() {
        console = new Console();
        final String USER = "postgres";
        final String PASS = "root";
        String db_url = "jdbc:postgresql://127.0.0.1:5432/" + DATABASE_NAME;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            console.write(" {b}{red}Драйвер {b}{yellow}postgreSQL{b}{red} не установлен.{next}");
            System.exit(1);
        }

        connection = null;

        try {
            connection = DriverManager
                    .getConnection(db_url, USER, PASS);
        } catch (SQLException e) {
            console.write(" {b}{red}Указанная база данных {n}{yellow}[" + DATABASE_NAME + "]{b}{red} не существует или введён неверный логин/пароль.{next}");
        }

        return connection;
    }

//    private void send_inquiry(String inquiry) {
//        try {
//            statement = connection.createStatement();
//
//            // выполнить SQL запрос
//            statement.execute(inquiry);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            if (statement != null) {
//                try {
//                    statement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    @Override
    public void create_table(String table_name, String schema_name, String[] columns, String[] primary_key) {
        try {
            String inquiry = create_table_inquiry(table_name, schema_name, columns, primary_key);

            statement = connection.createStatement();

            statement.execute(inquiry);

            console.write(" {b}{green}Таблица [{b}{yellow}" + table_name + "{b}{green}] успешно создана.{next}");
        } catch (SQLException e) {
            console.write(" {b}{red}Ошибка при создании таблицы. Перезапустите приложение.{next}");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void create_schema(String schema_name) {
        try {
            String create_schema = "CREATE SCHEMA " + schema_name;

            if (schemaIsCreated(schema_name)) {
                console.write(" {n}{red}Схема с таким именем {b}{red}уже создана{n}{red}.{next}");
                return;
            }

            statement = connection.createStatement();
            statement.execute(create_schema);

            console.write(" {b}{green}Схема [{b}{yellow}" + schema_name + "{b}{green}] успешно создана.{next}");
        } catch (SQLException e) {
            console.write(" {b}{red}Ошибка при создании схемы.{next}");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean schemaIsCreated(String schema_name) throws SQLException {
        try {
            String get_schemas = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA";

            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(get_schemas);

            while (rs.next()) {
                if (rs.getString("schema_name").equals(schema_name)) {
                    return true;
                }
            }

            return false;
        } catch (java.lang.NullPointerException e) {
            return false;
        }
    }

    @Override
    public String tables(String schema_name) {
        try {
            if(!schemaIsCreated(schema_name)) {
                return ("  {b}{red}Указанная схема не создана.");
            }

            String get_tables = "select table_name " + "from information_schema.tables " + "where table_schema='" + schema_name + "'";

            statement = connection.createStatement();
            statement.execute(get_tables);

            ResultSet rs = statement.executeQuery(get_tables);
            StringBuilder strB = new StringBuilder();

            strB.append("  {b}{black}[ ");

            while (rs.next()) {
                strB.append("{b}{yellow}|");
                strB.append("{b}{green}" + rs.getString("table_name"));
                strB.append("{b}{yellow}| ");
            }

            strB.append("{b}{black}]");

            return strB.toString();
        } catch (SQLException e) {
           return ("  {b}{red}Ошибка при получении данных.");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private String create_table_inquiry(String table_name, String schema_name, String[] columns, String[] primary_keys) throws SQLException {
        if(!schemaIsCreated(schema_name)) {
            create_schema(schema_name);
        }

        StringBuilder inquiry = new StringBuilder();
        inquiry.append("CREATE TABLE " + schema_name + "." + table_name + " ( ");

        for (int i = 0; i < columns.length; i++) {
            String[] data = columns[i].split("/");
            for (int f = 0; f < data.length; f++) {
                inquiry.append(data[f] + " ");
            }
            if ((i + 1) != columns.length) {
                inquiry.append(",");
            }
        }

        if (primary_keys.length != 1) {
            inquiry.append(", PRIMARY KEY (");
            for (int i = 0; i < primary_keys.length; i++) {
                inquiry.append(primary_keys[i]);
                if (((i + 1) != primary_keys.length)) {
                    inquiry.append(",");
                }
            }
            inquiry.append(")");
        }

        inquiry.append(");");

        return inquiry.toString();
    }

    @Override
    public void clear(String table_name) {
        // body
    }
}
