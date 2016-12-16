package ua.rd.project4.model.dao;

import ua.rd.project4.domain.Car;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;

import java.math.BigDecimal;


public class Car_DaoTest extends EntityDaoTest<Car> {
    @Override
    Car initElem1() {
        return new Car("Mersedes", "black", Car.CarType.SEDAN, "FFJJ", "heavy.", new BigDecimal(17000), new BigDecimal(300));
    }

    @Override
    Car initElem2() {
        return new Car("BENZ", "white", Car.CarType.MINI, "JJKK", "light.", new BigDecimal(16000), new BigDecimal(250));
    }

    @Override
    Car initElem3() {
        return new Car("Zapor", "white", Car.CarType.SPORT, "1212", "dsdasd", new BigDecimal(1234), new BigDecimal(50));
    }

    @Override
    EntityDao<Car> getDao() {
        return JdbcDaoFactory.getInstance().getCarDao();
    }
}
