package ua.rd.project4.model.dao.impl;

import ua.rd.project4.domain.Invoice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.model.dao.ClientDao;
import ua.rd.project4.model.dao.connection.impl.JdbcConnectionFactory;
import ua.rd.project4.model.dao.InvoiceDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class JdbcInvoiceDao implements InvoiceDao {
    private static final JdbcInvoiceDao instance = new JdbcInvoiceDao();
    private final Logger logger = LogManager.getLogger(JdbcInvoiceDao.class);
    private final ClientDao clientDao = JdbcDaoFactory.getInstance().getClientDao();

    private JdbcInvoiceDao() {
    }

    public static JdbcInvoiceDao getInstance() {
        return instance;
    }

    @Override
    public void createTable() {
        clientDao.createTable();
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS `invoices` (" +
                    "id INT PRIMARY KEY auto_increment," +
                    "client INT," +
                    "dateCreated DATE," +
                    "total INT," +
                    "paid BOOLEAN," +
                    "description VARCHAR(16)," +
                    "FOREIGN KEY (client) REFERENCES clients(id))");
        } catch (SQLException e) {
            logger.error("Table `invoices` didn't created: ",e);
        }
    }

    @Override
    public boolean insert(Invoice invoice) {
        boolean wasInserted = false;
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `invoices` " +
                     "(client, dateCreated, total, paid, description) VALUES(?,?,?,?,?)")) {
            preparedStatement.setObject(1, clientDao.findId(invoice.getClient()));
            preparedStatement.setDate(2, invoice.getDateCreated());
            preparedStatement.setInt(3, invoice.getTotal());
            preparedStatement.setBoolean(4, invoice.isPaid());
            preparedStatement.setString(5, invoice.getDescription());
            wasInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasInserted;
    }

    @Override
    public boolean update(int id, Invoice invoice) {
        boolean wasUpdated = false;
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `invoices` SET " +
                     "client=?, dateCreated=?, total=?, paid=?, description=? WHERE id=?")) {
            preparedStatement.setObject(1, clientDao.findId(invoice.getClient()));
            preparedStatement.setDate(2, invoice.getDateCreated());
            preparedStatement.setInt(3, invoice.getTotal());
            preparedStatement.setBoolean(4, invoice.isPaid());
            preparedStatement.setString(5, invoice.getDescription());
            preparedStatement.setInt(6, id);
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
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `invoices` WHERE id=?")) {
            preparedStatement.setInt(1, id);
            wasDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasDeleted;
    }

    @Override
    public Invoice getById(int id) {
        Invoice invoice = null;
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `invoices` WHERE id=? LIMIT 1")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                invoice = new Invoice(
                        clientDao.getById(resultSet.getInt("client")),
                        resultSet.getDate("dateCreated"),
                        resultSet.getInt("total"),
                        resultSet.getBoolean("paid"),
                        resultSet.getString("description"));
                invoice.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return invoice;
    }

    @Override
    public List<Invoice> findAll() {
        List<Invoice> foundInvoices = new ArrayList<>();
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `invoices` ")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Invoice invoice = new Invoice(
                        clientDao.getById(resultSet.getInt("client")),
                        resultSet.getDate("dateCreated"),
                        resultSet.getInt("total"),
                        resultSet.getBoolean("paid"),
                        resultSet.getString("description"));
                invoice.setId(resultSet.getInt("id"));
                foundInvoices.add(invoice);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundInvoices;
    }

    @Override
    public Integer findId(Invoice invoice) {
        if (invoice == null)
            return null;
        for (Invoice sqlInvoice : findAll())
            if (sqlInvoice.equals(invoice))
                return sqlInvoice.getId();
        return null;
    }

    @Override
    public List<Invoice> findInvoicesByClientId(int idClient) {
        List<Invoice> foundInvoices = new ArrayList<>();
        Invoice invoice;
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `invoices` WHERE client=?")) {
            preparedStatement.setInt(1, idClient);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                invoice = new Invoice(
                        clientDao.getById(resultSet.getInt("client")),
                        resultSet.getDate("dateCreated"),
                        resultSet.getInt("total"),
                        resultSet.getBoolean("paid"),
                        resultSet.getString("description"));
                invoice.setId(resultSet.getInt("id"));
                foundInvoices.add(invoice);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundInvoices;
    }
}
