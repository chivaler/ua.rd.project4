package ua.rd.project4.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Invoice implements Entity {
    private int id = -1;
    private Client client;
    private BigDecimal total;
    private boolean paid;
    private String description;
    private Timestamp dateCreated;

    public Invoice(Client client, BigDecimal total, boolean paid, String description) {
        this.client = client;
        this.total = total;
        this.paid = paid;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;
        Invoice that = (Invoice) o;
        return (total == null ? that.total == null : that.total != null && total.compareTo(that.total) == 0) &&
                paid == that.paid &&
                Objects.equals(getClientId(), that.getClientId()) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientId(), paid, description);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getClientId() {
        return (client == null) ? 0 : client.getId();
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Invoice_" + id;
    }
}
