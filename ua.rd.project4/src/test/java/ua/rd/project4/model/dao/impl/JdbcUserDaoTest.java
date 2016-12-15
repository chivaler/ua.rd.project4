package ua.rd.project4.model.dao.impl;

import org.junit.Test;
import ua.rd.project4.RandomEntities;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.dao.UserDao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class JdbcUserDaoTest {
    UserDao userDao = JdbcUserDao.getInstance();
    {
        userDao.createTableIfNotExist();
    }
    @Test
    public void getByLogin() throws Exception {
        User user1 = RandomEntities.getUser();
        userDao.insert(user1);
        User user2 = RandomEntities.getUser();
        userDao.insert(user2);
        assertThat(userDao.getByLogin(user1.getLogin()),is(user1));
        assertThat(userDao.getByLogin(user2.getLogin()),is(user2));
    }

    @Test
    public void getByLoginBlank() throws Exception {
        User user1 = RandomEntities.getUser();
        assertNull(userDao.getByLogin(user1.getLogin()));
    }

}