package ua.rd.project4.model.dao;

import ua.rd.project4.domain.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcClientDao implements ClientDao {
    final private static JdbcClientDao instance = new JdbcClientDao();
    private static Logger logger = LogManager.getLogger(JdbcClientDao.class);

    private JdbcClientDao() {
    }

    public static JdbcClientDao getInstance() {
        return instance;
    }

    @Override
    public void createTable() {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS `clients` (" +
                    "id INT PRIMARY KEY auto_increment," +
                    "firstName VARCHAR(16)," +
                    "lastName VARCHAR(16)," +
                    "address VARCHAR(64)," +
                    "telephone VARCHAR(16)," +
                    "email VARCHAR(24)," +
                    "idCard VARCHAR(100))");
        } catch (SQLException e) {
            logger.error("Table `clients` didn't created:" + e.toString());
        }
    }

    @Override
    public boolean insert(Client client) {
        boolean wasInserted = false;
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `clients` " +
                     "(firstName, lastName, address, telephone, email, idCard) VALUES(?,?,?,?,?,?)")) {
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getAddress());
            preparedStatement.setString(4, client.getTelephone());
            preparedStatement.setString(5, client.getEmail());
            preparedStatement.setString(6, client.getIdCard());
            wasInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return wasInserted;
    }

    @Override
    public boolean update(int id, Client client) {
        boolean wasUpdated = false;
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `clients` SET " +
                     "firstName=?, lastName=?, address=?, telephone=?, email=?, idCard=? WHERE id=?")) {
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getAddress());
            preparedStatement.setString(4, client.getTelephone());
            preparedStatement.setString(5, client.getEmail());
            preparedStatement.setString(6, client.getIdCard());
            preparedStatement.setInt(7, id);
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
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `clients` WHERE id=?")) {
            preparedStatement.setInt(1, id);
            wasDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return wasDeleted;
    }

    @Override
    public Client getById(int id) {
        Client client = null;
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `clients` WHERE id=? LIMIT 1")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                client = new Client(
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("address"),
                        resultSet.getString("telephone"),
                        resultSet.getString("email"),
                        resultSet.getString("idCard"));
                client.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return client;
    }

    @Override
    public List<Client> findAll() {
        List<Client> foundClients = new ArrayList<>();
        Client client;
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `clients`")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                client = new Client(
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("address"),
                        resultSet.getString("telephone"),
                        resultSet.getString("email"),
                        resultSet.getString("idCard"));
                client.setId(resultSet.getInt("id"));
                foundClients.add(client);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return foundClients;
    }

    @Override
    public Integer findId(Client client) {
        if (client == null)
            return null;
        for (Client sqlClient : findAll())
            if (sqlClient.equals(client))
                return sqlClient.getId();
        return null;
    }
}
