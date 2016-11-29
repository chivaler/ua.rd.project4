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
                    throw new IllegalArgumentException("Expected 1,-1: Recieved" + value);
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
        if (this == o)
            return true;
        if (!(o instanceof CarFlow))
            return false;

        CarFlow carFlow = (CarFlow) o;

        if (getCarId() != carFlow.getCarId())
            return false;
        if (getCarFlowType() != carFlow.getCarFlowType())
            return false;
        if (getClientId() != carFlow.getClientId())
            return false;
        if (getCarRequestId() != carFlow.getCarRequestId())
            return false;
        if (getResponsiblePersonId() != carFlow.getResponsiblePersonId())
            return false;
        if (getInvoiceId() != carFlow.getInvoiceId())
            return false;
        return getSupplement() != null ? getSupplement().equals(carFlow.getSupplement()) : carFlow.getSupplement() == null;

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getCarId(), getCarFlowType(), getCarRequestId(),
                getResponsiblePersonId(), getInvoiceId(), getSupplement());
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
        return (supplement != null) ? supplement : "";
    }

    public void setSupplement(String supplement) {
        this.supplement = supplement;
    }

    public int getCarId() {
        return (car == null) ? 0 : car.getId();
    }

    public int getClientId() {
        return (client == null) ? 0 : client.getId();
    }

    public int getCarRequestId() {
        return (carRequest == null) ? 0 : carRequest.getId();
    }

    public int getResponsiblePersonId() {
        return (responsiblePerson == null) ? 0 : responsiblePerson.getId();
    }

    public int getInvoiceId() {
        return (invoice == null) ? 0 : invoice.getId();
    }

    @Override
    public String toString() {
        return "Car(" + getCarId() + ")_" + carFlowType + "(" + id + ")";
    }
}
