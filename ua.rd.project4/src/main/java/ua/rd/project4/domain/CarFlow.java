package ua.rd.project4.domain;

import java.util.Objects;


public class CarFlow implements Entity {
    private int id;
    private Car car;
    private CarFlowType carFlowType;
    private Client client;
    private CarRequest carRequest;
    private SystemUser responsiblePerson;
    private Invoice invoice;
    private String supplement;

    public enum CarFlowType {
        IN(1), OUT(-1);
        final int value;

        CarFlowType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static CarFlowType getTypeByValue(Integer value) {
            switch (value) {
                case 1:
                    return IN;
                case -1:
                    return OUT;
                case 0:
                    return null;
//                case null:
//                    return null;
                default:
                    throw new IllegalArgumentException("Expected 1,-1: Recieved"+value);
            }
        }
    }

    public CarFlow(Car car, CarFlowType carFlowType, Client client, CarRequest carRequest, SystemUser responsiblePerson, Invoice invoice, String supplement) {
        this.car = car;
        this.carFlowType = carFlowType;
        this.client = client;
        this.carRequest = carRequest;
        this.responsiblePerson = responsiblePerson;
        this.invoice = invoice;
        this.supplement = supplement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarFlow carFlow = (CarFlow) o;
        return Objects.equals(car, carFlow.car) &&
                carFlowType == carFlow.carFlowType &&
                Objects.equals(client, carFlow.client) &&
                Objects.equals(carRequest, carFlow.carRequest) &&
                Objects.equals(responsiblePerson, carFlow.responsiblePerson) &&
                Objects.equals(invoice, carFlow.invoice) &&
                Objects.equals(supplement, carFlow.supplement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(car, carFlowType, client, carRequest, responsiblePerson, invoice, supplement);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public CarFlowType getCarFlowType() {
        return carFlowType;
    }

    public void setCarFlowType(CarFlowType carFlowType) {
        this.carFlowType = carFlowType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public CarRequest getCarRequest() {
        return carRequest;
    }

    public void setCarRequest(CarRequest carRequest) {
        this.carRequest = carRequest;
    }

    public SystemUser getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(SystemUser responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public String getSupplement() {
        return supplement;
    }

    public void setSupplement(String supplement) {
        this.supplement = supplement;
    }
}
