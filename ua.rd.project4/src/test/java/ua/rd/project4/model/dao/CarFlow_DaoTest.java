package ua.rd.project4.model.dao;

import org.junit.BeforeClass;
import ua.rd.project4.domain.CarFlow;
import org.junit.Before;

public class CarFlow_DaoTest extends EntityDaoTest<CarFlow> {
    @BeforeClass
    public static void setParams() {
        JdbcDaoFactory.getInstance().getCarFlowDao().createTable();
    }

    @Override
    CarFlow initElem1() {
        return  new CarFlow(null, null, null, null, null, null, "1");
    }

    @Override
    CarFlow initElem2() {
        return  new CarFlow(null, null, null, null, null, null, "2");
    }

    @Override
    CarFlow initElem3() {
        return new CarFlow(null, null, null, null, null, null, "3");
    }

    @Override
    EntityDao<CarFlow> getDao() {
        return JdbcDaoFactory.getInstance().getCarFlowDao();
    }
}
