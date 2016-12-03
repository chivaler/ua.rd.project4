package ua.rd.project4.domain;

import java.util.Objects;

public class User implements Entity {
    private int id = -1;
    private boolean isAdmin;
    private String login;
    private String passwordHash;
    private Client client;

    public User(boolean isAdmin, String login, String passwordHash, Client client) {
        this.isAdmin = isAdmin;
        this.login = login;
        this.passwordHash = passwordHash;
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User that = (User) o;
        return isAdmin == that.isAdmin &&
                Objects.equals(login, that.login) &&
                Objects.equals(passwordHash, that.passwordHash) &&
                Objects.equals(getClientId(), that.getClientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAdmin, login, passwordHash, client);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getClientId() {
        return (client == null) ? 0 : client.getId();
    }

    @Override
    public String toString() {
        return login + "("+id+")";
    }
}
