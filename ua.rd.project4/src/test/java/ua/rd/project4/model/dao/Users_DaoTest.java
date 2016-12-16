package ua.rd.project4.model.dao;

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
    UserDao getDao() {
        return JdbcDaoFactory.getInstance().getUserDao();
    }

    @Test
    public void getByLogin() throws Exception {
        User user1 = RandomEntities.getUser();
        getDao().insert(user1);
        User user2 = RandomEntities.getUser();
        getDao().insert(user2);
        assertThat(getDao().getByLogin(user1.getLogin()),is(user1));
        assertThat(getDao().getByLogin(user2.getLogin()),is(user2));
    }

    @Test
    public void getByLoginBlank() throws Exception {
        User user1 = RandomEntities.getUser();
        assertNull(getDao().getByLogin(user1.getLogin()));
    }

}
