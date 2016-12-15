package ua.rd.project4.model.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.rd.project4.RandomEntities;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class Users_DaoTest extends EntityDaoTest<User> {
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
