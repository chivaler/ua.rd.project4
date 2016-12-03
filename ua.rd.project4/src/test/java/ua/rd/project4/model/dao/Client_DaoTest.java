package ua.rd.project4.model.dao;

import org.junit.BeforeClass;
import ua.rd.project4.domain.Client;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;

public class Client_DaoTest extends EntityDaoTest<Client> {
    @BeforeClass
    public static void setParams() {
        JdbcDaoFactory.getInstance().getClientDao().createTableIfNotExist();
    }

    @Override
    Client initElem1() {
        return new Client("Igor", "Podgurski", "north", "+380", "igor@email","some Passport");
    }

    @Override
    Client initElem2() {
        return new Client("Vasya", "Pupkin", "south", "+360", "vasya@email","some ID card");
    }

    @Override
    Client initElem3() {
        return new Client("Petya", "Is", "west", "+32260", "veta@email","some ID card");
    }

    @Override
    EntityDao<Client> getDao() {
        return  JdbcDaoFactory.getInstance().getClientDao();
    }


}
