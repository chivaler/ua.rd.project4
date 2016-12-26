package ua.rd.project4.model.services;

import org.junit.Test;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.exceptions.LoginExistsException;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;
import ua.rd.project4.RandomEntities;

import java.util.Collections;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserServiceTest {
    private final UserService userService = DefaultServiceFactory.getInstance().getUserService();
    private final ClientService clientService = DefaultServiceFactory.getInstance().getClientService();

    @Test
    public void findUsersByClientId_blank() throws Exception {
        assertThat(userService.findUsersByClientId(-1).isEmpty(), is(true));
        assertThat(userService.findUsersByClientId(0).isEmpty(), is(true));
    }

    @Test
    public void findUsersByClientId1() throws Exception {
        User user1 = RandomEntities.getUser();
        User user2 = RandomEntities.getUser();
        assertThat(userService.insert(user1), is(true));
        assertThat(userService.insert(user2), is(true));
        assertThat(userService.findUsersByClientId(-1).isEmpty(), is(true));
        assertThat(userService.findUsersByClientId(0).isEmpty(), is(true));

        Client client1 = RandomEntities.getClient();
        assertThat(clientService.insert(client1), is(true));
        assertThat(userService.findUsersByClientId(client1.getId()).isEmpty(), is(true));

        user1.setClient(client1);
        userService.update(user1.getId(), user1);
        assertThat(userService.findUsersByClientId(client1.getId()), is(Collections.singletonList(user1)));
        user2.setClient(client1);
        userService.update(user2.getId(), user2);
        assertThat(userService.findUsersByClientId(client1.getId()), containsInAnyOrder(user1, user2));
        userService.delete(user1.getId());
        userService.delete(user2.getId());
        clientService.delete(client1.getId());
    }

    @Test
    public void authentication_blank() throws Exception {
        assertThat(userService.authentication("login", "password") == null, is(true));
        assertThat(userService.authentication("", "password") == null, is(true));
        assertThat(userService.authentication("login", "") == null, is(true));
        assertThat(userService.authentication("login", "password") == null, is(true));

    }

    @Test
    public void authentication() throws Exception {
        User user1 = RandomEntities.getUser();
        assertThat(userService.authentication(user1.getLogin(), user1.getPasswordHash()) == null, is(true));
        user1.setPasswordHash(userService.getHashPassword("password"));
        assertThat(userService.authentication(user1.getLogin(), user1.getPasswordHash()) == null, is(true));
        userService.insert(user1);
        assertThat(userService.authentication(user1.getLogin(), user1.getPasswordHash()) == null, is(true));
        assertThat(userService.authentication(user1.getLogin(), "") == null, is(true));
        assertThat(userService.authentication(user1.getLogin(), "otherPassword") == null, is(true));
        assertThat(userService.authentication(user1.getLogin(), "password"), is(user1));

        userService.delete(user1.getId());
    }

    @Test
    public void getHashPassword() throws Exception {
        assertThat(userService.getHashPassword("md5"), is("1bc29b36f623ba82aaf6724fd3b16718"));
        assertThat(userService.getHashPassword("password"), is("5f4dcc3b5aa765d61d8327deb882cf99"));
    }

    @Test(expected = LoginExistsException.class)
    public void insert() throws Exception {
        User user1 = RandomEntities.getUser();
        assertThat(userService.insert(user1), is(true));
        User user2 = RandomEntities.getUser();
        user2.setLogin(user1.getLogin());
        userService.insert(user2);
    }

    @Test(expected = LoginExistsException.class)
    public void update() throws Exception {
        User user1 = RandomEntities.getUser();
        User user2 = RandomEntities.getUser();
        assertThat(userService.insert(user1), is(true));
        assertThat(userService.insert(user2), is(true));

        user1.setLogin(user2.getLogin());
        userService.update(user1.getId(), user1);
    }



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
    public void insertx() throws Exception {
        User user = RandomEntities.getUser();
        userService.insert(user);
        user.setPasswordHash(RandomEntities.getString());
        userService.insert(user);
    }

    @Test(expected = LoginExistsException.class)
    public void updatex() throws Exception {
        User user1 = RandomEntities.getUser();
        userService.insert(user1);
        User user2 = RandomEntities.getUser();
        userService.insert(user2);
        user1.setLogin(user2.getLogin());
        userService.update(user1.getId(), user1);
    }
}