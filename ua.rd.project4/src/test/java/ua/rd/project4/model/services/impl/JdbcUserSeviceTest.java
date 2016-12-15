package ua.rd.project4.model.services.impl;

import org.junit.Test;
import ua.rd.project4.RandomEntities;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.exceptions.LoginExistsException;
import ua.rd.project4.model.services.UserService;

import static org.junit.Assert.*;

public class JdbcUserSeviceTest {
    UserService userService = JdbcUserSevice.getInstance();

    @Test
    public void insertOk() throws Exception {
        userService.insert(RandomEntities.getUser());
    }

    @Test
    public void updateOk() throws Exception {
        User user = RandomEntities.getUser();
        userService.insert(user);
        user.setPasswordHash(RandomEntities.getString());
        userService.update(user.getId(), user);
    }

    @Test(expected = LoginExistsException.class)
    public void insert() throws Exception {
        User user = RandomEntities.getUser();
        userService.insert(user);
        user.setPasswordHash(RandomEntities.getString());
        userService.insert(user);
    }

    @Test(expected = LoginExistsException.class)
    public void update() throws Exception {
        User user1 = RandomEntities.getUser();
        userService.insert(user1);
        User user2 = RandomEntities.getUser();
        userService.insert(user2);
        user1.setLogin(user2.getLogin());
        userService.update(user1.getId(), user1);
    }

}