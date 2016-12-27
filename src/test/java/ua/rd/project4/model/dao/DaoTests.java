package ua.rd.project4.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import ua.rd.project4.RandomEntities;
import ua.rd.project4.domain.*;
import org.junit.Test;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

@RunWith(Enclosed.class)
public class DaoTests {

    public static class CarDaoTests extends EntityDaoTest<Car> {

        @Override
        Car getElem() {
            return RandomEntities.getCar();
        }

        @Override
        EntityDao<Car> getDao() {
            return JdbcDaoFactory.getInstance().getCarDao();
        }
    }

    public static class CarFlowDaoTests extends EntityDaoTest<CarFlow> {

        @Override
        CarFlow getElem() {
            return RandomEntities.getCarFlow();
        }

        @Override
        EntityDao<CarFlow> getDao() {
            return JdbcDaoFactory.getInstance().getCarFlowDao();
        }
    }

    public static class UserDaoTests extends EntityDaoTest<User> {

        @Override
        User getElem() {
            return RandomEntities.getUser();
        }

        @Override
        EntityDao<User> getDao() {
            return JdbcDaoFactory.getInstance().getUserDao();
        }
    }

    public static class InvoiceDaoTests extends EntityDaoTest<Invoice> {

        @Override
        Invoice getElem() {
            return RandomEntities.getInvoice();
        }

        @Override
        EntityDao<Invoice> getDao() {
            return JdbcDaoFactory.getInstance().getInvoiceDao();
        }
    }

    public static class ClientDaoTests extends EntityDaoTest<Client> {

        @Override
        Client getElem() {
            return RandomEntities.getClient();
        }

        @Override
        EntityDao<Client> getDao() {
            return JdbcDaoFactory.getInstance().getClientDao();
        }
    }

    public static class CarRequestDaoTests extends EntityDaoTest<CarRequest> {

        @Override
        CarRequest getElem() {
            return RandomEntities.getCarRequest();
        }

        @Override
        EntityDao<CarRequest> getDao() {
            return JdbcDaoFactory.getInstance().getCarRequestDao();
        }
    }
}


abstract class EntityDaoTest<T extends Entity> {
    private Logger logger = LogManager.getLogger(EntityDaoTest.class);
    private final EntityDao<T> dao = getDao();
    private final T elem1 = getElem();
    private final T elem2 = getElem();
    private final T elem3 = getElem();

    {
        getDao().createTableIfNotExist();
    }

    abstract T getElem();

    abstract EntityDao<T> getDao();

    @Test
    public void createTable() throws Exception {
        dao.createTableIfNotExist();
    }

    @Test
    public void insert_delete() throws Exception {
        int prevCount = dao.findAll().size();

        assertThat(dao.insert(elem1), is(true));
        assertThat(dao.findAll().size(), is(prevCount + 1));
        assertThat(dao.findAll().contains(elem1), is(true));
        int idElem1 = dao.findAll().stream().
                filter(s -> s.equals(elem1)).
                findAny().get().getId();
        assertThat(dao.delete(idElem1), is(true));
        assertThat(dao.findAll().size(), is(prevCount));

        if (prevCount == 0) {
            assertThat(dao.delete(1), is(false));
            assertThat(dao.delete(0), is(false));
            assertThat(dao.delete(-1), is(false));
        }
    }

    @Test
    public void get_update() throws Exception {
        // TODO rewrite
        int prevCount = dao.findAll().size();
        assertThat(dao.insert(elem1), is(true));
        int id = dao.findAll().get(0).getId();
        assertThat(dao.update(id, elem2), is(true));
        assertThat(dao.findAll().size(), is(prevCount + 1));
        T car3 = dao.getById(id);
        assertThat(elem2.equals(elem1), is(false));
        assertThat(car3.equals(elem2), is(true));
        assertThat(car3.equals(elem1), is(false));
        assertThat(dao.update(id, elem2), is(true));
        assertThat(dao.delete(dao.findAll().get(0).getId()), is(true));
        assertThat(dao.update(id, elem2), is(false));
        assertThat(dao.findAll().size(), is(prevCount));
    }

    @Test
    public void findAll() throws Exception {
        List<T> listOfExistBeforeTest = dao.findAll();
        List<T> listToAdd = new ArrayList<>();
        listToAdd.add(elem1);
        listToAdd.add(elem2);
        listToAdd.add(elem3);
        listToAdd.forEach(s -> dao.insert(s));

        List<T> listAll = new ArrayList<>(listOfExistBeforeTest);
        listAll.addAll(listToAdd);
        List<T> listResultFromDaoAfterInsert = dao.findAll();
        assertThat(listResultFromDaoAfterInsert, is(listAll));

        listToAdd.forEach(s -> assertThat(s.getId() > 0, is(true)));
        listToAdd.forEach(s -> assertThat(dao.delete(s.getId()), is(true)));

        assertThat(dao.findAll(), containsInAnyOrder(listOfExistBeforeTest.toArray()));
    }

    @Test
    public void getById() throws Exception {
        assertThat(dao.getById(0) == null, is(true));
        assertThat(dao.getById(-1) == null, is(true));
    }

    @Test
    public void insertTestId() throws Exception {
        assertThat(elem3.getId(), is(-1));
        dao.insert(elem3);
        assertThat(elem3.getId() > 0, is(true));
        assertThat(dao.getById(elem3.getId()), is(elem3));
    }
}
