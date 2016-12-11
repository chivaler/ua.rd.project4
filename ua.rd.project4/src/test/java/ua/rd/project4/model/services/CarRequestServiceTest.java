package ua.rd.project4.model.services;

import org.junit.Test;
import ua.rd.project4.domain.Car;
import ua.rd.project4.domain.CarRequest;
import ua.rd.project4.domain.Client;
import ua.rd.project4.domain.Invoice;
import ua.rd.project4.model.RandomEntities;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Car car1 = RandomEntities.getCar();
        serviceFactory.getCarService().insert(car1);
        Car car2 = RandomEntities.getCar();
        serviceFactory.getCarService().insert(car2);
        Client client1 = RandomEntities.getClient();
        serviceFactory.getClientService().insert(client1);

        CarRequest carRequest1 =  RandomEntities.getCarRequest();
        carRequest1.setCar(car1);
        carRequest1.setClient(client1);
        carRequest1.setDateFrom(Date.valueOf(LocalDate.now()));
        carRequest1.setDateTo(Date.valueOf(LocalDate.now()));
        carRequest1.setStatus(CarRequest.RequestStatus.NEW);
        serviceFactory.getCarRequestService().insert(carRequest1);
        assertThat(serviceFactory.getCarRequestService().isPossible(carRequest1.getId()),is(CarRequestService.CarRequestStatus.POSSIBLE));

        CarRequest carRequest2 =  RandomEntities.getCarRequest();
        carRequest2.setCar(car1);
        carRequest2.setClient(client1);
        carRequest2.setStatus(CarRequest.RequestStatus.NEW);
        carRequest2.setDateFrom(Date.valueOf(LocalDate.now()));
        carRequest2.setDateTo(Date.valueOf(LocalDate.now().plusDays(1)));
        serviceFactory.getCarRequestService().insert(carRequest2);
        assertThat(serviceFactory.getCarRequestService().isPossible(carRequest2.getId()),is(CarRequestService.CarRequestStatus.CONFLICT));
        assertThat(serviceFactory.getCarRequestService().isPossible(carRequest1.getId()),is(CarRequestService.CarRequestStatus.CONFLICT));

        carRequest1.setStatus(CarRequest.RequestStatus.APPROVED);
        serviceFactory.getCarRequestService().update(carRequest1.getId(), carRequest1);
        assertThat(serviceFactory.getCarRequestService().isPossible(carRequest2.getId()),is(CarRequestService.CarRequestStatus.IMPOSSIBLE));

        carRequest2.setDateFrom(Date.valueOf(LocalDate.now().minusDays(2)));
        carRequest2.setDateTo(Date.valueOf(LocalDate.now()));
        serviceFactory.getCarRequestService().update(carRequest2.getId(), carRequest2);
        assertThat(serviceFactory.getCarRequestService().isPossible(carRequest2.getId()),is(CarRequestService.CarRequestStatus.IMPOSSIBLE));

        carRequest2.setDateFrom(Date.valueOf(LocalDate.now().minusDays(2)));
        carRequest2.setDateTo(Date.valueOf(LocalDate.now().plusDays(2)));
        serviceFactory.getCarRequestService().update(carRequest2.getId(), carRequest2);
        assertThat(serviceFactory.getCarRequestService().isPossible(carRequest2.getId()),is(CarRequestService.CarRequestStatus.IMPOSSIBLE));

        carRequest1.setDateFrom(Date.valueOf(LocalDate.now().minusDays(1)));
        carRequest1.setDateTo(Date.valueOf(LocalDate.now().plusDays(1)));
        serviceFactory.getCarRequestService().update(carRequest1.getId(), carRequest1);
        assertThat(serviceFactory.getCarRequestService().isPossible(carRequest2.getId()),is(CarRequestService.CarRequestStatus.IMPOSSIBLE));

        carRequest2.setDateFrom(Date.valueOf(LocalDate.now().minusDays(2)));
        carRequest2.setDateTo(Date.valueOf(LocalDate.now()));
        serviceFactory.getCarRequestService().update(carRequest2.getId(), carRequest2);
        assertThat(serviceFactory.getCarRequestService().isPossible(carRequest2.getId()),is(CarRequestService.CarRequestStatus.IMPOSSIBLE));

        carRequest2.setDateFrom(Date.valueOf(LocalDate.now()));
        carRequest2.setDateTo(Date.valueOf(LocalDate.now().plusDays(2)));
        serviceFactory.getCarRequestService().update(carRequest2.getId(), carRequest2);
        assertThat(serviceFactory.getCarRequestService().isPossible(carRequest2.getId()),is(CarRequestService.CarRequestStatus.IMPOSSIBLE));

        carRequest2.setDateFrom(Date.valueOf(LocalDate.now()));
        carRequest2.setDateTo(Date.valueOf(LocalDate.now()));
        serviceFactory.getCarRequestService().update(carRequest2.getId(), carRequest2);
        assertThat(serviceFactory.getCarRequestService().isPossible(carRequest2.getId()),is(CarRequestService.CarRequestStatus.IMPOSSIBLE));

        carRequest2.setCar(car2);
        serviceFactory.getCarRequestService().update(carRequest2.getId(), carRequest2);
        assertThat(serviceFactory.getCarRequestService().isPossible(carRequest2.getId()),is(CarRequestService.CarRequestStatus.POSSIBLE));
    }

    //Business Logic

