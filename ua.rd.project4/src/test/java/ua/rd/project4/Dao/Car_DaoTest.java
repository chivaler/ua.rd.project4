package ua.rd.project4.Dao;

import ua.rd.project4.entities.Car;
import org.junit.Before;



public class Car_DaoTest extends EntityDaoTest<Car> {
    @Before
    public void seParams() {
        elem1 = new Car("Mersedes", "black", Car.CarType.SEDAN, "FFJJ", "heavy.", 17000, 300);
        elem2 = new Car("BENZ", "white", Car.CarType.MINI, "JJKK", "light.", 16000, 250);
        elem3 = new Car("Zapor", "white", Car.CarType.SPORT, "1212", "dsdasd", 1234, 30);
        setClass(Car.class);
        dao.createTable();
    }

}
