package ua.rd.project4.domain;

import java.util.Objects;

public class Client implements Entity {
    private int id = -1;
    private String firstName;
    private String lastName;
    private String address;
    private String telephone;
    private String email;
    private String idCard;

    public Client(String firstName, String lastName, String address, String telephone, String email, String idCard) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.idCard = idCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(firstName, client.firstName) &&
                Objects.equals(lastName, client.lastName) &&
                Objects.equals(address, client.address) &&
                Objects.equals(telephone, client.telephone) &&
                Objects.equals(email, client.email) &&
                Objects.equals(idCard, client.idCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, telephone, email, idCard);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Override
    public String toString() {
        return firstName +
                ", " + lastName + " (" + id + ")";
    }
}
