package ua.rd.project4.model.dao.impl;

import ua.rd.project4.domain.CarRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.model.dao.*;
import ua.rd.project4.model.dao.connection.ConnectionFactory;
import ua.rd.project4.model.dao.connection.impl.JdbcConnectionFactory;
import ua.rd.project4.model.holders.CarRequestHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class JdbcCarRequestDao implements CarRequestDao {
    private static final JdbcCarRequestDao instance = new JdbcCarRequestDao();
    private final Logger logger = LogManager.getLogger(JdbcCarRequestDao.class);
    private final ConnectionFactory connectionFactory = JdbcConnectionFactory.getInstance();
    private final ClientDao clientDao = JdbcDaoFactory.getInstance().getClientDao();
    private final CarDao carDao = JdbcDaoFactory.getInstance().getCarDao();
    private final InvoiceDao invoiceDao = JdbcDaoFactory.getInstance().getInvoiceDao();
    private boolean h2Used = JdbcConnectionFactory.getInstance().isH2Used();


    private JdbcCarRequestDao() {
    }

    public static JdbcCarRequestDao getInstance() {
        return instance;
    }

    @Override
    public void createTableIfNotExist() {
        clientDao.createTableIfNotExist();
        carDao.createTableIfNotExist();
        invoiceDao.createTableIfNotExist();
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS `car_request` (" +
                    "id INT PRIMARY KEY auto_increment," +
                    "car INT," +
                    "client INT," +
                    "dateFrom DATE," +
                    "dateTo DATE," +
                    "totalCost DECIMAL(10,2)," +
                    "invoice INT," +
                    (h2Used ? "status VARCHAR(16)," : "status enum('NEW','APPROVED','REJECTED','PROGRESS','DONE') NOT NULL DEFAULT 'NEW',") +
                    "rejectReason VARCHAR(64)," +
                    "dateCreated TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP," +
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
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `car_request` " +
                             "(car, client, dateFrom, dateTo, totalCost, invoice, status, rejectReason) VALUES(?,?,?,?,?,?,?,?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, carRequest.getCarId() == 0 ? null : carRequest.getCarId());
            preparedStatement.setObject(2, carRequest.getClientId() == 0 ? null : carRequest.getClientId());
            preparedStatement.setDate(3, carRequest.getDateFrom());
            preparedStatement.setDate(4, carRequest.getDateTo());
            preparedStatement.setBigDecimal(5, carRequest.getTotalCost());
            preparedStatement.setObject(6, carRequest.getInvoiceId() == 0 ? null : carRequest.getInvoiceId());
            preparedStatement.setString(7, carRequest.getStatus().toString());
            preparedStatement.setString(8, carRequest.getRejectReason());
            wasInserted = preparedStatement.executeUpdate() > 0;
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            carRequest.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasInserted;
    }

    @Override
    public boolean update(int id, CarRequest carRequest) {
        boolean wasUpdated = false;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `car_request` SET " +
                     "car=?, client=?, dateFrom=?, dateTo=?, totalCost=?, invoice=?, status=?, rejectReason=? WHERE id=?")) {
            preparedStatement.setObject(1, carRequest.getCarId() == 0 ? null : carRequest.getCarId());
            preparedStatement.setObject(2, carRequest.getClientId() == 0 ? null : carRequest.getClientId());
            preparedStatement.setDate(3, carRequest.getDateFrom());
            preparedStatement.setDate(4, carRequest.getDateTo());
            preparedStatement.setBigDecimal(5, carRequest.getTotalCost());
            preparedStatement.setObject(6, carRequest.getInvoiceId() == 0 ? null : carRequest.getInvoiceId());
            preparedStatement.setString(7, carRequest.getStatus().toString());
            preparedStatement.setString(8, carRequest.getRejectReason());
            preparedStatement.setInt(9, id);
            wasUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasUpdated;
    }

    @Override
    public boolean delete(int id) {
        boolean wasDeleted = false;
        try (Connection connection = connectionFactory.getConnection();
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
        CarRequest carRequest = null;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `car_request` WHERE `id`=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                carRequest = getEntityFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return carRequest;
    }

    private CarRequest getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        CarRequest carRequest = new CarRequestHolder(
                resultSet.getInt("car"),
                resultSet.getInt("client"),
                resultSet.getDate("dateFrom"),
                resultSet.getDate("dateTo"),
                resultSet.getBigDecimal("totalCost"),
                CarRequest.RequestStatus.valueOf(Optional.ofNullable(resultSet.getString("status")).orElse("NEW")),
                resultSet.getInt("invoice"),
                resultSet.getString("rejectReason"),
                JdbcDaoFactory.getInstance());
        carRequest.setId(resultSet.getInt("id"));
        carRequest.setDateCreated(resultSet.getTimestamp("dateCreated"));
        return carRequest;
    }

    @Override
    public List<CarRequest> findAll() {
        List<CarRequest> foundCarsRequests = new ArrayList<>();
        CarRequest carRequest;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `car_request`")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carRequest = getEntityFromResultSet(resultSet);
                if (carRequest != null)
                    foundCarsRequests.add(carRequest);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundCarsRequests;
    }

    @Override
    public List<CarRequest> findCarRequestsByClientId(int clientId) {
        List<CarRequest> foundCarsRequests = new ArrayList<>();
        CarRequest carRequest;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `car_request` WHERE `client`=?")) {
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carRequest = getEntityFromResultSet(resultSet);
                if (carRequest != null)
                    foundCarsRequests.add(carRequest);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundCarsRequests;
    }

    @Override
    public List<CarRequest> findCarRequestsByCarId(int carId) {
        List<CarRequest> foundCarsRequests = new ArrayList<>();
        CarRequest carRequest;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `car_request` WHERE `car`=?")) {
            preparedStatement.setInt(1, carId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carRequest = getEntityFromResultSet(resultSet);
                foundCarsRequests.add(carRequest);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundCarsRequests;
    }

    @Override
    public List<CarRequest> findCarRequestsByInvoiceId(int invoiceId) {
        List<CarRequest> foundCarsRequests = new ArrayList<>();
        CarRequest carRequest;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `car_request` WHERE `invoice`=?")) {
            preparedStatement.setInt(1, invoiceId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carRequest = getEntityFromResultSet(resultSet);
                foundCarsRequests.add(carRequest);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundCarsRequests;
    }

    @Override
    public List<CarRequest> findConflictingCarRequests(CarRequest carRequestChecked) {
        return findConflictingCarRequests(carRequestChecked.getDateFrom(), carRequestChecked.getDateTo(),
                carRequestChecked.getCarId(), carRequestChecked.getId());
    }

    public List<CarRequest> findConflictingCarRequests(Date dateFrom, Date dateTo, int carId, int excludedCarRequestId) {
        List<CarRequest> foundCarsRequests = new ArrayList<>();
        CarRequest carRequest;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM `car_request` " +
                             "WHERE  `dateFrom`<=? AND `dateTo`>=? " +
                             "AND status NOT LIKE 'REJECTED' AND status NOT LIKE 'DONE' " +
                             "AND `car`=? " +
                             "AND `id` <> ?")) {
            preparedStatement.setDate(1, dateTo);
            preparedStatement.setDate(2, dateFrom);
            preparedStatement.setInt(3, carId);
            preparedStatement.setInt(4, excludedCarRequestId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carRequest = getEntityFromResultSet(resultSet);
                if (carRequest != null)
                    foundCarsRequests.add(carRequest);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundCarsRequests;
    }

    @Override
    public List<CarRequest> findAllActive() {
        List<CarRequest> foundCarsRequests = new ArrayList<>();
        CarRequest carRequest;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `car_request` WHERE `status`<>'DONE' AND `status`<>'REJECTED'")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carRequest = getEntityFromResultSet(resultSet);
                foundCarsRequests.add(carRequest);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundCarsRequests;
    }
}
