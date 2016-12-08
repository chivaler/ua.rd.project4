package ua.rd.project4.model;

import ua.rd.project4.domain.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

public class RandomEntities {
    static Random random = new Random();

    public static Car getCar() {
        return new Car(new BigInteger(64, random).toString(32),
                new BigInteger(64, random).toString(32),
                Car.CarType.values()[random.nextInt(Car.CarType.values().length)],
                new BigInteger(64, random).toString(32),
                new BigInteger(64, random).toString(32),
                new BigDecimal(new BigInteger(24, random)),
                new BigDecimal(new BigInteger(24, random))
                );
    }

    public static User getUser() {
        return new User(random.nextBoolean(),
                new BigInteger(64, random).toString(32),
                new BigInteger(64, random).toString(32),
                null);
    }

    public static Client getClient() {
        return new Client(
                new BigInteger(64, random).toString(32),
                new BigInteger(64, random).toString(32),
                new BigInteger(64, random).toString(32),
                new BigInteger(64, random).toString(32),
                new BigInteger(64, random).toString(32),
                new BigInteger(64, random).toString(32)
        );
    }

    public static Invoice getInvoice() {
        return new Invoice(
                null,
                new BigDecimal(new BigInteger(24, random)),
                random.nextBoolean(),
                new BigInteger(64, random).toString(32)
        );
    }

    public static CarFlow getCarFlow() {
        return new CarFlow(
                null, CarFlow.CarFlowType.values()[random.nextInt(CarFlow.CarFlowType.values().length)],
                null, null, null, new BigInteger(64, random).toString(32));

    }

    public static CarRequest getCarRequest() {
        return new CarRequest(
                null,
                null,
                Date.valueOf(LocalDate.now().minusDays(random.nextInt(30))),
                Date.valueOf(LocalDate.now().plusDays(random.nextInt(30))),
                new BigDecimal(new BigInteger(24, random)),
                CarRequest.RequestStatus.values()[random.nextInt(CarRequest.RequestStatus.values().length)],
                null,
                new BigInteger(64, random).toString(32));
    }
}

