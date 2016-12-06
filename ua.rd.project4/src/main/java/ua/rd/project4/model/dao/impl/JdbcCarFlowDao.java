package ua.rd.project4.model.dao.impl;

import ua.rd.project4.domain.CarFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.model.dao.*;
import ua.rd.project4.model.dao.connection.ConnectionFactory;
import ua.rd.project4.model.dao.connection.impl.JdbcConnectionFactory;
import ua.rd.project4.model.holders.CarFlowHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class JdbcCarFlowDao implements CarFlowDao {
    private static final JdbcCarFlowDao instance = new JdbcCarFlowDao();
    private static final Logger logger = LogManager.getLogger(JdbcCarFlowDao.class);
    private final ConnectionFactory connectionFactory = JdbcConnectionFactory.getInstance();
    private final ClientDao clientDao = JdbcDaoFactory.getInstance().getClientDao();
    private final CarDao carDao = JdbcDaoFactory.getInstance().getCarDao();
    private final InvoiceDao invoiceDao = JdbcDaoFactory.getInstance().getInvoiceDao();
    private final CarRequestDao carRequestDao = JdbcDaoFactory.getInstance().getCarRequestDao();
    private final UserDao userDao = JdbcDaoFactory.getInstance().getUserDao();

    private JdbcCarFlowDao() {
    }

    public static JdbcCarFlowDao getInstance() {
        return instance;
    }

    @Override
    public void createTableIfNotExist() {
        userDao.createTableIfNotExist();
        clientDao.createTableIfNotExist();
        invoiceDao.createTableIfNotExist();
        userDao.createTableIfNotExist();
        carRequestDao.createTableIfNotExist();
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS `car_flow` (" +
                    "id INT PRIMARY KEY auto_increment," +
                    "car INT," +
                    "carFlowType INT," +
                    "carRequest INT," +
                    "responsiblePerson INT," +
                    "invoice INT," +
                    "supplement VARCHAR(240)," +
                    "dateCreated TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (car) REFERENCES cars(id)," +
                    "FOREIGN KEY (invoice) REFERENCES invoices(id)," +
                    "FOREIGN KEY (responsiblePerson) REFERENCES users(id)," +
                    "FOREIGN KEY (carRequest) REFERENCES car_request(id))");
        } catch (SQLException e) {
            logger.error("Table `car_flow` didn't created: ", e);
        }
    }

    @Override
    public boolean insert(CarFlow carFlow) {
        boolean wasInserted = false;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `car_flow` " +
                     "(car, carFlowType, carRequest, responsiblePerson, invoice, supplement) VALUES(?,?,?,?,?,?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, carFlow.getCarId() == 0 ? null : carFlow.getCarId());
            preparedStatement.setObject(2, carFlow.getCarFlowType() == null ? null : carFlow.getCarFlowType().getValue());
            preparedStatement.setObject(3, carFlow.getCarRequestId() == 0 ? null : carFlow.getCarRequestId());
            preparedStatement.setObject(4, carFlow.getResponsiblePersonId() == 0 ? null : carFlow.getResponsiblePersonId());
            preparedStatement.setObject(5, carFlow.getInvoiceId() == 0 ? null : carFlow.getInvoiceId());
            preparedStatement.setString(6, carFlow.getSupplement());
            wasInserted = preparedStatement.executeUpdate() > 0;
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            carFlow.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasInserted;
    }

    @Override
    public boolean update(int id, CarFlow carFlow) {
        boolean wasUpdated = false;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `car_flow` SET " +
                     "car=?, carFlowType=?, carRequest=?, responsiblePerson=?, invoice=?, supplement=? WHERE id=?")) {
            preparedStatement.setObject(1, carFlow.getCarId() == 0 ? null : carFlow.getCarId());
            preparedStatement.setObject(2, carFlow.getCarFlowType() == null ? null : carFlow.getCarFlowType().getValue());
            preparedStatement.setObject(3, carFlow.getCarRequestId() == 0 ? null : carFlow.getCarRequestId());
            preparedStatement.setObject(4, carFlow.getResponsiblePersonId() == 0 ? null : carFlow.getResponsiblePersonId());
            preparedStatement.setObject(5, carFlow.getInvoiceId() == 0 ? null : carFlow.getInvoiceId());
            preparedStatement.setString(6, carFlow.getSupplement());
            preparedStatement.setInt(7, id);
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
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `car_flow` WHERE id=?")) {
            preparedStatement.setInt(1, id);
            wasDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasDeleted;
    }


    private CarFlow getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        CarFlow carFlow = new CarFlowHolder(
                resultSet.getInt("car"),
                CarFlow.CarFlowType.getTypeByValue(resultSet.getInt("carFlowType")),
                resultSet.getInt("carRequest"),
                resultSet.getInt("responsiblePerson"),
                resultSet.getInt("invoice"),
                resultSet.getString("supplement"),
                JdbcDaoFactory.getInstance());
        carFlow.setId(resultSet.getInt("id"));
        carFlow.setDateCreated(resultSet.getTimestamp("dateCreated"));
        return carFlow;
    }

    @Override
    public List<CarFlow> findAll() {
        List<CarFlow> foundCarFlows = new ArrayList<>();
        CarFlow carFlow;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `car_flow`")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carFlow = getEntityFromResultSet(resultSet);
                foundCarFlows.add(carFlow);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundCarFlows;
    }

    private List<CarFlow> findCarFlowsByIdField(int id, JdbcFields field) {
        String sqlQuery = "SELECT * FROM `car_flow` WHERE " + field.getFieldName() + "=" + id;
        List<CarFlow> foundCarFlows = new ArrayList<>();
        CarFlow carFlow;
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                carFlow = getEntityFromResultSet(resultSet);
                foundCarFlows.add(carFlow);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundCarFlows;
    }

    @Override
    public List<CarFlow> findCarFlowsByCarId(int carId) {
        return findCarFlowsByIdField(carId, JdbcFields.CAR);
    }

    @Override
    public List<CarFlow> findCarFlowsByCarRequestId(int carRequestId) {
        return findCarFlowsByIdField(carRequestId, JdbcFields.CARREQUEST);
    }

    @Override
    public List<CarFlow> findCarFlowsByUserId(int userId) {
        return findCarFlowsByIdField(userId, JdbcFields.RESPONSIBLEUSER);
    }

    @Override
    public List<CarFlow> findCarFlowsByInvoiceId(int invoiceId) {
        return findCarFlowsByIdField(invoiceId, JdbcFields.INVOICE);
    }

    @Override
    public List<CarFlow> findAll(int n) {
        List<CarFlow> foundCarFlows = new ArrayList<>();
        CarFlow carFlow;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `car_flow` ORDER BY dateCreated DESC LIMIT " + Integer.toString(n))) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carFlow = getEntityFromResultSet(resultSet);
                foundCarFlows.add(carFlow);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundCarFlows;
    }

    @Override
    public CarFlow getById(int id) {
        return findCarFlowsByIdField(id, JdbcFields.ID).stream().findFirst().orElse(null);
    }
}
