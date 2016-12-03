package ua.rd.project4.model.dao.impl;

import ua.rd.project4.domain.CarRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.model.dao.*;
import ua.rd.project4.model.dao.connection.impl.JdbcConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class JdbcCarRequestDao implements CarRequestDao {
    private final static JdbcCarRequestDao instance = new JdbcCarRequestDao();
    private final Logger logger = LogManager.getLogger(JdbcCarRequestDao.class);
    private final ClientDao clientDao = JdbcDaoFactory.getInstance().getClientDao();
    private final CarDao carDao = JdbcDaoFactory.getInstance().getCarDao();
    private final InvoiceDao invoiceDao = JdbcDaoFactory.getInstance().getInvoiceDao();

    private JdbcCarRequestDao() {
    }

    public static JdbcCarRequestDao getInstance() {
        return instance;
    }

    @Override
    public void createTable() {
        clientDao.createTable();
        carDao.createTable();
        invoiceDao.createTable();
        try (Statement statement = JdbcConnectionFactory.getInstance().getConnection().createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS `car_request` (" +
                    "id INT PRIMARY KEY auto_increment," +
                    "car INT," +
                    "client INT," +
                    "dateFrom DATE," +
                    "dateTo DATE," +
                    "totalCost INT," +
                    "approved BOOLEAN," +
                    "invoice INT," +
                    "FOREIGN KEY (car) REFERENCES cars(id)," +
                    "FOREIGN KEY (client) REFERENCES clients(id)," +
                    "FOREIGN KEY (invoice) REFERENCES invoices(id))");
        } catch (SQLException e) {
            logger.error("Table `car_request` didn't created: ", e);
        }
    }

    @Override
    public boolean insert(CarRequest carRequest) {
        boolean wasInserted = false;
        try (PreparedStatement preparedStatement = JdbcConnectionFactory.getInstance().getConnection().prepareStatement("INSERT INTO `car_request` " +
                "(car, client, dateFrom, dateTo, totalCost, approved, invoice) VALUES(?,?,?,?,?,?,?)")) {
            preparedStatement.setObject(1, carDao.findId(carRequest.getCar()));
            preparedStatement.setObject(2, clientDao.findId(carRequest.getClient()));
            preparedStatement.setDate(3, carRequest.getDateFrom());
            preparedStatement.setDate(4, carRequest.getDateTo());
            preparedStatement.setBigDecimal(5, carRequest.getTotalCost());
            preparedStatement.setBoolean(6, carRequest.isApproved());
            preparedStatement.setObject(7, invoiceDao.findId(carRequest.getInvoice()));
            wasInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasInserted;
    }

    @Override
    public boolean update(int id, CarRequest carRequest) {
        boolean wasUpdated = false;
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `car_request` SET " +
                     "car=?, client=?, dateFrom=?, dateTo=?, totalCost=?, approved=?, invoice=? WHERE id=?")) {
            preparedStatement.setObject(1, carDao.findId(carRequest.getCar()));
            preparedStatement.setObject(2, clientDao.findId(carRequest.getClient()));
            preparedStatement.setDate(3, carRequest.getDateFrom());
            preparedStatement.setDate(4, carRequest.getDateTo());
            preparedStatement.setBigDecimal(5, carRequest.getTotalCost());
            preparedStatement.setString(6, carRequest.getStatus().toString());
            preparedStatement.setObject(7, invoiceDao.findId(carRequest.getInvoice()));
            preparedStatement.setInt(8, id);
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
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `car_request` WHERE id=?")) {
            preparedStatement.setInt(1, id);
            wasDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasDeleted;
    }

    @Override
    public CarRequest getById(int id) {
        return findCarRequestsByIdField(id, "id").stream().findFirst().orElse(null);
    }


    @Override
    public List<CarRequest> findAll() {
        List<CarRequest> foundCarsRequests = new ArrayList<>();
        CarRequest carRequest;
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `car_request`")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carRequest = new CarRequest(
                        carDao.getById(resultSet.getInt("car")),
                        clientDao.getById(resultSet.getInt("client")),
                        resultSet.getDate("dateFrom"),
                        resultSet.getDate("dateTo"),
                        resultSet.getBigDecimal("totalCost"),
                        Optional.ofNullable(CarRequest.RequestStatus.valueOf(resultSet.getString("status"))).orElse(CarRequest.RequestStatus.NEW),
                        invoiceDao.getById(resultSet.getInt("invoice")));
                carRequest.setId(resultSet.getInt("id"));
                foundCarsRequests.add(carRequest);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundCarsRequests;
    }

    @Override
    public Integer findId(CarRequest carRequest) {
        if (carRequest == null)
            return null;
        for (CarRequest sqlRequest : findAll())
            if (sqlRequest.equals(carRequest))
                return sqlRequest.getId();
        return null;
    }

    private List<CarRequest> findCarRequestsByIdField(int id, String field) {
        if (!(field.equals("car") || field.equals("id") || field.equals("client") || field.equals("invoice")))
            throw new IllegalArgumentException();
        List<CarRequest> foundCarsRequests = new ArrayList<>();
        CarRequest carRequest;
        try (Connection connection = JdbcConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `car_request` WHERE " + field + "=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carRequest = new CarRequest(
                        carDao.getById(resultSet.getInt("car")),
                        clientDao.getById(resultSet.getInt("client")),
                        resultSet.getDate("dateFrom"),
                        resultSet.getDate("dateTo"),
                        resultSet.getBigDecimal("totalCost"),
                        Optional.ofNullable(CarRequest.RequestStatus.valueOf(resultSet.getString("status"))).orElse(CarRequest.RequestStatus.NEW),
                        invoiceDao.getById(resultSet.getInt("invoice")));
                carRequest.setId(resultSet.getInt("id"));
                foundCarsRequests.add(carRequest);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundCarsRequests;
    }

    @Override
    public List<CarRequest> findCarRequestsByClientId(int clientId) {
        return findCarRequestsByIdField(clientId, "client");
    }

    @Override
    public List<CarRequest> findCarRequestsByCarId(int carId) {
        return findCarRequestsByIdField(carId, "car");
    }

    @Override
    public List<CarRequest> findCarRequestsByInvoiceId(int invoiceId) {
        return findCarRequestsByIdField(invoiceId, "invoice");
    }
}
