package ua.rd.project4.model.dao;

import org.junit.BeforeClass;
import ua.rd.project4.domain.CarRequest;

public class CarRequest_DaoTest extends EntityDaoTest<CarRequest> {
    @BeforeClass
    public static void setParams() {
        JdbcDaoFactory.getInstance().getCarRequestDao().createTable();
    }

    @Override
    CarRequest initElem1() {
        return new CarRequest(null, null, java.sql.Date.valueOf("2016-11-25"), null, 0, false, null);
    }

    @Override
    CarRequest initElem2() {
        return new CarRequest(null, null, java.sql.Date.valueOf("2016-11-26"), null, 0, false, null);
    }

    @Override
    CarRequest initElem3() {
        return new CarRequest(null, null, java.sql.Date.valueOf("2016-11-27"), null, 0, false, null);
    }

    @Override
    EntityDao<CarRequest> getDao() {
        return JdbcDaoFactory.getInstance().getCarRequestDao();
    }

}
