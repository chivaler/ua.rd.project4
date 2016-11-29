package ua.rd.project4.domain;

import java.util.Objects;

public class SystemUser implements Entity {
    private int id = -1;
    private boolean isAdmin;
    private String login;
    private String password;
    private Client client;

    public SystemUser(boolean isAdmin, String login, String password, Client client) {
        this.isAdmin = isAdmin;
        this.login = login;
        this.password = password;
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SystemUser that = (SystemUser) o;
        return isAdmin == that.isAdmin &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password) &&
                Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAdmin, login, password, client);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
