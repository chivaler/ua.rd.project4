package ua.rd.project4.model.dao;

import ua.rd.project4.domain.SystemUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao extends UserDao {
    final private static JdbcUserDao instance = new JdbcUserDao();
    private static Logger logger = LogManager.getLogger(JdbcUserDao.class);
    private ClientDao clientDao = JdbcDaoFactory.getInstance().getClientDao();

    private JdbcUserDao() {
    }

    public static JdbcUserDao getInstance() {
        return instance;
    }

    @Override
    public void createTable() {
        clientDao.createTable();
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS `users` (" +
                    "id INT PRIMARY KEY auto_increment," +
                    "isAdmin BOOLEAN," +
                    "login VARCHAR(16)," +
                    "password VARCHAR(32)," +
                    "client INT," +
                    "FOREIGN KEY (client) REFERENCES clients(id))");
        } catch (SQLException e) {
            logger.error("Table `users` didn't created: " + e.toString());
        }
    }

    @Override
    public boolean insert(SystemUser user) {
        boolean wasInserted = false;
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `users` " +
                     "(isAdmin, login, password, client) VALUES(?,?,?,?)")) {
            preparedStatement.setBoolean(1, user.isAdmin());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setObject(4, clientDao.findId(user.getClient()));
            wasInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return wasInserted;
    }

    @Override
    public boolean update(int id, SystemUser user) {
        boolean wasUpdated = false;
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `users` SET "
                     + "isAdmin=?, login=?, password=?, client=? WHERE id=?")) {
            preparedStatement.setBoolean(1, user.isAdmin());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setObject(4, clientDao.findId(user.getClient()));
            preparedStatement.setInt(5, id);
            wasUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return wasUpdated;
    }

    @Override
    public boolean delete(int id) {

        boolean wasDeleted = false;
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `users` WHERE id=?")) {
            preparedStatement.setInt(1, id);
            wasDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return wasDeleted;
    }

    @Override
    public SystemUser getById(int id) {
        SystemUser user = null;
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `users` WHERE id=? LIMIT 1")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new SystemUser(
                        resultSet.getBoolean("isAdmin"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        clientDao.getById(resultSet.getInt("client")));
                user.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return user;
    }

    @Override
    public List<SystemUser> findAll() {
        List<SystemUser> allUsers = new ArrayList<>();
        SystemUser user;
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `users`")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new SystemUser(
                        resultSet.getBoolean("isAdmin"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        clientDao.getById(resultSet.getInt("client")));
                user.setId(resultSet.getInt("id"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return allUsers;
    }

    @Override
    public Integer findId(SystemUser systemUser) {
        if (systemUser == null)
            return null;
        for (SystemUser sqlUser : findAll())
            if (sqlUser.equals(systemUser))
                return sqlUser.getId();
        return null;
    }

    @Override
    public List<SystemUser> findUsersByClientId(int clientId) {
        List<SystemUser> allUsers = new ArrayList<>();
        SystemUser user;
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `users` WHERE client=?")) {
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new SystemUser(
                        resultSet.getBoolean("isAdmin"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        clientDao.getById(resultSet.getInt("client")));
                user.setId(resultSet.getInt("id"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return allUsers;
    }
}
