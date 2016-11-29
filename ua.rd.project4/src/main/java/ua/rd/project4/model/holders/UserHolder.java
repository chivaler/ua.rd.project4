package ua.rd.project4.model.holders;

import ua.rd.project4.domain.Client;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.dao.AbstractDaoFactory;

public class UserHolder extends User implements Holder<User> {
    final AbstractDaoFactory daoFactory;
    private int clientId;

    public UserHolder(boolean isAdmin, String login, String password, int clientId, AbstractDaoFactory daoFactory) {
        super(isAdmin, login, password, null);
        this.daoFactory = daoFactory;
        this.clientId = clientId;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public Client getClient() {
        return (clientId > 0)? daoFactory.getClientDao().getById(clientId) : null;
    }

    @Override
    public void setClient(Client client) {
        this.clientId = (client == null) ? 0 : client.getId();
    }

}
