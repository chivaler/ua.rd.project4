package ua.rd.project4.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Car implements Entity {
    private int id = -1;
    private String model;
    private String color;
    private CarType carType;
    private String registrationNumber;
    private String description;
    private BigDecimal price;
    private BigDecimal rentPricePerDay;

    public enum CarType {
        PICKUP, SEDAN, SPORT, LIMO, MINI
    }

    public Car(String model, String color, CarType carType, String registrationNumber, String description, BigDecimal price, BigDecimal rentPricePerDay) {
        this.model = model;
        this.color = color;
        this.carType = carType;
        this.registrationNumber = registrationNumber;
        this.description = description;
        this.price = price;
        this.rentPricePerDay = rentPricePerDay;
    }

    @Override
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

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getRentPricePerDay() {
        return rentPricePerDay;
    }

    public void setRentPricePerDay(BigDecimal rentPricePerDay) {
        this.rentPricePerDay = rentPricePerDay;
    }

    @Override
    public void setId(int id) {
        this.id = id;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Car))
            return false;
        Car car = (Car) o;
        return Objects.equals(model, car.model) &&
                Objects.equals(color, car.color) &&
                carType == car.carType &&
                Objects.equals(registrationNumber, car.registrationNumber) &&
                Objects.equals(description, car.description) &&
                (price == null ? car.price == null : car.price != null && price.compareTo(car.price) == 0) &&
                (rentPricePerDay == null ? car.rentPricePerDay == null :
                        car.rentPricePerDay != null && rentPricePerDay.compareTo(car.rentPricePerDay) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, color, carType, registrationNumber, description);
    }


    @Override
    public String toString() {
        return model + ", " + color + " (" + id + ")";
    }
}
