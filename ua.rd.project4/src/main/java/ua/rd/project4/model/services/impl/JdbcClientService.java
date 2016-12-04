package ua.rd.project4.model.services.impl;

import ua.rd.project4.model.dao.EntityDao;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.ClientService;
import ua.rd.project4.model.exceptions.ExceptionEntityInUse;

class JdbcClientService extends AbstractEntityService<Client> implements ClientService {
    private static final JdbcClientService instance = new JdbcClientService();

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
    public boolean delete(int id) throws ExceptionEntityInUse {
        if (!(getServiceFactory().getInvoiceService().
                findInvoicesByClientId(id).isEmpty()))
            throw new ExceptionEntityInUse("There is invoices for this client. Delete them before deleting client.");
        if (!(getServiceFactory().getCarRequestService().
                findCarRequestsByClientId(id).isEmpty()))
            throw new ExceptionEntityInUse("There is CarRequests for this client. Delete them before deleting client.");
        if (!(getServiceFactory().getUserService().
                findUsersByClientId(id).isEmpty()))
            throw new ExceptionEntityInUse("There is User for this client. Delete them before deleting client.");
        return super.delete(id);
    }
}
