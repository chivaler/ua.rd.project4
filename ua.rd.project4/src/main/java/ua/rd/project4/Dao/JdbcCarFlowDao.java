package ua.rd.project4.Dao;

import ua.rd.project4.entities.CarFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCarFlowDao extends CarFlowDao {
    final private static JdbcCarFlowDao instance = new JdbcCarFlowDao();
    private static Logger logger = LogManager.getLogger(JdbcCarFlowDao.class);
    private ClientDao clientDao = JdbcDaoFactory.getInstance().getClientDao();
    private CarDao carDao = JdbcDaoFactory.getInstance().getCarDao();
    private InvoiceDao invoiceDao = JdbcDaoFactory.getInstance().getInvoiceDao();
    private CarRequestDao carRequestDao = JdbcDaoFactory.getInstance().getCarRequestDao();
    private UserDao userDao = JdbcDaoFactory.getInstance().getUserDao();

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
                    "carRequest INT,"+
                    "responsiblePerson INT,"+
                    "invoice INT," +
                    "supplement VARCHAR(240)," +
                    "FOREIGN KEY (car) REFERENCES cars(id)," +
                    "FOREIGN KEY (client) REFERENCES clients(id)," +
                    "FOREIGN KEY (invoice) REFERENCES invoices(id),"+
                    "FOREIGN KEY (responsiblePerson) REFERENCES users(id)," +
                    "FOREIGN KEY (carRequest) REFERENCES car_request(id))");
        } catch (SQLException e) {
            logger.error("Table `car_flow` didn't created: " + e.toString());
        }
    }

    @Override
    public boolean insert(CarFlow carFlow) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        boolean wasInserted = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `car_flow` " +
                    "(car, carFlowType, client, carRequest, responsiblePerson, invoice, supplement) VALUES(?,?,?,?,?,?,?)");
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
            logger.error(e.toString());
        }
        return wasInserted;
    }

    @Override
    public boolean update(int id, CarFlow carFlow) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        boolean wasUpdated = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `car_flow` SET " +
                    "car=?, carFlowType=?, client=?, carRequest=?, responsiblePerson=?, invoice=?, supplement=? WHERE id=?");
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
            logger.error(e.toString());
        }
        return wasUpdated;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        boolean wasDeleted = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `car_flow` WHERE id=?");
            preparedStatement.setInt(1, id);
            wasDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return wasDeleted;
    }

    @Override
    public CarFlow getById(int id) {;
        CarFlow carFlow = null;
        try {
            PreparedStatement preparedStatement = ConnectionFactory.getInstance().getConnection().
                    prepareStatement("SELECT * FROM `car_flow` WHERE id=? LIMIT 1");
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
            logger.error(e.toString());
        }
        return carFlow;
    }

    @Override
    public List<CarFlow> findAll() {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        List<CarFlow> allCarFlows = new ArrayList<>();
        CarFlow carFlow;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `car_flow`");
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
                allCarFlows.add(carFlow);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return allCarFlows;
    }

}
