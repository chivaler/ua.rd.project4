package ua.rd.project4.entities;

import java.util.Objects;

/**
 * Car class
 *
 * @author Igor Podgurski
 */
public class Car implements Entity {
    private int id = -1;
    private String model;
    private String color;
    private CarType carType;
    private String registrationNumber;
    private String description;
    private int price;
    private int rentPricePerDay;

    public enum CarType {
        PICKUP, SEDAN, SPORT, LIMO, MINI
    }

    public Car(String model, String color, CarType carType, String registrationNumber, String description, int price, int rentPricePerDay) {
        this.model = model;
        this.color = color;
        this.carType = carType;
        this.registrationNumber = registrationNumber;
        this.description = description;
        this.price = price;
        this.rentPricePerDay = rentPricePerDay;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public CarType getCarType() {
        return carType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getRentPricePerDay() {
        return rentPricePerDay;
    }

    public void setId(int id) {
        this.id = id;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return // id == car.id &&
                price == car.price &&
                        rentPricePerDay == car.rentPricePerDay &&
                        Objects.equals(model, car.model) &&
                        Objects.equals(color, car.color) &&
                        carType == car.carType &&
                        Objects.equals(registrationNumber, car.registrationNumber) &&
                        Objects.equals(description, car.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, color, carType, registrationNumber, description, price, rentPricePerDay);
    }
}
