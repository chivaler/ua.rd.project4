package ua.rd.project4.model.dao.connection;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection getConnection();
    public String getJdbcDriver();
}
