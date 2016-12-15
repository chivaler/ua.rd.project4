package ua.rd.project4.model.services;

import org.junit.Test;
import ua.rd.project4.domain.*;
import ua.rd.project4.RandomEntities;
import ua.rd.project4.model.exceptions.CarRequestPaymentNeededException;
import ua.rd.project4.model.exceptions.ConflictsRequestException;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static ua.rd.project4.domain.CarFlow.CarFlowType.IN;
import static ua.rd.project4.domain.CarFlow.CarFlowType.OUT;
import static ua.rd.project4.domain.CarRequest.RequestStatus.APPROVED;
import static ua.rd.project4.domain.CarRequest.RequestStatus.PROGRESS;
import static ua.rd.project4.domain.CarRequest.RequestStatus.REJECTED;

public class CarRequestServiceTest {
    private final InvoiceService invoiceService = JdbcServiceFactory.getInstance().getInvoiceService();
    private final ClientService clientService = JdbcServiceFactory.getInstance().getClientService();
    private final CarFlowService carFlowService = JdbcServiceFactory.getInstance().getCarFlowService();
    private final CarService carService = JdbcServiceFactory.getInstance().getCarService();
    private final CarRequestService carRequestService = JdbcServiceFactory.getInstance().getCarRequestService();
    private final UserService userService = JdbcServiceFactory.getInstance().getUserService();

    @Test
    public void findCarRequestsByClientId_Blank() throws Exception {
        Client client1 = RandomEntities.getClient();
        clientService.insert(client1);
        assertThat(carRequestService.findCarRequestsByClientId(client1.getId()).isEmpty(), is(true));
        assertThat(carRequestService.findCarRequestsByClientId(-1).isEmpty(), is(true));
        assertThat(carRequestService.findCarRequestsByClientId(0).isEmpty(), is(true));
    }

    @Test
    public void findCarRequestsByClientId() throws Exception {
        Car car1 = RandomEntities.getCar();
        carService.insert(car1);
        Client client1 = RandomEntities.getClient();
        Client client2 = RandomEntities.getClient();
        clientService.insert(client1);
        clientService.insert(client2);
        Invoice invoice1 = RandomEntities.getInvoice();
        invoice1.setClient(client1);
        invoiceService.insert(invoice1);

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

        assertThat(carRequestService.findCarRequestsByClientId(client1.getId()), is(Stream.of(carRequest1, carRequest2).collect(Collectors.toList())));
    }

    @Test
    public void findCarRequestsByCarId_blank() throws Exception {
        Car car1 = RandomEntities.getCar();
        carService.insert(car1);
        assertThat(carRequestService.findCarRequestsByCarId(car1.getId()).isEmpty(), is(true));
        assertThat(carRequestService.findCarRequestsByCarId(-1).isEmpty(), is(true));
        assertThat(carRequestService.findCarRequestsByCarId(0).isEmpty(), is(true));
    }

    @Test
    public void findCarRequestsByCarId() throws Exception {
        Car car1 = RandomEntities.getCar();
        carService.insert(car1);
        Car car2 = RandomEntities.getCar();
        carService.insert(car2);
        Client client1 = RandomEntities.getClient();
        clientService.insert(client1);
        Invoice invoice1 = RandomEntities.getInvoice();
        invoice1.setClient(client1);
        invoiceService.insert(invoice1);

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

        assertThat(carRequestService.findCarRequestsByCarId(car1.getId()), is(Stream.of(carRequest1, carRequest3).collect(Collectors.toList())));

    }

    @Test
    public void findCarRequestsByInvoiceId_blank() throws Exception {
        Invoice invoice1 = RandomEntities.getInvoice();
        invoiceService.insert(invoice1);
        assertThat(carRequestService.findCarRequestsByInvoiceId(invoice1.getId()).isEmpty(), is(true));
        assertThat(carRequestService.findCarRequestsByInvoiceId(-1).isEmpty(), is(true));
        assertThat(carRequestService.findCarRequestsByInvoiceId(0).isEmpty(), is(true));
    }