//    @Test
    public void getCarRequestsWithStatusesTest() throws Exception {
        Client client1 = RandomEntities.getClient();
        serviceFactory.getClientService().insert(client1);
        CarRequest carRequest1 =  RandomEntities.getCarRequest();
        carRequest1.setClient(client1);
        carRequest1.setStatus(CarRequest.RequestStatus.NEW);
        serviceFactory.getCarRequestService().insert(carRequest1);
         List<Map<String, String>> carRequestsWithStatuses = serviceFactory.getCarRequestService().getCarRequestsWithStatuses();
        assertThat(carRequestsWithStatuses.size()>=1,is(true));
        boolean found = false;
        for (Map<String, String> map : carRequestsWithStatuses)
            if (String.valueOf(carRequest1.getId()).equals(map.get("id")))
                found = true;
        assertThat(found==true, is(true));
    }

    @Test
    public void approve() throws Exception {

    }

    @Test
    public void reject() throws Exception {

    }

    @Test
    public void calculateTotal1() throws Exception {
        Car car1 = RandomEntities.getCar();
        car1.setRentPricePerDay(new BigDecimal(1));
        CarRequest carRequest1 =  RandomEntities.getCarRequest();
        carRequest1.setCar(car1);
        carRequest1.setDateFrom(Date.valueOf(LocalDate.now()));
        carRequest1.setDateTo(Date.valueOf(LocalDate.now()));
        assertThat(serviceFactory.getCarRequestService().calculateTotal(carRequest1)
                .compareTo(new BigDecimal(1)),is(0));
    }

    @Test
    public void calculateTotal2() throws Exception {
        Car car1 = RandomEntities.getCar();
        car1.setRentPricePerDay(new BigDecimal(2.5));
        CarRequest carRequest1 =  RandomEntities.getCarRequest();
        carRequest1.setCar(car1);
        carRequest1.setDateFrom(Date.valueOf(LocalDate.now().minusDays(7)));
        carRequest1.setDateTo(Date.valueOf(LocalDate.now()));
        assertThat(serviceFactory.getCarRequestService().calculateTotal(carRequest1)
                .compareTo(new BigDecimal(20)),is(0));
    }

    @Test
    public void calculateTotal3() throws Exception {
        Car car1 = RandomEntities.getCar();
        car1.setRentPricePerDay(new BigDecimal(0));
        CarRequest carRequest1 =  RandomEntities.getCarRequest();
        carRequest1.setCar(car1);
        carRequest1.setDateFrom(Date.valueOf(LocalDate.now()));
        carRequest1.setDateTo(Date.valueOf(LocalDate.now().plusDays(15)));
        assertThat(serviceFactory.getCarRequestService().calculateTotal(carRequest1)
                .compareTo(new BigDecimal(0)),is(0));
    }


    @Test
    public void checkInCarOut() throws Exception {

    }

}