package ua.rd.project4.model.dao.connection.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.jdbcx.JdbcDataSource;
import ua.rd.project4.model.dao.connection.ConnectionFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    private JdbcConnectionFactory() {
        //TODO Jdbc from file, different from tests
//        setJdbcParameters(
//                "com.mysql.jdbc.Driver",
////                "com.mysql.cj.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/car_rent?verifyServerCertificate=false&useSSL=false",
//                "root",
//                "925060");

//        setJdbcParameters(
//                "org.h2.Driver",
//                "jdbc:h2:mem:test",
//                "sa",
//                "");

//        try {
//            InitialContext ic = new InitialContext();
//            dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/carRentService");
//        } catch (NamingException e) {
//            logger.debug(e);
//        }
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties prop = new Properties();
        try(InputStream input = loader.getResourceAsStream("db.properties")) {
//        try (InputStream input = new FileInputStream("db.properties")) {
            prop.load(input);
            jdbcDriver =prop.getProperty("driver");
            jdbcUser = prop.getProperty("dbUser");
            jdbcUrl = prop.getProperty("dbUrl");
            jdbcPassword = prop.getProperty("dbPassword");
        } catch (IOException e) {
            logger.error(e);
        }
//        dataSource = new JdbcDataSource( );
    }

    public static JdbcConnectionFactory getInstance() {
        return instance;
    }

    private void setJdbcParameters(String jdbcDriver, String jdbcUrl, String jdbcUser, String jdbcPassword) {
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
            logger.debug(e);
            try {
                connection = getSingleConnection();
            } catch (Exception e1) {
                logger.error(e1);
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