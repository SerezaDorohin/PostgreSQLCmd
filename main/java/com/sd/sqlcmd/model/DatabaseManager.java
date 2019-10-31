package com.sd.sqlcmd.model;

import java.sql.Connection;

public interface DatabaseManager {
    Connection connect();

    void create_table();

    void clear(String table_name);
}
