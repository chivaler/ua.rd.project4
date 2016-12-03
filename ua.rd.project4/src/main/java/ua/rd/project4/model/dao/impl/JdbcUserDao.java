package ua.rd.project4.model.dao.impl;

import ua.rd.project4.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.model.dao.ClientDao;
import ua.rd.project4.model.dao.connection.impl.JdbcConnectionFactory;
import ua.rd.project4.model.dao.UserDao;
import ua.rd.project4.model.holders.UserHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class JdbcUserDao implements UserDao {
    private final static JdbcUserDao instance = new JdbcUserDao();
    private static final Logger logger = LogManager.getLogger(JdbcUserDao.class);
    private final ClientDao clientDao = JdbcDaoFactory.getInstance().getClientDao();

    private JdbcUserDao() {
    }

    public static JdbcUserDao getInstance() {
        return instance;
    }

    @Override
    public void createTableIfNotExist() {
        clientDao.createTableIfNotExist();
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS `users` (" +
                    "id INT PRIMARY KEY auto_increment," +
                    "isAdmin BOOLEAN," +
                    "login VARCHAR(16)," +
                    "passwordHash VARCHAR(32)," +
                    "client INT," +
                    "FOREIGN KEY (client) REFERENCES clients(id))");
        } catch (SQLException e) {
            logger.error("Table `users` didn't created: ",e);
        }
    }

    @Override
    public boolean insert(User user) {
        boolean wasInserted = false;
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `users` " +
                     "(isAdmin, login, passwordHash, client) VALUES(?,?,?,?)")) {
            preparedStatement.setBoolean(1, user.isAdmin());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPasswordHash());
            preparedStatement.setObject(4, user.getClientId()==0?null:user.getClientId());
            wasInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasInserted;
    }

    @Override
    public boolean update(int id, User user) {
        boolean wasUpdated = false;
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `users` SET "
                     + "isAdmin=?, login=?, passwordHash=?, client=? WHERE id=?")) {
            preparedStatement.setBoolean(1, user.isAdmin());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPasswordHash());
            preparedStatement.setObject(4, user.getClientId()==0?null:user.getClientId());
            preparedStatement.setInt(5, id);
            wasUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasUpdated;
    }

    @Override
    public boolean delete(int id) {

        boolean wasDeleted = false;
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `users` WHERE id=?")) {
            preparedStatement.setInt(1, id);
            wasDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasDeleted;
    }

    private User getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new UserHolder(
                resultSet.getBoolean("isAdmin"),
                resultSet.getString("login"),
                resultSet.getString("passwordHash"),
                resultSet.getInt("client"),
                JdbcDaoFactory.getInstance());
        user.setId(resultSet.getInt("id"));
        return user;
    }

    @Override
    public User getById(int id) {
        User user = null;
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `users` WHERE id=? LIMIT 1")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = getEntityFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> allUsers = new ArrayList<>();
        User user;
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `users`")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = getEntityFromResultSet(resultSet);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return allUsers;
    }

    @Override
    public Integer findId(User user) {
        if (user == null)
            return null;
        for (User sqlUser : findAll())
            if (sqlUser.equals(user))
                return sqlUser.getId();
        return null;
    }

    @Override
    public List<User> findUsersByClientId(int clientId) {
        List<User> allUsers = new ArrayList<>();
        User user;
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `users` WHERE client=?")) {
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = getEntityFromResultSet(resultSet);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return allUsers;
    }
}