    @Test
    public void findCarRequestsByInvoiceId() throws Exception {
        Car car1 = RandomEntities.getCar();
        carService.insert(car1);
        Client client1 = RandomEntities.getClient();
        clientService.insert(client1);
        Invoice invoice1 = RandomEntities.getInvoice();
        invoice1.setClient(client1);
        invoiceService.insert(invoice1);
        Invoice invoice2 = RandomEntities.getInvoice();
        invoice2.setClient(client1);
        invoiceService.insert(invoice2);

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

        assertThat(carRequestService.findCarRequestsByInvoiceId(invoice1.getId()), is(Stream.of(carRequest1, carRequest3).collect(Collectors.toList())));

    }

    @Test
    public void isPossible() throws Exception {
        Car car1 = RandomEntities.getCar();
        carService.insert(car1);
        Car car2 = RandomEntities.getCar();
        carService.insert(car2);
        Client client1 = RandomEntities.getClient();
        clientService.insert(client1);

        CarRequest carRequest1 = RandomEntities.getCarRequest();
        carRequest1.setCar(car1);
        carRequest1.setClient(client1);
        carRequest1.setDateFrom(Date.valueOf(LocalDate.now()));
        carRequest1.setDateTo(Date.valueOf(LocalDate.now()));
        carRequest1.setStatus(CarRequest.RequestStatus.NEW);
        carRequestService.insert(carRequest1);
        assertThat(carRequestService.isPossible(carRequest1.getId()), is(CarRequestService.CarRequestStatus.POSSIBLE));

        CarRequest carRequest2 = RandomEntities.getCarRequest();
        carRequest2.setCar(car1);
        carRequest2.setClient(client1);
        carRequest2.setStatus(CarRequest.RequestStatus.NEW);
        carRequest2.setDateFrom(Date.valueOf(LocalDate.now()));
        carRequest2.setDateTo(Date.valueOf(LocalDate.now().plusDays(1)));
        carRequestService.insert(carRequest2);
        assertThat(carRequestService.isPossible(carRequest2.getId()), is(CarRequestService.CarRequestStatus.CONFLICT));
        assertThat(carRequestService.isPossible(carRequest1.getId()), is(CarRequestService.CarRequestStatus.CONFLICT));

        carRequest1.setStatus(APPROVED);
        carRequestService.update(carRequest1.getId(), carRequest1);
        assertThat(carRequestService.isPossible(carRequest2.getId()), is(CarRequestService.CarRequestStatus.IMPOSSIBLE));

        carRequest2.setDateFrom(Date.valueOf(LocalDate.now().minusDays(2)));
        carRequest2.setDateTo(Date.valueOf(LocalDate.now()));
        carRequestService.update(carRequest2.getId(), carRequest2);
        assertThat(carRequestService.isPossible(carRequest2.getId()), is(CarRequestService.CarRequestStatus.IMPOSSIBLE));

        carRequest2.setDateFrom(Date.valueOf(LocalDate.now().minusDays(2)));
        carRequest2.setDateTo(Date.valueOf(LocalDate.now().plusDays(2)));
        carRequestService.update(carRequest2.getId(), carRequest2);
        assertThat(carRequestService.isPossible(carRequest2.getId()), is(CarRequestService.CarRequestStatus.IMPOSSIBLE));

        carRequest1.setDateFrom(Date.valueOf(LocalDate.now().minusDays(1)));
        carRequest1.setDateTo(Date.valueOf(LocalDate.now().plusDays(1)));
        carRequestService.update(carRequest1.getId(), carRequest1);
        assertThat(carRequestService.isPossible(carRequest2.getId()), is(CarRequestService.CarRequestStatus.IMPOSSIBLE));

        carRequest2.setDateFrom(Date.valueOf(LocalDate.now().minusDays(2)));
        carRequest2.setDateTo(Date.valueOf(LocalDate.now()));
        carRequestService.update(carRequest2.getId(), carRequest2);
        assertThat(carRequestService.isPossible(carRequest2.getId()), is(CarRequestService.CarRequestStatus.IMPOSSIBLE));

        carRequest2.setDateFrom(Date.valueOf(LocalDate.now()));
        carRequest2.setDateTo(Date.valueOf(LocalDate.now().plusDays(2)));
        carRequestService.update(carRequest2.getId(), carRequest2);
        assertThat(carRequestService.isPossible(carRequest2.getId()), is(CarRequestService.CarRequestStatus.IMPOSSIBLE));

        carRequest2.setDateFrom(Date.valueOf(LocalDate.now()));
        carRequest2.setDateTo(Date.valueOf(LocalDate.now()));
        carRequestService.update(carRequest2.getId(), carRequest2);
        assertThat(carRequestService.isPossible(carRequest2.getId()), is(CarRequestService.CarRequestStatus.IMPOSSIBLE));

        carRequest2.setCar(car2);
        carRequestService.update(carRequest2.getId(), carRequest2);
        assertThat(carRequestService.isPossible(carRequest2.getId()), is(CarRequestService.CarRequestStatus.POSSIBLE));
    }

