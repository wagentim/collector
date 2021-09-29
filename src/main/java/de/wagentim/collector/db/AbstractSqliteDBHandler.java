package de.wagentim.collector.db;

import de.wagentim.collector.utils.IConstants;

import java.sql.Connection;

public abstract class AbstractSqliteDBHandler 
{
    protected String dbPath = IConstants.TXT_EMPTY_STRING;
    protected static final String DB_PRE_FIX = "jdbc:sqlite:";
    protected static final String DB_MEMORY = "memory:";
    protected Connection conn = null;

    public AbstractSqliteDBHandler(String dbPath)
    {
        this.dbPath = dbPath;
        initialConnection();
    }

    protected void initialConnection() 
    {

    }
}
