package ua.rd.project4.Dao;

import ua.rd.project4.entities.Entity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public abstract class EntityDaoTest<T extends Entity> {
    EntityDao<T> dao;
    T elem1;
    T elem2;
    T elem3;

    public void setClass(Class c) {
        dao = JdbcDaoFactory.getInstance().getGenericDao(c);
    }

    @Test
    public void createTable() throws Exception {
        dao.createTable();
    }

    @Test
    public void insert_delete() throws Exception {
        int prevCount = dao.findAll().size();
        assertThat(dao.insert(elem1), is(true));
        assertThat(dao.findAll().size(), is(prevCount + 1));
        assertThat(dao.findAll().get(0).equals(elem1), is(true));
        assertThat(dao.delete(dao.findAll().get(0).getId()), is(true));
        assertThat(dao.findAll().size(), is(prevCount));
        assertThat(dao.delete(1), is(false));
        assertThat(dao.delete(0), is(false));
    }

    @Test
    public void get_update() throws Exception {
        int prevCount = dao.findAll().size();
        assertThat(dao.insert(elem1), is(true));
        int id = dao.findAll().get(0).getId();
        assertThat(dao.update(id, elem2), is(true));
        assertThat(dao.findAll().size(), is(prevCount+1));
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
        List<T> list_exist = dao.findAll();

        List<T> list = new ArrayList<>();
        list.add(elem1);
        list.add(elem2);
        list.add(elem3);

        for (T elem : list)
            dao.insert(elem);
        List<T> list2 = dao.findAll();

        list.addAll(list_exist);

//        assertThat(list2, containsInAnyOrder(list.toArray()));

        for (T sqlElem : list2)
            assertThat(dao.delete(sqlElem.getId()), is(true));

//        assertThat( dao.findAll(), containsInAnyOrder(list_exist.toArray()));
    }

    @Test
    public void getById() throws Exception {
        assertThat(dao.getById(0)==null, is(true));
        assertThat(dao.getById(-1)==null, is(true));
    }
}
