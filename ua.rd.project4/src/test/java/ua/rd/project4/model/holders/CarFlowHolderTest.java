package ua.rd.project4.model.holders;

import org.junit.Test;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.RandomEntities;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CarFlowHolderTest {
    ServiceFactory serviceFactory = JdbcServiceFactory.getInstance();

    @Test
    public void equalsTest() throws Exception {
        CarFlow carFlow1 = RandomEntities.getCarFlow();
        CarFlowHolder carFlowHolder = new CarFlowHolder(0, carFlow1.getCarFlowType(), 0, 0, 0, carFlow1.getSupplement(), JdbcDaoFactory.getInstance());
        assertThat(carFlow1.equals(carFlowHolder), is(true));
        assertThat(carFlowHolder.equals(carFlow1), is(true));
    }

    @Test
    public void insertAndGetBlankTest() throws Exception {
        CarFlow carFlow1 = RandomEntities.getCarFlow();
        serviceFactory.getCarFlowService().insert(carFlow1);
        CarFlow carFlow2 = serviceFactory.getCarFlowService().getById(carFlow1.getId());

        assertThat(carFlow2 instanceof CarFlowHolder, is(true));
        assertThat(carFlow2.equals(carFlow1), is(true));
        assertThat(carFlow1.equals(carFlow2), is(true));
    }

    @Test
    public void insertAndGetTest() throws Exception {
        Car car1 = RandomEntities.getCar();
        serviceFactory.getCarService().insert(car1);
        User user1 = RandomEntities.getUser();
        serviceFactory.getUserService().insert(user1);
        Client client1 = RandomEntities.getClient();
        serviceFactory.getClientService().insert(client1);
        Invoice invoice1 = RandomEntities.getInvoice();
        invoice1.setClient(client1);
        serviceFactory.getInvoiceService().insert(invoice1);
        CarRequest carRequest1 = RandomEntities.getCarRequest();
        carRequest1.setClient(client1);
        carRequest1.setCar(car1);
        serviceFactory.getCarRequestService().insert(carRequest1);

        CarFlow carFlow1 = RandomEntities.getCarFlow();
        carFlow1.setCar(car1);
        carFlow1.setInvoice(invoice1);
        carFlow1.setResponsiblePerson(user1);
        carFlow1.setCarRequest(carRequest1);
        serviceFactory.getCarFlowService().insert(carFlow1);
        CarFlow carFlow2 = serviceFactory.getCarFlowService().getById(carFlow1.getId());

        assertThat(carFlow2 instanceof CarFlowHolder, is(true));
        assertThat(carFlow2.equals(carFlow1), is(true));
        assertThat(carFlow1.equals(carFlow2), is(true));
    }

    @Test
    public void getEntities() throws Exception {
        CarFlow carFlow1 = RandomEntities.getCarFlow();
        CarFlowHolder carFlowHolder = new CarFlowHolder(0, carFlow1.getCarFlowType(), 0, 0, 0, carFlow1.getSupplement(), JdbcDaoFactory.getInstance());
        assertThat(carFlowHolder.getCarRequestId(), is(0));
        assertThat(carFlowHolder.getCarRequest() == null, is(true));
        assertThat(carFlowHolder.getResponsiblePersonId(), is(0));
        assertThat(carFlowHolder.getResponsiblePerson() == null, is(true));
        assertThat(carFlowHolder.getInvoiceId(), is(0));
        assertThat(carFlowHolder.getInvoice() == null, is(true));
    }

    @Test
    public void setEntity() throws Exception {
        Car car1 = RandomEntities.getCar();
        serviceFactory.getCarService().insert(car1);
        User user1 = RandomEntities.getUser();
        serviceFactory.getUserService().insert(user1);
        Client client1 = RandomEntities.getClient();
        serviceFactory.getClientService().insert(client1);
        Invoice invoice1 = RandomEntities.getInvoice();
        invoice1.setClient(client1);
        serviceFactory.getInvoiceService().insert(invoice1);
        CarRequest carRequest1 = RandomEntities.getCarRequest();
        carRequest1.setClient(client1);
        carRequest1.setCar(car1);
        serviceFactory.getCarRequestService().insert(carRequest1);

        CarFlow carFlow1 = RandomEntities.getCarFlow();
        CarFlow carFlowHolder = new CarFlowHolder(0, carFlow1.getCarFlowType(), 0, 0, 0, carFlow1.getSupplement(), JdbcDaoFactory.getInstance());
        carFlowHolder.setCar(car1);
        carFlowHolder.setInvoice(invoice1);
        carFlowHolder.setResponsiblePerson(user1);
        carFlowHolder.setCarRequest(carRequest1);

        assertThat(carFlowHolder.getCarRequestId(),is(carRequest1.getId()));
        assertThat(carFlowHolder.getCarRequest(),is(carRequest1));
        assertThat(carFlowHolder.getResponsiblePersonId(),is(user1.getId()));
        assertThat(carFlowHolder.getResponsiblePerson(),is(user1));
        assertThat(carFlowHolder.getCarId(),is(car1.getId()));
        assertThat(carFlowHolder.getCar(),is(car1));
        assertThat(carFlowHolder.getInvoiceId(),is(invoice1.getId()));
        assertThat(carFlowHolder.getInvoice(),is(invoice1));
    }
}