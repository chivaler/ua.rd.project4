package ua.rd.project4.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

class ConnectionFactory {
    private static final ConnectionFactory instance = new ConnectionFactory();
    private final Logger logger = LogManager.getLogger(ConnectionFactory.class);
    private DataSource dataSource = null;
    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;

    private ConnectionFactory() {
        setJdbcParameters(
                "com.mysql.jdbc.Driver",
//                "com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/car_rent?verifyServerCertificate=false&useSSL=false",
                "root",
                "925060");

//        setJdbcParameters(
//                "org.h2.Driver",
//                "jdbc:h2:mem:test",
//                "sa",
//                "");

        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/epam4_2");
        } catch (NamingException e) {
            logger.debug(e.toString());
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
//            logger.debug(e.toString());
            try {
                connection = getSingleConnection();
            } catch (Exception e1) {
                logger.error(e1.toString());
            }
        }
        if (connection == null) {
            logger.error("Couln't create connection");
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