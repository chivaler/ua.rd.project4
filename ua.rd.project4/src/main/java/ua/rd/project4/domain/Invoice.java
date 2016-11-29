package ua.rd.project4.domain;

import java.util.Objects;

public class Invoice implements Entity {
    private int id;
    private Client client;
    private int total;
    private boolean paid;
    private String description;

    public Invoice(Client client, int total, boolean paid, String description) {
        this.client = client;
        this.total = total;
        this.paid = paid;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return total == invoice.total &&
                paid == invoice.paid &&
                Objects.equals(client, invoice.client) &&
                Objects.equals(description, invoice.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, total, paid, description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
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

    @Override
    public String toString() {
        return "Invoice_" + id;
    }
}
