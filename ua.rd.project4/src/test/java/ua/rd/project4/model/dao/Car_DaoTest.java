package ua.rd.project4.model.dao;

import org.junit.BeforeClass;
import ua.rd.project4.domain.Car;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;


public class Car_DaoTest extends EntityDaoTest<Car> {
    @BeforeClass
    public static void setParams() {
        JdbcDaoFactory.getInstance().getCarDao().createTable();
    }

    @Override
    Car initElem1() {
        return new Car("Mersedes", "black", Car.CarType.SEDAN, "FFJJ", "heavy.", 17000, 300);
    }

    @Override
    Car initElem2() {
        return new Car("BENZ", "white", Car.CarType.MINI, "JJKK", "light.", 16000, 250);
    }

    @Override
    Car initElem3() {
        return new Car("Zapor", "white", Car.CarType.SPORT, "1212", "dsdasd", 1234, 30);
    }

    @Override
    EntityDao<Car> getDao() {
        return JdbcDaoFactory.getInstance().getCarDao();
    }
}
