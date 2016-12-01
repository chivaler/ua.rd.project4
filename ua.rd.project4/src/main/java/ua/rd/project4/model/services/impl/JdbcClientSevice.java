package ua.rd.project4.model.services.impl;

import ua.rd.project4.model.dao.EntityDao;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.ClientService;
import ua.rd.project4.model.exceptions.ExceptionEntityInUse;

class JdbcClientSevice extends AbstractEntityService<Client> implements ClientService {
    private static final JdbcClientSevice instance = new JdbcClientSevice();

    private JdbcClientSevice() {
    }

    public static JdbcClientSevice getInstance() {
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
        if (!(getServiceFactory().getCarFlowService().
                findCarFlowsByClientId(id).isEmpty()))
            throw new ExceptionEntityInUse("There is CarFlows for this client. Delete them before deleting client.");
        if (!(getServiceFactory().getCarRequestService().
                findCarRequestsByClientId(id).isEmpty()))
            throw new ExceptionEntityInUse("There is CarRequests for this client. Delete them before deleting client.");
        if (!(getServiceFactory().getUserService().
                findUsersByClientId(id).isEmpty()))
            throw new ExceptionEntityInUse("There is User for this client. Delete them before deleting client.");
        return super.delete(id);
    }
}
