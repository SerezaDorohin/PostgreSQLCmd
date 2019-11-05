package com.sd.sqlcmd.model;

import java.sql.Connection;

public interface DatabaseManager {
    Connection connect();

    void create_table(String table_name, String schema_name, String[] columns, String[] primary_key);

    void create_schema(String schema_name);

    String tables();

    void clear(String table_name);
}
