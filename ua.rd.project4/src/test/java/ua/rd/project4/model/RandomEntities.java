package ua.rd.project4.model;

import ua.rd.project4.domain.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

public class RandomEntities {
    static Random random = new Random();

    public static Car getCar() {
        return null;
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
}

