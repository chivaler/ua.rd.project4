package ua.rd.project4.model.dao;

import org.junit.BeforeClass;
import ua.rd.project4.domain.SystemUser;
import org.junit.Before;

public class Users_DaoTest extends EntityDaoTest<SystemUser> {
    @BeforeClass
    public static void setParams() {
        JdbcDaoFactory.getInstance().getUserDao().createTable();
    }

    @Override
    SystemUser initElem1() {
        return new SystemUser(true,"admin","bla",null);
    }

    @Override
    SystemUser initElem2() {
        return new SystemUser(true,"user1","",null);
    }

    @Override
    SystemUser initElem3() {
        return new SystemUser(false,"user1","",null);
    }

    @Override
    EntityDao<SystemUser> getDao() {
        return JdbcDaoFactory.getInstance().getUserDao();
    }
}
