package ua.rd.project4.domain;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class CarRequest implements Entity {
    private int id = -1;
    private Car car;
    private Client client;
    private Date dateFrom;
    private Date dateTo;
    private BigDecimal totalCost;
    private RequestStatus status;
    private Invoice invoice;
    private String rejectReason;
    private Timestamp dateCreated;

    public enum RequestStatus {
        NEW, APPROVED, REJECTED, DONE;
    }

    public CarRequest(Car car, Client client, Date dateFrom, Date dateTo, BigDecimal totalCost, RequestStatus status, Invoice invoice) {
        this.car = car;
        this.client = client;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.totalCost = totalCost;
        this.status = status;
        this.invoice = invoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarRequest that = (CarRequest) o;
        return totalCost == that.totalCost &&
//                approved == that.approved &&
                Objects.equals(car, that.car) &&
                Objects.equals(client, that.client) &&
                Objects.equals(dateFrom, that.dateFrom) &&
                Objects.equals(dateTo, that.dateTo) &&
                Objects.equals(invoice, that.invoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(car, client, dateFrom, dateTo, totalCost, status, invoice);
    }
    @Override
    public int getId() {
        return id;
    }
    @Override
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
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isApproved() {
        return status == RequestStatus.APPROVED;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public int getCarId() {
        return (car == null) ? 0 : car.getId();
    }

    public int getClientId() {
        return (client == null) ? 0 : client.getId();
    }

    public int getInvoiceId() {
        return (invoice == null) ? 0 : invoice.getId();
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "CarRequest_" + id;
    }
}