    //Business Logic

    //    @Test
    public void getCarRequestsWithStatusesTest() throws Exception {
        Client client1 = RandomEntities.getClient();
        clientService.insert(client1);
        CarRequest carRequest1 = RandomEntities.getCarRequest();
        carRequest1.setClient(client1);
        carRequest1.setStatus(CarRequest.RequestStatus.NEW);
        carRequestService.insert(carRequest1);
        List<Map<String, String>> carRequestsWithStatuses = carRequestService.getCarRequestsWithStatuses();
        assertThat(carRequestsWithStatuses.size() >= 1, is(true));
        boolean found = false;
        for (Map<String, String> map : carRequestsWithStatuses)
            if (String.valueOf(carRequest1.getId()).equals(map.get("id")))
                found = true;
        assertThat(found == true, is(true));
    }

    @Test
    public void approve() throws Exception {
        Car car1 = RandomEntities.getCar();
        carService.insert(car1);
        Client client = RandomEntities.getClient();
        clientService.insert(client);
        CarRequest carRequest1 = new CarRequest(car1, client, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), new BigDecimal(0), CarRequest.RequestStatus.NEW, null, "");
        carRequestService.insert(carRequest1);
        assertNull(carRequestService.getById(carRequest1.getId()).getInvoice());
        carRequestService.approve(carRequest1.getId());
        assertThat(carRequestService.getById(carRequest1.getId()).getStatus(), is(APPROVED));
        assertNotNull(carRequestService.getById(carRequest1.getId()).getInvoice());
        assertThat(carRequestService.getById(carRequest1.getId()).getInvoice().isPaid(), is(false));
    }

    @Test(expected = ConflictsRequestException.class)
    public void approveImpossible() throws Exception {
        Car car1 = RandomEntities.getCar();
        carService.insert(car1);
        Client client1 = RandomEntities.getClient();
        clientService.insert(client1);
        Client client2 = RandomEntities.getClient();
        clientService.insert(client2);
        CarRequest carRequest1 = new CarRequest(car1, client1, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), new BigDecimal(0), CarRequest.RequestStatus.NEW, null, "");
        CarRequest carRequest2 = new CarRequest(car1, client2, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(1)), new BigDecimal(15), CarRequest.RequestStatus.NEW, null, "");
        carRequestService.insert(carRequest1);
        carRequestService.insert(carRequest2);
        carRequestService.approve(carRequest1.getId());
        carRequestService.approve(carRequest2.getId());
    }


    @Test
    public void rejectAfterApprove() throws Exception {
        Car car1 = RandomEntities.getCar();
        carService.insert(car1);
        Client client = RandomEntities.getClient();
        clientService.insert(client);
        CarRequest carRequest1 = new CarRequest(car1, client, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), new BigDecimal(0), CarRequest.RequestStatus.NEW, null, "");
        carRequestService.insert(carRequest1);
        carRequestService.approve(carRequest1.getId());
        assertThat(carRequestService.getById(carRequest1.getId()).getStatus(), is(APPROVED));
        carRequestService.reject(carRequest1.getId(), "Some reason");
        assertThat(carRequestService.getById(carRequest1.getId()).getStatus(), is(REJECTED));
    }

    @Test
    public void reject() throws Exception {
        Car car1 = RandomEntities.getCar();
        carService.insert(car1);
        Client client = RandomEntities.getClient();
        clientService.insert(client);
        CarRequest carRequest1 = new CarRequest(car1, client, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), new BigDecimal(0), CarRequest.RequestStatus.NEW, null, "");
        carRequestService.insert(carRequest1);
        carRequestService.approve(carRequest1.getId());
        carRequestService.reject(carRequest1.getId(), "Some reason");
        assertThat(carRequestService.getById(carRequest1.getId()).getStatus(), is(REJECTED));
    }

    @Test
    public void calculateTotal1() throws Exception {
        Car car1 = RandomEntities.getCar();
        car1.setRentPricePerDay(new BigDecimal(1));
        CarRequest carRequest1 = RandomEntities.getCarRequest();
        carRequest1.setCar(car1);
        carRequest1.setDateFrom(Date.valueOf(LocalDate.now()));
        carRequest1.setDateTo(Date.valueOf(LocalDate.now()));
        assertThat(carRequestService.calculateTotal(carRequest1)
                .compareTo(new BigDecimal(1)), is(0));
    }

    @Test
    public void calculateTotal2() throws Exception {
        Car car1 = RandomEntities.getCar();
        car1.setRentPricePerDay(new BigDecimal(2.5));
        CarRequest carRequest1 = RandomEntities.getCarRequest();
        carRequest1.setCar(car1);
        carRequest1.setDateFrom(Date.valueOf(LocalDate.now().minusDays(7)));
        carRequest1.setDateTo(Date.valueOf(LocalDate.now()));
        assertThat(carRequestService.calculateTotal(carRequest1)
                .compareTo(new BigDecimal(20)), is(0));
    }

    @Test
    public void calculateTotal3() throws Exception {
        Car car1 = RandomEntities.getCar();
        car1.setRentPricePerDay(new BigDecimal(0));
        CarRequest carRequest1 = RandomEntities.getCarRequest();
        carRequest1.setCar(car1);
        carRequest1.setDateFrom(Date.valueOf(LocalDate.now()));
        carRequest1.setDateTo(Date.valueOf(LocalDate.now().plusDays(15)));
        assertThat(carRequestService.calculateTotal(carRequest1)
                .compareTo(new BigDecimal(0)), is(0));
    }


    @Test(expected = CarRequestPaymentNeededException.class)
    public void checkInCarOutWithoutPayment() throws Exception {
        User user = RandomEntities.getUser();
        user.setAdmin(true);
        Car car1 = RandomEntities.getCar();
        Client client = RandomEntities.getClient();
        userService.insert(user);
        carService.insert(car1);
        clientService.insert(client);

        CarRequest carRequest1 = new CarRequest(car1, client, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), new BigDecimal(0), CarRequest.RequestStatus.NEW, null, "");
        carRequestService.insert(carRequest1);
        carRequestService.approve(carRequest1.getId());
        carRequestService.checkInCarOut(carRequest1.getId(), user);
    }

    @Test
    public void checkInCarOut() throws Exception {
        User user = RandomEntities.getUser();
        user.setAdmin(true);
        Car car1 = RandomEntities.getCar();
        Client client = RandomEntities.getClient();
        userService.insert(user);
        carService.insert(car1);
        clientService.insert(client);

        CarRequest carRequest1 = new CarRequest(car1, client, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), new BigDecimal(0), CarRequest.RequestStatus.NEW, null, "");
        carRequestService.insert(carRequest1);
        carRequestService.approve(carRequest1.getId());
        Invoice invoice = carRequestService.getById(carRequest1.getId()).getInvoice();
        invoice.setPaid(true);
        invoiceService.update(invoice.getId(), invoice);

        carRequestService.checkInCarOut(carRequest1.getId(), user);
        assertThat(carFlowService.findCarFlowsByCarId(car1.getId()).isEmpty(), is(true));

        CarFlow carFlow1 = new CarFlow(car1, IN, null, user, null, "arrive");
        carFlowService.insert(carFlow1);
        assertThat(carFlowService.isCarInBox(car1.getId()), is(true));
        carRequestService.checkInCarOut(carRequest1.getId(), user);

        CarFlow carFlow = carFlowService.findCarFlowsByCarId(car1.getId()).stream().filter(s -> s.getCarRequestId() == carRequest1.getId()).findAny().get();
        assertThat(carFlow.getCarFlowType(), is(OUT));

        assertThat(carFlow.getDateCreated().getTime() - Timestamp.valueOf(LocalDateTime.now()).getTime() < 2000, is(true));
        assertThat(carFlowService.isCarInBox(car1.getId()), is(false));
        assertThat(carRequestService.getById(carRequest1.getId()).getStatus(), is(PROGRESS));
    }

}