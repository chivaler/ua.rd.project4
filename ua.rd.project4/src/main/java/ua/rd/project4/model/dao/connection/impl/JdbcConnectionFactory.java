package ua.rd.project4.model.dao.connection.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.model.dao.connection.ConnectionFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JdbcConnectionFactory implements ConnectionFactory {
    private static final JdbcConnectionFactory instance = new JdbcConnectionFactory();
    private final Logger logger = LogManager.getLogger(JdbcConnectionFactory.class);
    private DataSource dataSource = null;
    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;
    private ConnectionType connectionType = ConnectionType.POOL_PREFERRED;

    enum ConnectionType {
        POOL_PREFERRED, SINGLE;
    }

    private JdbcConnectionFactory() {
        //TODO Jdbc from file, different from tests
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties prop = new Properties();
        try (InputStream input = loader.getResourceAsStream("db.properties")) {
            prop.load(input);
            jdbcDriver = prop.getProperty("driver");
            jdbcUser = prop.getProperty("dbUser");
            jdbcUrl = prop.getProperty("dbUrl");
            jdbcPassword = prop.getProperty("dbPassword");
            Class.forName(jdbcDriver);
        } catch (IOException e) {
            logger.error(e);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }

        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/carRentService");
        } catch (NamingException e) {
            logger.debug(e);
            connectionType = ConnectionType.SINGLE;
        }

    }

    public static JdbcConnectionFactory getInstance() {
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        if (connectionType == ConnectionType.POOL_PREFERRED)
            try {
                connection = getPoolConnection();
            } catch (Exception e) {
                logger.debug(e);
            }
        if (connection == null) {
            try {
                connection = getSingleConnection();
            } catch (Exception e) {
                logger.debug(e);
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
        return DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
    }

}