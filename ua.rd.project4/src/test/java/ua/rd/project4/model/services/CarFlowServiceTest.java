package ua.rd.project4.model.services;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.Test;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.RandomEntities;
import ua.rd.project4.model.exceptions.WrongCarFlowDirectionException;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static ua.rd.project4.domain.CarFlow.CarFlowType.IN;
import static ua.rd.project4.domain.CarFlow.CarFlowType.OUT;

public class CarFlowServiceTest {
    private final InvoiceService invoiceService = JdbcServiceFactory.getInstance().getInvoiceService();
    private final ClientService clientService = JdbcServiceFactory.getInstance().getClientService();
    private final CarFlowService carFlowService = JdbcServiceFactory.getInstance().getCarFlowService();
    private final CarService carService = JdbcServiceFactory.getInstance().getCarService();
    private final CarRequestService carRequestService = JdbcServiceFactory.getInstance().getCarRequestService();
    private final UserService userService = JdbcServiceFactory.getInstance().getUserService();

    @Test
    public void findCarFlowsByCarId_Blank() throws Exception {
        Car car = RandomEntities.getCar();
        carService.insert(car);
        assertThat(carFlowService.findCarFlowsByCarId(-1).isEmpty(), is(true));
        assertThat(carFlowService.findCarFlowsByCarId(0).isEmpty(), is(true));
        assertThat(carFlowService.findCarFlowsByCarId(car.getId()).isEmpty(), is(true));
    }

    @Test
    public void findCarFlowsByCarId() throws Exception {
        Car car1 = RandomEntities.getCar();
        Car car2 = RandomEntities.getCar();
        Car car3 = RandomEntities.getCar();
        carService.insert(car1);
        carService.insert(car2);
        carService.insert(car3);

        CarFlow carFlow1 = new CarFlow(car1, CarFlow.CarFlowType.IN, null, null, null, "");
        carFlowService.insert(carFlow1);
        assertThat(carFlowService.findCarFlowsByCarId(car1.getId()), is(Collections.singletonList(carFlow1)));
        CarFlow carFlow2 = new CarFlow(car2, CarFlow.CarFlowType.IN, null, null, null, "");
        carFlowService.insert(carFlow2);
        assertThat(carFlowService.findCarFlowsByCarId(car1.getId()), is(Collections.singletonList(carFlow1)));
        CarFlow carFlow3 = new CarFlow(car1, OUT, null, null, null, "");
        carFlowService.insert(carFlow3);
        assertThat(carFlowService.findCarFlowsByCarId(car1.getId()), is(Stream.of(carFlow1, carFlow3).collect(Collectors.toList())));
    }

    @Test
    public void findCarFlowsByCarRequestId_blank() throws Exception {
        CarRequest carRequest = RandomEntities.getCarRequest();
        carRequestService.insert(carRequest);
        assertThat(carFlowService.findCarFlowsByCarRequestId(-1).isEmpty(), is(true));
        assertThat(carFlowService.findCarFlowsByCarRequestId(0).isEmpty(), is(true));
        assertThat(carFlowService.findCarFlowsByCarRequestId(carRequest.getId()).isEmpty(), is(true));
    }

    @Test
    public void findCarFlowsByCarRequestId() throws Exception {
        Car car1 = RandomEntities.getCar();
        carService.insert(car1);
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

        CarRequest carRequest2 = RandomEntities.getCarRequest();
        carRequest1.setClient(client1);
        carRequest1.setCar(car1);
        carRequest1.setInvoice(invoice1);
        carRequestService.insert(carRequest2);

        CarFlow carFlow1 = new CarFlow(car1, OUT, carRequest1, null, null, "");
        carFlowService.insert(carFlow1);
        assertThat(carFlowService.findCarFlowsByCarRequestId(carRequest1.getId()), is(Stream.of(carFlow1).collect(Collectors.toList())));

        CarFlow carFlow2 = new CarFlow(car1, CarFlow.CarFlowType.IN, carRequest1, null, null, "");
        carFlowService.insert(carFlow2);
        CarFlow carFlow3 = new CarFlow(car1, CarFlow.CarFlowType.IN, carRequest2, null, null, "");
        carFlowService.insert(carFlow3);

        assertThat(carFlowService.findCarFlowsByCarRequestId(carRequest1.getId()), is(Stream.of(carFlow1, carFlow2).collect(Collectors.toList())));
    }

    @Test
    public void findCarFlowsByUserId_blank() throws Exception {
        User user = RandomEntities.getUser();
        userService.insert(user);
        assertThat(carFlowService.findCarFlowsByUserId(-1).isEmpty(), is(true));
        assertThat(carFlowService.findCarFlowsByUserId(0).isEmpty(), is(true));
        assertThat(carFlowService.findCarFlowsByUserId(user.getId()).isEmpty(), is(true));
    }


    @Test
    public void findCarFlowsByUserId() throws Exception {
        User user1 = RandomEntities.getUser();
        userService.insert(user1);
        User user2 = RandomEntities.getUser();
        userService.insert(user2);

        CarFlow carFlow1 = new CarFlow(null, OUT, null, user1, null, "");
        carFlowService.insert(carFlow1);
        assertThat(carFlowService.findCarFlowsByUserId(user1.getId()), is(Stream.of(carFlow1).collect(Collectors.toList())));

        CarFlow carFlow2 = new CarFlow(null, CarFlow.CarFlowType.IN, null, user2, null, "");
        carFlowService.insert(carFlow2);
        CarFlow carFlow3 = new CarFlow(null, CarFlow.CarFlowType.IN, null, user1, null, "");
        carFlowService.insert(carFlow3);
        assertThat(carFlowService.findCarFlowsByUserId(user1.getId()), is(Stream.of(carFlow1, carFlow3).collect(Collectors.toList())));
    }

