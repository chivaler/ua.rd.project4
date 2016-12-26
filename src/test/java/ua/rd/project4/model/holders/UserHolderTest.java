package ua.rd.project4.model.holders;

import org.junit.Test;
import ua.rd.project4.domain.Client;
import ua.rd.project4.domain.User;
import ua.rd.project4.RandomEntities;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserHolderTest {
    ServiceFactory serviceFactory = DefaultServiceFactory.getInstance();

    @Test
    public void equalsTest() throws Exception {
        User user1 = RandomEntities.getUser();
        UserHolder userHolder = new UserHolder(user1.isAdmin(),user1.getLogin(),
                user1.getPasswordHash(), 0, JdbcDaoFactory.getInstance());
        assertThat(user1.equals(userHolder),is(true));
        assertThat(userHolder.equals(user1),is(true));
    }

    @Test
    public void equals2Test() throws Exception {
        User user1 = RandomEntities.getUser();
        Client client1 = RandomEntities.getClient();
        serviceFactory.getClientService().insert(client1);


        UserHolder userHolder = new UserHolder(user1.isAdmin(),user1.getLogin(),
                user1.getPasswordHash(), 0, JdbcDaoFactory.getInstance());
        assertThat(user1.equals(userHolder),is(true));
        assertThat(userHolder.equals(user1),is(true));
    }


    @Test
    public void insertAndGetTest() throws Exception {
        User user1 = RandomEntities.getUser();
        serviceFactory.getUserService().insert(user1);
        User user2 = serviceFactory.getUserService().getById(user1.getId());
        assertThat(user2 instanceof UserHolder, is(true));
        assertThat(user2.equals(user1),is(true));
        assertThat(user1.equals(user2),is(true));
        serviceFactory.getUserService().delete(user1.getId());
    }

    @Test
    public void getClientId() throws Exception {
        Client client1 = RandomEntities.getClient();
        serviceFactory.getClientService().insert(client1);
        User user1 = new UserHolder(false, null,null,client1.getId(),JdbcDaoFactory.getInstance());
        assertThat(user1.getClientId(),is(client1.getId()));
        assertThat(user1.getClient(),is(client1));
        serviceFactory.getClientService().delete(client1.getId());
    }

    @Test
    public void getClient() throws Exception {
        User user1 = new UserHolder(false, null,null,0,JdbcDaoFactory.getInstance());
        assertThat(user1.getClientId(),is(0));
        assertThat(user1.getClient()==null,is(true));
    }

    @Test
    public void setClient() throws Exception {
        User user1 = new UserHolder(false, null,null,0,JdbcDaoFactory.getInstance());
        Client client1 = RandomEntities.getClient();
        serviceFactory.getClientService().insert(client1);
        user1.setClient(client1);
        assertThat(user1.getClientId(),is(client1.getId()));
        assertThat(user1.getClient(),is(client1));
    }

}