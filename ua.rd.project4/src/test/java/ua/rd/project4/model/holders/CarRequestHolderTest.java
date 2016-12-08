package ua.rd.project4.model.holders;

import org.junit.Test;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.RandomEntities;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CarRequestHolderTest {
    ServiceFactory serviceFactory = JdbcServiceFactory.getInstance();

    @Test
    public void equalsTest() throws Exception {
        CarRequest carRequest = RandomEntities.getCarRequest();
        CarRequestHolder carRequestHolder = new CarRequestHolder(0, 0, carRequest.getDateFrom(), carRequest.getDateTo(),
                carRequest.getTotalCost(), carRequest.getStatus(), 0, carRequest.getRejectReason(), JdbcDaoFactory.getInstance());

        assertThat(carRequest.equals(carRequestHolder), is(true));
        assertThat(carRequestHolder.equals(carRequest), is(true));
    }

    @Test
    public void insertAndGetBlankTest() throws Exception {
        CarRequest carRequest = RandomEntities.getCarRequest();
        serviceFactory.getCarRequestService().insert(carRequest);
        CarRequest carRequestDao = serviceFactory.getCarRequestService().getById(carRequest.getId());

        assertThat(carRequestDao instanceof CarRequestHolder, is(true));
        assertThat(carRequestDao.equals(carRequest), is(true));
        assertThat(carRequest.equals(carRequestDao), is(true));
    }

    @Test
    public void insertAndGetTest() throws Exception {
        Car car1 = RandomEntities.getCar();
        serviceFactory.getCarService().insert(car1);
        Client client1 = RandomEntities.getClient();
        serviceFactory.getClientService().insert(client1);
        Invoice invoice1 = RandomEntities.getInvoice();
        invoice1.setClient(client1);
        serviceFactory.getInvoiceService().insert(invoice1);

        CarRequest carRequest = RandomEntities.getCarRequest();
        carRequest.setClient(client1);
        carRequest.setCar(car1);
        carRequest.setInvoice(invoice1);
        serviceFactory.getCarRequestService().insert(carRequest);
        CarRequest carRequestDao = serviceFactory.getCarRequestService().getById(carRequest.getId());

        assertThat(carRequestDao instanceof CarRequestHolder, is(true));
        assertThat(carRequestDao.equals(carRequest), is(true));
        assertThat(carRequest.equals(carRequestDao), is(true));
    }

    @Test
    public void getEntities() throws Exception {
        CarRequest carRequest = RandomEntities.getCarRequest();
        CarRequestHolder carRequestHolder = new CarRequestHolder(0, 0, carRequest.getDateFrom(), carRequest.getDateTo(),
                carRequest.getTotalCost(), carRequest.getStatus(), 0, carRequest.getRejectReason(), JdbcDaoFactory.getInstance());

        assertThat(carRequestHolder.getClientId(), is(0));
        assertThat(carRequestHolder.getClient() == null, is(true));
        assertThat(carRequestHolder.getCarId(), is(0));
        assertThat(carRequestHolder.getCar() == null, is(true));
        assertThat(carRequestHolder.getInvoiceId(), is(0));
        assertThat(carRequestHolder.getInvoice() == null, is(true));
    }

    @Test
    public void setEntity() throws Exception {
        Car car1 = RandomEntities.getCar();
        serviceFactory.getCarService().insert(car1);
        Client client1 = RandomEntities.getClient();
        serviceFactory.getClientService().insert(client1);
        Invoice invoice1 = RandomEntities.getInvoice();
        invoice1.setClient(client1);
        serviceFactory.getInvoiceService().insert(invoice1);

        CarRequest carRequest = RandomEntities.getCarRequest();
        CarRequestHolder carRequestHolder = new CarRequestHolder(0, 0, carRequest.getDateFrom(), carRequest.getDateTo(),
                carRequest.getTotalCost(), carRequest.getStatus(), 0, carRequest.getRejectReason(), JdbcDaoFactory.getInstance());

        carRequestHolder.setClient(client1);
        carRequestHolder.setCar(car1);
        carRequestHolder.setInvoice(invoice1);

        assertThat(carRequestHolder.getClientId(), is(client1.getId()));
        assertThat(carRequestHolder.getClient(), is(client1));
        assertThat(carRequestHolder.getCarId(), is(car1.getId()));
        assertThat(carRequestHolder.getCar(), is(car1));
        assertThat(carRequestHolder.getInvoiceId(), is(invoice1.getId()));
        assertThat(carRequestHolder.getInvoice(), is(invoice1));
    }
}