package Dao;

import ua.rd.project4.model.dao.*;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class InitDataTest {

    public void initData() {
        CarDao carDao = JdbcDaoFactory.getInstance().getCarDao();
        ClientDao clientDao = JdbcDaoFactory.getInstance().getClientDao();
        InvoiceDao invoiceDao = JdbcDaoFactory.getInstance().getInvoiceDao();
        CarFlowDao carFlowDao = JdbcDaoFactory.getInstance().getCarFlowDao();
        carFlowDao.createTableIfNotExist();

        Car car1 = new Car("Mersedes", "black", Car.CarType.SEDAN, "FFJJ", "heavy.", new BigDecimal(17000), new BigDecimal(300));
        Car car2 = new Car("BENZ", "white", Car.CarType.MINI, "JJKK", "light.", new BigDecimal(16000), new BigDecimal(250));
        Car car3 = new Car("Zapor", "white", Car.CarType.SPORT, "1212", "dsdasd", new BigDecimal(1245), new BigDecimal(75));

        carDao.insert(car1);
        carDao.insert(car2);
        carDao.insert(car3);
        assertThat(car1.getId()==car2.getId(),is(false));
        assertThat(car3.getId()==car2.getId(),is(false));
        assertThat(car1.getId()==car3.getId(),is(false));

        Client client1 = new Client("Gor", "Podgurski", "north", "+380", "igor@email","some Passport");
        Client client2 = new Client("Asya", "Pupkin", "south", "+360", "vasya@email","some ID card");
        Client client3 = new Client("Etya", "Is", "west", "+32260", "veta@email","some ID card");

        clientDao.insert(client1);
        clientDao.insert(client2);
        clientDao.insert(client3);
        assertThat(client1.getId()==client2.getId(),is(false));
        assertThat(client3.getId()==client2.getId(),is(false));
        assertThat(client1.getId()==client3.getId(),is(false));

        Invoice invoice1 = new Invoice(client1, new BigDecimal(0), true, "Bla");
        Invoice invoice2 = new Invoice(client1,  new BigDecimal(0), false, "Bla");
        Invoice invoice3 = new Invoice(client3,  new BigDecimal(2), true, "Bla");

        invoiceDao.insert(invoice1);
        invoiceDao.insert(invoice2);
        invoiceDao.insert(invoice3);
        assertThat(invoice1.getId()==invoice2.getId(),is(false));
        assertThat(invoice3.getId()==invoice2.getId(),is(false));
        assertThat(invoice1.getId()==invoice3.getId(),is(false));

        User user1 = new User(true,"admin","admin",null);
        User user2 = new User(false,"user1","",client1);
        User user3 = new User(false,"user2","",client2);
        UserDao userDao = JdbcDaoFactory.getInstance().getUserDao();
        userDao.insert(user1);
        userDao.insert(user2);
        userDao.insert(user3);
        assertThat(user1.getId()==user2.getId(),is(false));
        assertThat(user3.getId()==user2.getId(),is(false));
        assertThat(user1.getId()==user3.getId(),is(false));

        CarRequest carRequest1 = new CarRequest(car1, client1, java.sql.Date.valueOf("2016-11-25"), java.sql.Date.valueOf("2016-11-26"), new BigDecimal(0), CarRequest.RequestStatus.APPROVED, invoice3,"");
        CarRequest carRequest2 = new CarRequest(car1, client2, java.sql.Date.valueOf("2016-11-26"), java.sql.Date.valueOf("2016-11-27"), new BigDecimal(0), CarRequest.RequestStatus.NEW, null, "");
        CarRequest carRequest3 = new CarRequest(car3, client3, java.sql.Date.valueOf("2016-11-27"), java.sql.Date.valueOf("2016-11-28"), new BigDecimal(0), CarRequest.RequestStatus.DONE, invoice2, "");
        CarRequestDao carRequestDao = JdbcDaoFactory.getInstance().getCarRequestDao();
        carRequestDao.insert(carRequest1);
        carRequestDao.insert(carRequest2);
        carRequestDao.insert(carRequest3);
        assertThat(carRequest1.getId()==carRequest2.getId(),is(false));
        assertThat(carRequest3.getId()==carRequest2.getId(),is(false));
        assertThat(carRequest1.getId()==carRequest3.getId(),is(false));

        CarFlow carFlow1 = new CarFlow(car1, CarFlow.CarFlowType.OUT,  carRequest1, user1, null, "xyz");
        CarFlow carFlow2= new CarFlow(car2, CarFlow.CarFlowType.OUT,  carRequest2, user1, invoice1, "bolbol");
        CarFlow carFlow3 = new CarFlow(car1, CarFlow.CarFlowType.IN,  carRequest1, user1, invoice2, "x");

        carFlowDao.insert(carFlow1);
        carFlowDao.insert(carFlow2);
        carFlowDao.insert(carFlow3);
        assertThat(carFlow1.getId()==carFlow2.getId(),is(false));
        assertThat(carFlow3.getId()==carFlow2.getId(),is(false));
        assertThat(carFlow1.getId()==carFlow3.getId(),is(false));
    }
}
