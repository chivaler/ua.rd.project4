package ua.rd.project4.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.domain.Entity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

public abstract class EntityDaoTest<T extends Entity> {
    private Logger logger = LogManager.getLogger(EntityDaoTest.class);
    private final EntityDao<T> dao = getDao();
    private final T elem1 = initElem1();
    private final T elem2  = initElem2();
    private final T elem3 = initElem3();
    {
        getDao().createTableIfNotExist();
    }

    abstract T initElem1();
    abstract T initElem2();
    abstract T initElem3();
    abstract EntityDao<T> getDao();

    @Test
    public void createTable() throws Exception {
        dao.createTableIfNotExist();
    }

    @Test
    public void insert_delete() throws Exception {
        int prevCount = dao.findAll().size();
        Integer findelem1 = dao.findId(elem1);
        if (findelem1 == null) {
            assertThat(dao.insert(elem1), is(true));
            assertThat(dao.findAll().size(), is(prevCount + 1));
            assertThat(dao.findAll().contains(elem1), is(true));
            int idElem1 = dao.findAll().stream().
                    filter(s -> s.equals(elem1)).
                    findAny().get().getId();
            assertThat(dao.delete(idElem1), is(true));
            assertThat(dao.findAll().size(), is(prevCount));
        } else
            assertThat(prevCount > 0, is(true));
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

        listToAdd.forEach(s -> assertThat(dao.findId(s)>0,is(true)));
        listToAdd.forEach(s -> s.setId(dao.findId(s)));
        listToAdd.forEach(s -> assertThat(dao.delete(s.getId()),is(true)));

        assertThat( dao.findAll(), containsInAnyOrder(listOfExistBeforeTest.toArray()));
    }

    @Test
    public void getById() throws Exception {
        assertThat(dao.getById(0) == null, is(true));
        assertThat(dao.getById(-1) == null, is(true));
    }
}
