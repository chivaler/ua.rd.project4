package ua.rd.project4.model.dao;

import ua.rd.project4.domain.CarFlow;
import org.junit.Before;

public class CarFlow_DaoTest extends EntityDaoTest<CarFlow> {
    @Before
    public void setParams() {
        elem1 = new CarFlow(null, null, null, null, null, null, "1");
        elem2 = new CarFlow(null, null, null, null, null, null, "2");
        elem3 = new CarFlow(null, null, null, null, null, null, "3");
        setClass(CarFlow.class);
        dao.createTable();
    }
}
