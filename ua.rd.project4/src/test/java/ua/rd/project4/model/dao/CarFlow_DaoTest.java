package ua.rd.project4.model.dao;

import org.junit.BeforeClass;
import ua.rd.project4.domain.CarFlow;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;

public class CarFlow_DaoTest extends EntityDaoTest<CarFlow> {
    @Override
    CarFlow initElem1() {
        return  new CarFlow(null, null, null, null, null, "1");
    }

    @Override
    CarFlow initElem2() {
        return  new CarFlow(null, null, null, null, null,  "2");
    }

    @Override
    CarFlow initElem3() {
        return new CarFlow(null, null, null, null, null,  "3");
    }

    @Override
    EntityDao<CarFlow> getDao() {
        return JdbcDaoFactory.getInstance().getCarFlowDao();
    }
}
