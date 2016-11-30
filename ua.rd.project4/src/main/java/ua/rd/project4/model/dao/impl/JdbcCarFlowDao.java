package ua.rd.project4.model.dao.impl;

import ua.rd.project4.domain.CarFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.model.dao.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class JdbcCarFlowDao implements CarFlowDao {
    private static final JdbcCarFlowDao instance = new JdbcCarFlowDao();
    private static final Logger logger = LogManager.getLogger(JdbcCarFlowDao.class);
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
    public void createTable() {
        userDao.createTable();
        clientDao.createTable();
        invoiceDao.createTable();
        userDao.createTable();
        carRequestDao.createTable();
        try (Statement statement = ConnectionFactory.getInstance().getConnection().createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS `car_flow` (" +
                    "id INT PRIMARY KEY auto_increment," +
                    "car INT," +
                    "carFlowType INT," +
                    "client INT," +
                    "carRequest INT," +
                    "responsiblePerson INT," +
                    "invoice INT," +
                    "supplement VARCHAR(240)," +
                    "FOREIGN KEY (car) REFERENCES cars(id)," +
                    "FOREIGN KEY (client) REFERENCES clients(id)," +
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
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `car_flow` " +
                     "(car, carFlowType, client, carRequest, responsiblePerson, invoice, supplement) VALUES(?,?,?,?,?,?,?)")) {
            preparedStatement.setObject(1, carDao.findId(carFlow.getCar()));
            if (carFlow.getCarFlowType() == null)
                preparedStatement.setObject(2, null);
            else
                preparedStatement.setObject(2, carFlow.getCarFlowType().getValue());
            preparedStatement.setObject(3, clientDao.findId(carFlow.getClient()));
            preparedStatement.setObject(4, carRequestDao.findId(carFlow.getCarRequest()));
            preparedStatement.setObject(5, userDao.findId(carFlow.getResponsiblePerson()));
            preparedStatement.setObject(6, invoiceDao.findId(carFlow.getInvoice()));
            preparedStatement.setString(7, carFlow.getSupplement());
            wasInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasInserted;
    }

    @Override
    public boolean update(int id, CarFlow carFlow) {
        boolean wasUpdated = false;
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `car_flow` SET " +
                     "car=?, carFlowType=?, client=?, carRequest=?, responsiblePerson=?, invoice=?, supplement=? WHERE id=?")) {
            preparedStatement.setObject(1, carDao.findId(carFlow.getCar()));
            if (carFlow.getCarFlowType() == null)
                preparedStatement.setObject(2, null);
            else
                preparedStatement.setObject(2, carFlow.getCarFlowType().getValue());
            preparedStatement.setObject(3, clientDao.findId(carFlow.getClient()));
            preparedStatement.setObject(4, carRequestDao.findId(carFlow.getCarRequest()));
            preparedStatement.setObject(5, userDao.findId(carFlow.getResponsiblePerson()));
            preparedStatement.setObject(6, invoiceDao.findId(carFlow.getInvoice()));
            preparedStatement.setString(7, carFlow.getSupplement());
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
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `car_flow` WHERE id=?")) {
            preparedStatement.setInt(1, id);
            wasDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasDeleted;
    }

    @Override
    public CarFlow getById(int id) {
        CarFlow carFlow = null;
        try (PreparedStatement preparedStatement = ConnectionFactory.getInstance().getConnection().
                prepareStatement("SELECT * FROM `car_flow` WHERE id=? LIMIT 1")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                carFlow = new CarFlow(
                        carDao.getById(resultSet.getInt("car")),
                        CarFlow.CarFlowType.getTypeByValue(resultSet.getInt("carFlowType")),
                        clientDao.getById(resultSet.getInt("client")),
                        carRequestDao.getById(resultSet.getInt("carRequest")),
                        userDao.getById(resultSet.getInt("responsiblePerson")),
                        invoiceDao.getById(resultSet.getInt("invoice")),
                        resultSet.getString("supplement"));
                carFlow.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return carFlow;
    }

    @Override
    public List<CarFlow> findAll() {
        List<CarFlow> foundCarFlows = new ArrayList<>();
        CarFlow carFlow;
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `car_flow`")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carFlow = new CarFlow(
                        carDao.getById(resultSet.getInt("car")),
                        CarFlow.CarFlowType.getTypeByValue(resultSet.getInt("carFlowType")),
                        clientDao.getById(resultSet.getInt("client")),
                        carRequestDao.getById(resultSet.getInt("carRequest")),
                        userDao.getById(resultSet.getInt("responsiblePerson")),
                        invoiceDao.getById(resultSet.getInt("invoice")),
                        resultSet.getString("supplement"));
                carFlow.setId(resultSet.getInt("id"));
                foundCarFlows.add(carFlow);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundCarFlows;
    }

    private List<CarFlow> findCarFlowsByIdField(int id, String field) {
        String sqlQuery = "SELECT * FROM `car_flow` WHERE " + field + "=" + id;
        List<CarFlow> foundCarFlows = new ArrayList<>();
        CarFlow carFlow;
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                carFlow = new CarFlow(
                        carDao.getById(resultSet.getInt("car")),
                        CarFlow.CarFlowType.getTypeByValue(resultSet.getInt("carFlowType")),
                        clientDao.getById(resultSet.getInt("client")),
                        carRequestDao.getById(resultSet.getInt("carRequest")),
                        userDao.getById(resultSet.getInt("responsiblePerson")),
                        invoiceDao.getById(resultSet.getInt("invoice")),
                        resultSet.getString("supplement"));
                carFlow.setId(resultSet.getInt("id"));
                foundCarFlows.add(carFlow);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundCarFlows;
    }

    @Override
    public List<CarFlow> findCarFlowsByClientId(int clientId) {
        return findCarFlowsByIdField(clientId, "client");
    }

    @Override
    public List<CarFlow> findCarFlowsByCarId(int carId) {
        return findCarFlowsByIdField(carId, "car");
    }

    @Override
    public List<CarFlow> findCarFlowsByCarRequestId(int carRequestId) {
        return findCarFlowsByIdField(carRequestId, "carRequest");
    }

    @Override
    public List<CarFlow> findCarFlowsByUserId(int userId) {
        return findCarFlowsByIdField(userId, "user");
    }

    @Override
    public List<CarFlow> findCarFlowsByInvoiceId(int invoiceId) {
        return findCarFlowsByIdField(invoiceId, "invoice");
    }
}
