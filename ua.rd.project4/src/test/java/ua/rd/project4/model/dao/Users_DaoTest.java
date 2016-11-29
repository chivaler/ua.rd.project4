package ua.rd.project4.model.dao;

import ua.rd.project4.domain.SystemUser;
import org.junit.Before;

public class Users_DaoTest extends EntityDaoTest<SystemUser> {
    @Before
    public void setParams() {
        elem1 = new SystemUser(true,"admin","bla",null);
        elem2 = new SystemUser(true,"user1","",null);
        elem3 = new SystemUser(false,"user1","",null);
        setClass(SystemUser.class);
        dao.createTable();
    }
}
