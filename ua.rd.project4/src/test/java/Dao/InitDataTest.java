package Dao;

import org.junit.Before;
import org.junit.Test;
import ua.rd.project4.Dao.*;
import ua.rd.project4.entities.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class InitDataTest {
    @Test
    public void initData() {
        CarDao carDao = JdbcDaoFactory.getInstance().getCarDao();
        ClientDao clientDao = JdbcDaoFactory.getInstance().getClientDao();
        InvoiceDao invoiceDao = JdbcDaoFactory.getInstance().getInvoiceDao();
        CarFlowDao carFlowDao = JdbcDaoFactory.getInstance().getCarFlowDao();
        carFlowDao.createTable();

        Car car1 = new Car("Mersedes", "black", Car.CarType.SEDAN, "FFJJ", "heavy.", 17000, 300);
        Car car2 = new Car("BENZ", "white", Car.CarType.MINI, "JJKK", "light.", 16000, 250);
        Car car3 = new Car("Zapor", "white", Car.CarType.SPORT, "1212", "dsdasd", 1234, 30);

        carDao.insert(car1);
        car1.setId(carDao.findId(car1));
        carDao.insert(car2);
        car2.setId(carDao.findId(car2));
        carDao.insert(car3);
        car3.setId(carDao.findId(car3));
        assertThat(car1.getId()==car2.getId(),is(false));
        assertThat(car3.getId()==car2.getId(),is(false));
        assertThat(car1.getId()==car3.getId(),is(false));

        Client client1 = new Client("Gor", "Podgurski", "north", "+380", "igor@email","some Passport");
        Client client2 = new Client("Asya", "Pupkin", "south", "+360", "vasya@email","some ID card");
        Client client3 = new Client("Etya", "Is", "west", "+32260", "veta@email","some ID card");

        clientDao.insert(client1);
        client1.setId(clientDao.findId(client1));
        clientDao.insert(client2);
        client2.setId(clientDao.findId(client2));
        clientDao.insert(client3);
        client3.setId(clientDao.findId(client3));
        assertThat(client1.getId()==client2.getId(),is(false));
        assertThat(client3.getId()==client2.getId(),is(false));
        assertThat(client1.getId()==client3.getId(),is(false));

        Invoice invoice1 = new Invoice(client1, 0, true, "Bla");
        Invoice invoice2 = new Invoice(client1, 0, false, "Bla");;
        Invoice invoice3 = new Invoice(client3, 2, true, "Bla");

        invoiceDao.insert(invoice1);
        invoice1.setId(invoiceDao.findId(invoice1));
        invoiceDao.insert(invoice2);
        invoice2.setId(invoiceDao.findId(invoice2));
        invoiceDao.insert(invoice3);
        invoice3.setId(invoiceDao.findId(invoice3));
        assertThat(invoice1.getId()==invoice2.getId(),is(false));
        assertThat(invoice3.getId()==invoice2.getId(),is(false));
        assertThat(invoice1.getId()==invoice3.getId(),is(false));

        SystemUser user1 = new SystemUser(true,"admin","admin",null);
        SystemUser user2 = new SystemUser(false,"user1","",client1);
        SystemUser user3 = new SystemUser(false,"user2","",client2);
        UserDao userDao = JdbcDaoFactory.getInstance().getUserDao();
        userDao.insert(user1);
        user1.setId(userDao.findId(user1));
        userDao.insert(user2);
        user2.setId(userDao.findId(user2));
        userDao.insert(user3);
        user3.setId(userDao.findId(user3));
        assertThat(user1.getId()==user2.getId(),is(false));
        assertThat(user3.getId()==user2.getId(),is(false));
        assertThat(user1.getId()==user3.getId(),is(false));

        CarRequest carRequest1 = new CarRequest(car1, client1, java.sql.Date.valueOf("2016-11-25"), java.sql.Date.valueOf("2016-11-26"), 0, false, invoice3);
        CarRequest carRequest2 = new CarRequest(car1, client2, java.sql.Date.valueOf("2016-11-26"), java.sql.Date.valueOf("2016-11-27"), 0, false, null);
        CarRequest carRequest3 = new CarRequest(car3, client3, java.sql.Date.valueOf("2016-11-27"), java.sql.Date.valueOf("2016-11-28"), 0, false, invoice2);
        CarRequestDao carRequestDao = JdbcDaoFactory.getInstance().getCarRequestDao();
        carRequestDao.insert(carRequest1);
        carRequest1.setId(carRequestDao.findId(carRequest1));
        carRequestDao.insert(carRequest2);
        carRequest2.setId(carRequestDao.findId(carRequest2));
        carRequestDao.insert(carRequest3);
        carRequest3.setId(carRequestDao.findId(carRequest3));
        assertThat(carRequest1.getId()==carRequest2.getId(),is(false));
        assertThat(carRequest3.getId()==carRequest2.getId(),is(false));
        assertThat(carRequest1.getId()==carRequest3.getId(),is(false));

        CarFlow carFlow1 = new CarFlow(car1, CarFlow.CarFlowType.OUT, client1, carRequest1, user1, null, "xyz");
        CarFlow carFlow2= new CarFlow(car2, CarFlow.CarFlowType.OUT, client2, carRequest2, user1, invoice1, "bolbol");
        CarFlow carFlow3 = new CarFlow(car1, CarFlow.CarFlowType.IN, client1, carRequest1, user1, invoice2, "x");

        carFlowDao.insert(carFlow1);
        carFlow1.setId(carFlowDao.findId(carFlow1));
        carFlowDao.insert(carFlow2);
        carFlow2.setId(carFlowDao.findId(carFlow2));
        carFlowDao.insert(carFlow3);
        carFlow3.setId(carFlowDao.findId(carFlow3));
        assertThat(carFlow1.getId()==carFlow2.getId(),is(false));
        assertThat(carFlow3.getId()==carFlow2.getId(),is(false));
        assertThat(carFlow1.getId()==carFlow3.getId(),is(false));
    }
}
