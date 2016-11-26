package ua.rd.project4.Dao;

import ua.rd.project4.entities.CarRequest;
import org.junit.Before;

public class CarRequest_DaoTest extends EntityDaoTest<CarRequest> {
    @Before
    public void setParams() {
        elem1 = new CarRequest(null, null, java.sql.Date.valueOf("2016-11-25"), null, 0, false, null);
        elem2 = new CarRequest(null, null, java.sql.Date.valueOf("2016-11-26"), null, 0, false, null);
        elem3 = new CarRequest(null, null, java.sql.Date.valueOf("2016-11-27"), null, 0, false, null);
        setClass(CarRequest.class);
        dao.createTable();

    }

}
