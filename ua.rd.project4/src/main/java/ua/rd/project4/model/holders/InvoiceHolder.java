package ua.rd.project4.model.holders;

import ua.rd.project4.domain.Client;
import ua.rd.project4.domain.Invoice;
import ua.rd.project4.model.dao.DaoFactory;

import java.math.BigDecimal;

public class InvoiceHolder extends Invoice {
    private final DaoFactory daoFactory;
    private int clientId;

    @Override
    public int getClientId() {
        return clientId;
    }

    public InvoiceHolder(int clientId, BigDecimal total, boolean paid, String description, DaoFactory daoFactory) {
        super(null, total, paid, description);
        this.daoFactory = daoFactory;
        this.clientId = clientId;
    }

    @Override
    public Client getClient() {
        return (clientId > 0) ? daoFactory.getClientDao().getById(clientId) : null;
    }

    @Override
    public void setClient(Client client) {
        this.clientId = (client == null) ? 0 : client.getId();
    }

}
