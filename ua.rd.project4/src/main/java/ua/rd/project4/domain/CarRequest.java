package ua.rd.project4.domain;

import java.sql.Date;
import java.util.Objects;

public class CarRequest implements Entity {
    private int id = -1;
    private Car car;
    private Client client;
    private Date DateFrom;
    private Date DateTo;
    private int totalCost;
    private boolean approved;
    private Invoice invoice;

    public CarRequest(Car car, Client client, Date dateFrom, Date dateTo, int totalCost, boolean approved, Invoice invoice) {
        this.car = car;
        this.client = client;
        DateFrom = dateFrom;
        DateTo = dateTo;
        this.totalCost = totalCost;
        this.approved = approved;
        this.invoice = invoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarRequest that = (CarRequest) o;
        return totalCost == that.totalCost &&
                approved == that.approved &&
                Objects.equals(car, that.car) &&
                Objects.equals(client, that.client) &&
                Objects.equals(DateFrom, that.DateFrom) &&
                Objects.equals(DateTo, that.DateTo) &&
                Objects.equals(invoice, that.invoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(car, client, DateFrom, DateTo, totalCost, approved, invoice);
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDateFrom() {
        return DateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        DateFrom = dateFrom;
    }

    public Date getDateTo() {
        return DateTo;
    }

    public void setDateTo(Date dateTo) {
        DateTo = dateTo;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
