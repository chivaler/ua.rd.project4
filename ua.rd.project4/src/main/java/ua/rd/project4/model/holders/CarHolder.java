package ua.rd.project4.model.holders;

import ua.rd.project4.domain.Car;

import java.math.BigDecimal;

public class CarHolder extends Car   {
    public CarHolder(String model, String color, CarType carType, String registrationNumber, String description, BigDecimal price, BigDecimal rentPricePerDay) {
        super(model, color, carType, registrationNumber, description, price, rentPricePerDay);
    }
}
