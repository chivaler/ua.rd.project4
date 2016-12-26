package ua.rd.project4.model.services.impl;

import ua.rd.project4.model.dao.EntityDao;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.services.*;
import ua.rd.project4.model.exceptions.EntityInUseException;

class DefaultClientService extends GenericEntityService<Client> implements ClientService {
    private static final DefaultClientService instance = new DefaultClientService();
    private final InvoiceService invoiceService = DefaultInvoiceSevice.getInstance();
    private final CarRequestService carRequestService = DefaultCarRequestService.getInstance();
    private final UserService userService = DefaultUserSevice.getInstance();


    private DefaultClientService() {
    }

    public static DefaultClientService getInstance() {
        return instance;
    }

    @Override
    EntityDao<Client> getDao() {
        return JdbcDaoFactory.getInstance().getClientDao();
    }

    @Override
    ServiceFactory getServiceFactory() {
        return DefaultServiceFactory.getInstance();
    }

    @Override
    public boolean delete(int id) throws EntityInUseException {
        if (!(invoiceService.findInvoicesByClientId(id).isEmpty()))
            throw new EntityInUseException(Messages.INUSE_INVOICE);
        if (!(carRequestService.findCarRequestsByClientId(id).isEmpty()))
            throw new EntityInUseException(Messages.INUSE_CARREQUEST);
        if (!(userService.findUsersByClientId(id).isEmpty()))
            throw new EntityInUseException(Messages.INUSE_USER);
        return super.delete(id);
    }
}
