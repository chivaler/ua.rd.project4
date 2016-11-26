package ua.rd.project4.Dao;

import ua.rd.project4.entities.Car;
import org.apache.logging.log4j.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCarDao extends CarDao {
    final private static JdbcCarDao instance = new JdbcCarDao();
    private static Logger logger = LogManager.getLogger(JdbcCarDao.class);

    private JdbcCarDao() {
    }

    public static JdbcCarDao getInstance() {
        return instance;
    }

    @Override
    public void createTable() {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS `cars` (" +
                    "id INT PRIMARY KEY auto_increment," +
                    "model VARCHAR(16)," +
                    "color VARCHAR(16)," +
                    "carType enum('LIMO','SPORT','MINI','PICKUP','SEDAN') NOT NULL DEFAULT 'SEDAN'," +
                    "registrationNumber VARCHAR(16)," +
                    "description VARCHAR(60)," +
                    "price INT," +
                    "rentPricePerDay INT)");
        } catch (SQLException e) {
            logger.error("Table `car` didn't created: " + e.toString());
        }
    }

    @Override
    public boolean insert(Car car) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        boolean wasInserted = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `cars` " +
                    "(model, color, carType, registrationNumber, description, price, rentPricePerDay) VALUES(?,?,?,?,?,?,?)");
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setString(2, car.getColor());
            preparedStatement.setString(3, Optional.of(car.getCarType().toString()).orElse("NULL"));
            preparedStatement.setString(4, car.getRegistrationNumber());
            preparedStatement.setString(5, car.getDescription());
            preparedStatement.setInt(6, car.getPrice());
            preparedStatement.setInt(7, car.getRentPricePerDay());
            wasInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return wasInserted;
    }

    @Override
    public boolean update(int id, Car car) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        boolean wasUpdated = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `cars` SET " +
                    "model=?, color=?, carType=?, registrationNumber=?, description=?, price=?, rentPricePerDay=? WHERE id=?");
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setString(2, car.getColor());
            preparedStatement.setString(3, Optional.of(car.getCarType().toString()).orElse("NULL"));
            preparedStatement.setString(4, car.getRegistrationNumber());
            preparedStatement.setString(5, car.getDescription());
            preparedStatement.setInt(6, car.getPrice());
            preparedStatement.setInt(7, car.getRentPricePerDay());
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
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `cars` WHERE id=?");
            preparedStatement.setInt(1, id);
            wasDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return wasDeleted;
    }

    @Override
    public Car getById(int id) {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        Car car = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `cars` WHERE id=? LIMIT 1");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                car = new Car(
                        resultSet.getString("model"),
                        resultSet.getString("color"),
                        Car.CarType.valueOf(resultSet.getString("carType")),
                        resultSet.getString("registrationNumber"),
                        resultSet.getString("description"),
                        resultSet.getInt("price"),
                        resultSet.getInt("rentPricePerDay"));
                car.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return car;
    }

    @Override
    public List<Car> findAll() {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        List<Car> allCars = new ArrayList<>();
        Car car;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `cars`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                car = new Car(
                        resultSet.getString("model"),
                        resultSet.getString("color"),
                        Car.CarType.valueOf(resultSet.getString("carType")),
                        resultSet.getString("registrationNumber"),
                        resultSet.getString("description"),
                        resultSet.getInt("price"),
                        resultSet.getInt("rentPricePerDay"));
                car.setId(resultSet.getInt("id"));
                allCars.add(car);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return allCars;
    }

    @Override
    public Integer findId(Car car) {
        if (car == null)
            return null;
        for (Car sqlCar : findAll())
            if (sqlCar.equals(car))
                return sqlCar.getId();
        return null;
    }
}
