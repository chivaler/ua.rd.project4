package ua.rd.project4.services;

import ua.rd.project4.Dao.EntityDao;
import ua.rd.project4.Dao.JdbcDaoFactory;
import ua.rd.project4.entities.*;

class JdbcClientSevice extends ClientService {
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
    AbstractServiceFactory getServiceFactory() {
        return ServiceFactory.getInstance();
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
            throw new ExceptionEntityInUse("There is SystemUser for this client. Delete them before deleting client.");
        return super.delete(id);
    }
}
