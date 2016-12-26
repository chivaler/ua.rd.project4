package ua.rd.project4.model.dao;

import org.junit.Test;
import ua.rd.project4.RandomEntities;
import ua.rd.project4.domain.CarFlow;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;

import static org.junit.Assert.assertTrue;

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

    @Test
    public void findAll() throws Exception {
        CarFlowDao carFlowDao = JdbcDaoFactory.getInstance().getCarFlowDao();
        carFlowDao.insert(RandomEntities.getCarFlow());
        carFlowDao.insert(RandomEntities.getCarFlow());
        carFlowDao.insert(RandomEntities.getCarFlow());
        assertTrue(carFlowDao.findAll(1).size()==1);
        assertTrue(carFlowDao.findAll(3).size()==3);
    }
}
