package ua.rd.project4.model.dao.impl;

import ua.rd.project4.domain.Invoice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.model.dao.ClientDao;
import ua.rd.project4.model.dao.connection.impl.JdbcConnectionFactory;
import ua.rd.project4.model.dao.InvoiceDao;
import ua.rd.project4.model.holders.InvoiceHolder;

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
    public void createTableIfNotExist() {
        clientDao.createTableIfNotExist();
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS `invoices` (" +
                    "id INT PRIMARY KEY auto_increment," +
                    "client INT," +
                    "total DECIMAL(10,2)," +
                    "paid BOOLEAN," +
                    "description VARCHAR(64)," +
                    "dateCreated TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP," +
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
                     "(client, total, paid, description) VALUES(?,?,?,?)")) {
            preparedStatement.setObject(1, invoice.getClientId()==0?null:invoice.getClientId());
            preparedStatement.setBigDecimal(2, invoice.getTotal());
            preparedStatement.setBoolean(3, invoice.isPaid());
            preparedStatement.setString(4, invoice.getDescription());
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
                     "client=?, total=?, paid=?, description=? WHERE id=?")) {
            preparedStatement.setObject(1, invoice.getClientId()==0?null:invoice.getClientId());
            preparedStatement.setBigDecimal(2, invoice.getTotal());
            preparedStatement.setBoolean(3, invoice.isPaid());
            preparedStatement.setString(4, invoice.getDescription());
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
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `invoices` WHERE id=?")) {
            preparedStatement.setInt(1, id);
            wasDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasDeleted;
    }

    private Invoice getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        Invoice invoice = new InvoiceHolder(
                resultSet.getInt("client"),
                resultSet.getBigDecimal("total"),
                resultSet.getBoolean("paid"),
                resultSet.getString("description"),
                JdbcDaoFactory.getInstance());
        invoice.setId(resultSet.getInt("id"));
        invoice.setDateCreated(resultSet.getTimestamp("dateCreated"));
        return invoice;
    }

    @Override
    public Invoice getById(int id) {
        Invoice invoice = null;
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `invoices` WHERE id=? LIMIT 1")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                invoice = getEntityFromResultSet(resultSet);
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
                Invoice invoice = getEntityFromResultSet(resultSet);
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
                invoice = getEntityFromResultSet(resultSet);
                foundInvoices.add(invoice);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundInvoices;
    }
}
