package com.sd.sqlcmd.model;

import com.sd.sqlcmd.view.Console;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
//            statement = null;
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
    public void create_table() {

    }

    @Override
    public void clear(String table_name) {
        // body
    }
}
