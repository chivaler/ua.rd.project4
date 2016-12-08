package ua.rd.project4.model.services;

import org.junit.Test;
import ua.rd.project4.domain.Car;
import ua.rd.project4.domain.CarRequest;
import ua.rd.project4.domain.Client;
import ua.rd.project4.domain.Invoice;
import ua.rd.project4.model.RandomEntities;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CarRequestServiceTest {
    ServiceFactory serviceFactory = JdbcServiceFactory.getInstance();
    CarRequestService carRequestService = JdbcServiceFactory.getInstance().getCarRequestService();

    @Test
    public void findCarRequestsByClientId_Blank() throws Exception {
        Client client1 = RandomEntities.getClient();
        serviceFactory.getClientService().insert(client1);
        assertThat(carRequestService.findCarRequestsByClientId(client1.getId()).isEmpty(), is(true));
        assertThat(carRequestService.findCarRequestsByClientId(-1).isEmpty(), is(true));
        assertThat(carRequestService.findCarRequestsByClientId(0).isEmpty(), is(true));
    }

    @Test
    public void findCarRequestsByClientId() throws Exception {
        Car car1 = RandomEntities.getCar();
        serviceFactory.getCarService().insert(car1);
        Client client1 = RandomEntities.getClient();
        Client client2 = RandomEntities.getClient();
        serviceFactory.getClientService().insert(client1);
        serviceFactory.getClientService().insert(client2);
        Invoice invoice1 = RandomEntities.getInvoice();
        invoice1.setClient(client1);
        serviceFactory.getInvoiceService().insert(invoice1);

        CarRequest carRequest1 = RandomEntities.getCarRequest();
        carRequest1.setClient(client1);
        carRequest1.setCar(car1);
        carRequest1.setInvoice(invoice1);
        carRequestService.insert(carRequest1);

        assertThat(carRequestService.findCarRequestsByClientId(client1.getId()), is(Stream.of(carRequest1).collect(Collectors.toList())));

        CarRequest carRequest2 = RandomEntities.getCarRequest();
        carRequest2.setClient(client1);
        carRequest2.setCar(car1);
        carRequest2.setInvoice(invoice1);
        carRequestService.insert(carRequest2);

        CarRequest carRequest3 = RandomEntities.getCarRequest();
        carRequest3.setClient(client2);
        carRequest3.setCar(car1);
        carRequest3.setInvoice(invoice1);
        carRequestService.insert(carRequest3);

        assertThat(carRequestService.findCarRequestsByClientId(client1.getId()), is(Stream.of(carRequest1,carRequest2).collect(Collectors.toList())));
    }

    @Test
    public void findCarRequestsByCarId_blank() throws Exception {
        Car car1 = RandomEntities.getCar();
        serviceFactory.getCarService().insert(car1);
        assertThat(carRequestService.findCarRequestsByCarId(car1.getId()).isEmpty(), is(true));
        assertThat(carRequestService.findCarRequestsByCarId(-1).isEmpty(), is(true));
        assertThat(carRequestService.findCarRequestsByCarId(0).isEmpty(), is(true));
    }

    @Test
    public void findCarRequestsByCarId() throws Exception {
        Car car1 = RandomEntities.getCar();
        serviceFactory.getCarService().insert(car1);
        Car car2 = RandomEntities.getCar();
        serviceFactory.getCarService().insert(car2);
        Client client1 = RandomEntities.getClient();
        serviceFactory.getClientService().insert(client1);
        Invoice invoice1 = RandomEntities.getInvoice();
        invoice1.setClient(client1);
        serviceFactory.getInvoiceService().insert(invoice1);

        CarRequest carRequest1 = RandomEntities.getCarRequest();
        carRequest1.setClient(client1);
        carRequest1.setCar(car1);
        carRequest1.setInvoice(invoice1);
        carRequestService.insert(carRequest1);

        assertThat(carRequestService.findCarRequestsByCarId(car1.getId()), is(Stream.of(carRequest1).collect(Collectors.toList())));

        CarRequest carRequest2 = RandomEntities.getCarRequest();
        carRequest2.setClient(client1);
        carRequest2.setCar(car2);
        carRequest2.setInvoice(invoice1);
        carRequestService.insert(carRequest2);

        CarRequest carRequest3 = RandomEntities.getCarRequest();
        carRequest3.setClient(client1);
        carRequest3.setCar(car1);
        carRequest3.setInvoice(invoice1);
        carRequestService.insert(carRequest3);

        assertThat(carRequestService.findCarRequestsByCarId(car1.getId()), is(Stream.of(carRequest1,carRequest3).collect(Collectors.toList())));

    }

    @Test
    public void findCarRequestsByInvoiceId_blank() throws Exception {
        Invoice invoice1 = RandomEntities.getInvoice();
        serviceFactory.getInvoiceService().insert(invoice1);
        assertThat(carRequestService.findCarRequestsByInvoiceId(invoice1.getId()).isEmpty(), is(true));
        assertThat(carRequestService.findCarRequestsByInvoiceId(-1).isEmpty(), is(true));
        assertThat(carRequestService.findCarRequestsByInvoiceId(0).isEmpty(), is(true));
    }

    @Test
    public void findCarRequestsByInvoiceId() throws Exception {
        Car car1 = RandomEntities.getCar();
        serviceFactory.getCarService().insert(car1);
        Client client1 = RandomEntities.getClient();
        serviceFactory.getClientService().insert(client1);
        Invoice invoice1 = RandomEntities.getInvoice();
        invoice1.setClient(client1);
        serviceFactory.getInvoiceService().insert(invoice1);
        Invoice invoice2 = RandomEntities.getInvoice();
        invoice2.setClient(client1);
        serviceFactory.getInvoiceService().insert(invoice2);

        CarRequest carRequest1 = RandomEntities.getCarRequest();
        carRequest1.setClient(client1);
        carRequest1.setCar(car1);
        carRequest1.setInvoice(invoice1);
        carRequestService.insert(carRequest1);

        assertThat(carRequestService.findCarRequestsByInvoiceId(invoice1.getId()), is(Stream.of(carRequest1).collect(Collectors.toList())));

        CarRequest carRequest2 = RandomEntities.getCarRequest();
        carRequest2.setClient(client1);
        carRequest2.setCar(car1);
        carRequest2.setInvoice(invoice2);
        carRequestService.insert(carRequest2);

        CarRequest carRequest3 = RandomEntities.getCarRequest();
        carRequest3.setClient(client1);
        carRequest3.setCar(car1);
        carRequest3.setInvoice(invoice1);
        carRequestService.insert(carRequest3);

        assertThat(carRequestService.findCarRequestsByInvoiceId(invoice1.getId()), is(Stream.of(carRequest1,carRequest3).collect(Collectors.toList())));

    }

    @Test
    public void isPossible() throws Exception {

    }

    //Business Logic

    @Test
    public void getCarRequestsWithStatuses() throws Exception {

    }

    @Test
    public void approve() throws Exception {

    }

    @Test
    public void reject() throws Exception {

    }

    @Test
    public void calculateTotal() throws Exception {

    }

    @Test
    public void checkInCarOut() throws Exception {

    }

}