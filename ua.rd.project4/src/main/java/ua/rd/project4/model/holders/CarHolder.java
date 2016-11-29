package ua.rd.project4.model.holders;

import ua.rd.project4.domain.Car;

public class CarHolder extends Car implements Holder<Car>  {
    public CarHolder(String model, String color, CarType carType, String registrationNumber, String description, int price, int rentPricePerDay) {
        super(model, color, carType, registrationNumber, description, price, rentPricePerDay);
    }
}
