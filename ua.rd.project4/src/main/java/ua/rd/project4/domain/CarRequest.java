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

    public CarRequest(Car car,
                      Client client,
                      Date dateFrom,
                      Date dateTo,
                      BigDecimal totalCost,
                      RequestStatus status,
                      Invoice invoice,
                      String rejectReason) {
        this.car = car;
        this.client = client;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.totalCost = totalCost;
        this.status = status;
        this.invoice = invoice;
        this.rejectReason = rejectReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarRequest)) return false;
        CarRequest that = (CarRequest) o;
        return Objects.equals(getCarId(), that.getCarId()) &&
                Objects.equals(getClientId(), that.getClientId()) &&
                Objects.equals(getDateFrom(), that.getDateFrom()) &&
                Objects.equals(getDateTo(), that.getDateTo()) &&
                (totalCost == null ? that.totalCost == null : that.totalCost != null
                        && totalCost.compareTo(that.totalCost) == 0) &&
                getStatus() == that.getStatus() &&
                Objects.equals(getInvoiceId(), that.getInvoiceId()) &&
                Objects.equals(getRejectReason(), that.getRejectReason());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCar(), getClient(), getDateFrom(), getDateTo(), getStatus(), getInvoice(), getRejectReason());
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
