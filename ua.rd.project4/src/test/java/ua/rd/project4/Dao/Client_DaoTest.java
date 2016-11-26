package ua.rd.project4.Dao;

import ua.rd.project4.entities.Client;
import org.junit.Before;

public class Client_DaoTest extends EntityDaoTest<Client> {
    @Before
    public void setParams() {
        elem1 = new Client("Igor", "Podgurski", "north", "+380", "igor@email","some Passport");
        elem2 = new Client("Vasya", "Pupkin", "south", "+360", "vasya@email","some ID card");
        elem3 = new Client("Petya", "Is", "west", "+32260", "veta@email","some ID card");
        setClass(Client.class);
        dao.createTable();
    }

}
