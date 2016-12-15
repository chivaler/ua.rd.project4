package ua.rd.project4.model.services.impl;

import ua.rd.project4.model.dao.EntityDao;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.ClientService;
import ua.rd.project4.model.exceptions.EntityInUseException;

class JdbcClientService extends GenericEntityService<Client> implements ClientService {
    private static final JdbcClientService instance = new JdbcClientService();
    private final String INUSE_INVOICE = "There is invoices for this client. Delete them before deleting client.";
    private final String INUSE_CARREQUEST = "There is CarRequests for this client. Delete them before deleting client.";
    private final String INUSE_USER = "There is User for this client. Delete them before deleting client.";

    private JdbcClientService() {
    }

    public static JdbcClientService getInstance() {
        return instance;
    }

    @Override
    EntityDao<Client> getDao() {
        return JdbcDaoFactory.getInstance().getClientDao();
    }

    @Override
    ServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    @Override
    public boolean delete(int id) throws EntityInUseException {
        if (!(getServiceFactory().getInvoiceService().
                findInvoicesByClientId(id).isEmpty()))
            throw new EntityInUseException(INUSE_INVOICE);
        if (!(getServiceFactory().getCarRequestService().
                findCarRequestsByClientId(id).isEmpty()))
            throw new EntityInUseException(INUSE_CARREQUEST);
        if (!(getServiceFactory().getUserService().
                findUsersByClientId(id).isEmpty()))
            throw new EntityInUseException(INUSE_USER);
        return super.delete(id);
    }
}
