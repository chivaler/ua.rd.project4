package ua.rd.project4.model.dao;

import org.junit.BeforeClass;
import ua.rd.project4.domain.User;

public class Users_DaoTest extends EntityDaoTest<User> {
    @BeforeClass
    public static void setParams() {
        JdbcDaoFactory.getInstance().getUserDao().createTable();
    }

    @Override
    User initElem1() {
        return new User(true,"admin","bla",null);
    }

    @Override
    User initElem2() {
        return new User(true,"user1","",null);
    }

    @Override
    User initElem3() {
        return new User(false,"user1","",null);
    }

    @Override
    EntityDao<User> getDao() {
        return JdbcDaoFactory.getInstance().getUserDao();
    }
}