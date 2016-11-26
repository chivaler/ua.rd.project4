package ua.rd.project4.Dao;

import org.apache.logging.log4j.LogManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    static ConnectionFactory instance = new ConnectionFactory();
    DataSource dataSource = null;
    String jdbcDriver;
    String jdbcUrl;
    String jdbcUser;
    String jdbcPassword;

    private ConnectionFactory() {
        setJdbcParameters(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/car_rent?verifyServerCertificate=false&useSSL=false&autoReconnect=true",
                "root",
                "925060");

        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/epam4_2");
        } catch (NamingException e) {
            LogManager.getLogger(JdbcDaoFactory.class).debug(e.toString());
        }

    }

    public static ConnectionFactory getInstance() {
        return instance;
    }

    public void setJdbcParameters(String jdbcDriver, String jdbcUrl, String jdbcUser, String jdbcPassword) {
        this.jdbcDriver = jdbcDriver;
        this.jdbcUrl = jdbcUrl;
        this.jdbcUser = jdbcUser;
        this.jdbcPassword = jdbcPassword;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = getPoolConnection();
        } catch (Exception e) {
//            LogManager.getLogger(JdbcDaoFactory.class).debug(e.toString());
            try {
                connection = getSingleConnection();
            } catch (Exception e1) {
                LogManager.getLogger(JdbcDaoFactory.class).warn(e1.toString());
            }
        }
        if (connection == null) {
            LogManager.getLogger(JdbcDaoFactory.class).error("Couln't create connection");
            throw new RuntimeException("Couln't create connection");
        }
        return connection;
    }

    private Connection getPoolConnection() throws Exception {
        return dataSource.getConnection();
    }

    private Connection getSingleConnection() throws Exception {
        Class.forName(jdbcDriver);
        return DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
    }

}