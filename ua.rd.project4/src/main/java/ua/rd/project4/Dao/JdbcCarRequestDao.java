package ua.rd.project4.Dao;

import ua.rd.project4.entities.CarRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCarRequestDao extends CarRequestDao {
    final private static JdbcCarRequestDao instance = new JdbcCarRequestDao();
    private static Logger logger = LogManager.getLogger(JdbcCarRequestDao.class);
    private ClientDao clientDao = JdbcDaoFactory.getInstance().getClientDao();
    private CarDao carDao = JdbcDaoFactory.getInstance().getCarDao();
    private InvoiceDao invoiceDao = JdbcDaoFactory.getInstance().getInvoiceDao();

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
        try (Statement statement = ConnectionFactory.getInstance().getConnection().createStatement()) {
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
            logger.error("Table `car_request` didn't created: " + e.toString());
        }
    }

    @Override
    public boolean insert(CarRequest carRequest) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        boolean wasInserted = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `car_request` " +
                    "(car, client, dateFrom, dateTo, totalCost, approved, invoice) VALUES(?,?,?,?,?,?,?)");
            preparedStatement.setObject(1, carDao.findId(carRequest.getCar()));
            preparedStatement.setObject(2, clientDao.findId(carRequest.getClient()));
            preparedStatement.setDate(3, carRequest.getDateFrom());
            preparedStatement.setDate(4, carRequest.getDateTo());
            preparedStatement.setInt(5, carRequest.getTotalCost());
            preparedStatement.setBoolean(6, carRequest.isApproved());
            preparedStatement.setObject(7, invoiceDao.findId(carRequest.getInvoice()));
            wasInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return wasInserted;
    }

    @Override
    public boolean update(int id, CarRequest carRequest) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        boolean wasUpdated = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `car_request` SET " +
                    "car=?, client=?, dateFrom=?, dateTo=?, totalCost=?, approved=?, invoice=? WHERE id=?");
            preparedStatement.setObject(1, carDao.findId(carRequest.getCar()));
            preparedStatement.setObject(2, clientDao.findId(carRequest.getClient()));
            preparedStatement.setDate(3, carRequest.getDateFrom());
            preparedStatement.setDate(4, carRequest.getDateTo());
            preparedStatement.setInt(5, carRequest.getTotalCost());
            preparedStatement.setBoolean(6, carRequest.isApproved());
            preparedStatement.setObject(7, invoiceDao.findId(carRequest.getInvoice()));
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
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `car_request` WHERE id=?");
            preparedStatement.setInt(1, id);
            wasDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return wasDeleted;
    }

    @Override
    public CarRequest getById(int id) {
        CarRequest carRequest = null;
        try {
            PreparedStatement preparedStatement = ConnectionFactory.getInstance().getConnection().
                    prepareStatement("SELECT * FROM `car_request` WHERE id=? LIMIT 1");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                carRequest = new CarRequest(
                        carDao.getById(resultSet.getInt("car")),
                        clientDao.getById(resultSet.getInt("client")),
                        resultSet.getDate("dateFrom"),
                        resultSet.getDate("dateTo"),
                        resultSet.getInt("totalCost"),
                        resultSet.getBoolean("approved"),
                        invoiceDao.getById(resultSet.getInt("invoice")));
                carRequest.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return carRequest;
    }

    @Override
    public List<CarRequest> findAll() {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        List<CarRequest> allCarsRequests = new ArrayList<>();
        CarRequest carRequest;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `car_request`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carRequest = new CarRequest(
                        carDao.getById(resultSet.getInt("car")),
                        clientDao.getById(resultSet.getInt("client")),
                        resultSet.getDate("dateFrom"),
                        resultSet.getDate("dateTo"),
                        resultSet.getInt("totalCost"),
                        resultSet.getBoolean("approved"),
                        invoiceDao.getById(resultSet.getInt("invoice")));
                carRequest.setId(resultSet.getInt("id"));
                allCarsRequests.add(carRequest);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return allCarsRequests;
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
}
