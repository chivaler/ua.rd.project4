package ua.rd.project4.model.dao.impl;

import ua.rd.project4.domain.Car;
import org.apache.logging.log4j.*;
import ua.rd.project4.model.dao.CarDao;
import ua.rd.project4.model.dao.connection.ConnectionFactory;
import ua.rd.project4.model.dao.connection.impl.JdbcConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

class JdbcCarDao implements CarDao {
    private static final JdbcCarDao instance = new JdbcCarDao();
    private final Logger logger = LogManager.getLogger(JdbcCarDao.class);
    private final ConnectionFactory connectionFactory = JdbcConnectionFactory.getInstance();
    private boolean H2Used = Objects.equals(connectionFactory.getJdbcDriver(), "org.h2.Driver");

    private JdbcCarDao() {
    }

    public static JdbcCarDao getInstance() {
        return instance;
    }

    @Override
    public void createTableIfNotExist() {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();) {
            statement.execute("CREATE TABLE IF NOT EXISTS `cars` (" +
                    "id INT PRIMARY KEY auto_increment," +
                    "model VARCHAR(16)," +
                    "color VARCHAR(16)," +
                    (H2Used ? "carType VARCHAR(16)," : "carType enum('LIMO','SPORT','MINI','PICKUP','SEDAN') NOT NULL DEFAULT 'SEDAN',") +
                    "registrationNumber VARCHAR(16)," +
                    "description VARCHAR(60)," +
                    "price DECIMAL(10,2)," +
                    "rentPricePerDay DECIMAL(10,2))");
        } catch (SQLException e) {
            logger.error("Table `car` didn't created: ", e);
        }
    }

    @Override
    public boolean insert(Car car) {
        boolean wasInserted = false;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `cars` " +
                     "(model, color, carType, registrationNumber, description, price, rentPricePerDay) VALUES(?,?,?,?,?,?,?)")) {
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setString(2, car.getColor());
            preparedStatement.setString(3, Optional.of(car.getCarType().toString()).orElse("NULL"));
            preparedStatement.setString(4, car.getRegistrationNumber());
            preparedStatement.setString(5, car.getDescription());
            preparedStatement.setBigDecimal(6, car.getPrice());
            preparedStatement.setBigDecimal(7, car.getRentPricePerDay());
            wasInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasInserted;
    }

    @Override
    public boolean update(int id, Car car) {
        boolean wasUpdated = false;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `cars` SET " +
                     "model=?, color=?, carType=?, registrationNumber=?, description=?, price=?, rentPricePerDay=? WHERE id=?")) {
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setString(2, car.getColor());
            preparedStatement.setString(3, Optional.of(car.getCarType().toString()).orElse("NULL"));
            preparedStatement.setString(4, car.getRegistrationNumber());
            preparedStatement.setString(5, car.getDescription());
            preparedStatement.setBigDecimal(6, car.getPrice());
            preparedStatement.setBigDecimal(7, car.getRentPricePerDay());
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
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `cars` WHERE id=?")) {
            preparedStatement.setInt(1, id);
            wasDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
        }
        return wasDeleted;
    }

    @Override
    public Car getById(int id) {
        Car car = null;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `cars` WHERE id=? LIMIT 1")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                car = new Car(
                        resultSet.getString("model"),
                        resultSet.getString("color"),
                        Car.CarType.valueOf(resultSet.getString("carType")),
                        resultSet.getString("registrationNumber"),
                        resultSet.getString("description"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getBigDecimal("rentPricePerDay"));
                car.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return car;
    }

    @Override
    public List<Car> findAll() {
        List<Car> foundCars = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `cars`")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Car car = new Car(
                        resultSet.getString("model"),
                        resultSet.getString("color"),
                        Car.CarType.valueOf(resultSet.getString("carType")),
                        resultSet.getString("registrationNumber"),
                        resultSet.getString("description"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getBigDecimal("rentPricePerDay"));
                car.setId(resultSet.getInt("id"));
                foundCars.add(car);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return foundCars;
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