    @Test
    public void findCarFlowsByInvoiceId_blank() throws Exception {
        Invoice invoice = RandomEntities.getInvoice();
        invoiceService.insert(invoice);
        assertThat(carFlowService.findCarFlowsByInvoiceId(-1).isEmpty(), is(true));
        assertThat(carFlowService.findCarFlowsByInvoiceId(0).isEmpty(), is(true));
        assertThat(carFlowService.findCarFlowsByInvoiceId(invoice.getId()).isEmpty(), is(true));
    }

    @Test
    public void findCarFlowsByInvoiceId() throws Exception {
        Invoice invoice1 = RandomEntities.getInvoice();
        invoiceService.insert(invoice1);
        Invoice invoice2 = RandomEntities.getInvoice();
        invoiceService.insert(invoice2);

        CarFlow carFlow1 = new CarFlow(null, OUT, null, null, invoice1, "");
        carFlowService.insert(carFlow1);
        assertThat(carFlowService.findCarFlowsByInvoiceId(invoice1.getId()), is(Stream.of(carFlow1).collect(Collectors.toList())));

        CarFlow carFlow2 = new CarFlow(null, CarFlow.CarFlowType.IN, null, null, invoice2, "");
        carFlowService.insert(carFlow2);
        CarFlow carFlow3 = new CarFlow(null, CarFlow.CarFlowType.IN, null, null, invoice1, "");
        carFlowService.insert(carFlow3);
        assertThat(carFlowService.findCarFlowsByInvoiceId(invoice1.getId()), is(Stream.of(carFlow1, carFlow3).collect(Collectors.toList())));
    }

    //Business logic

    @Test
    public void checkInCarFlowOut() throws Exception {
        Car car1 = RandomEntities.getCar();
        carService.insert(car1);
        CarFlow carFlowIn = new CarFlow(car1, IN, null, null, null, "to client");
        CarFlow carFlowOut = new CarFlow(car1, OUT, null, null, null, "to client");
        carFlowService.insert(carFlowIn);
        assertTrue(carFlowService.checkInCarFlowOut(carFlowOut));
    }

    @Test(expected = WrongCarFlowDirectionException.class)
    public void checkInCarFlowOutWrong() throws Exception {
        Car car1 = RandomEntities.getCar();
        carService.insert(car1);
        CarFlow carFlowOut = new CarFlow(car1, OUT, null, null, null, "to client");
        carFlowService.checkInCarFlowOut(carFlowOut);
    }

    @Test
    public void checkInCarFlowIn() throws Exception {
        Car car1 = RandomEntities.getCar();
        carService.insert(car1);
        CarFlow carFlowIn = new CarFlow(car1, IN, null, null, null, "to client");
        CarFlow carFlowOut = new CarFlow(car1, OUT, null, null, null, "to client");
        carFlowService.insert(carFlowIn);
        carFlowService.checkInCarFlowOut(carFlowOut);
        carFlowService.checkInCarFlowIn(carFlowOut.getId(), null);
        carFlowService.isCarInBox(car1.getId());
    }

    @Test(expected = WrongCarFlowDirectionException.class)
    public void checkInCarFlowInWrong() throws Exception {
        Car car1 = RandomEntities.getCar();
        carService.insert(car1);
        CarFlow carFlowIn = new CarFlow(car1, IN, null, null, null, "to client");
        CarFlow carFlowOut = new CarFlow(car1, OUT, null, null, null, "to client");
        carFlowService.insert(carFlowIn);
        assertTrue(carFlowService.checkInCarFlowOut(carFlowOut));
        carFlowService.checkInCarFlowIn(carFlowIn.getId(), null);

    }

    @Test
    public void isCarInBox1() throws Exception {
        Car car1 = RandomEntities.getCar();
        carService.insert(car1);
        assertThat(carFlowService.isCarInBox(-1), is(false));
        assertThat(carFlowService.isCarInBox(0), is(false));
        assertThat(carFlowService.isCarInBox(car1.getId()), is(false));
    }

    @Test
    public void isCarInBox2() throws Exception {
        Car car1 = RandomEntities.getCar();
        Car car2 = RandomEntities.getCar();
        Car car3 = RandomEntities.getCar();
        carService.insert(car1);
        carService.insert(car2);
        carService.insert(car3);

        CarFlow carFlow1 = new CarFlow(car1, CarFlow.CarFlowType.IN, null, null, null, "");
        carFlowService.insert(carFlow1);
        assertThat(carFlowService.isCarInBox(car1.getId()), is(true));
        CarFlow carFlow2 = new CarFlow(car2, CarFlow.CarFlowType.IN, null, null, null, "");
        carFlowService.insert(carFlow2);
        assertThat(carFlowService.isCarInBox(car2.getId()), is(true));
        CarFlow carFlow3 = new CarFlow(car1, OUT, null, null, null, "");
        carFlowService.insert(carFlow3);
        assertThat(carFlowService.isCarInBox(car1.getId()), is(false));

        CarFlow carFlow4 = new CarFlow(car3, OUT, null, null, null, "");
        carFlowService.insert(carFlow4);
        assertThat(carFlowService.isCarInBox(car3.getId()), is(false));
    }

}