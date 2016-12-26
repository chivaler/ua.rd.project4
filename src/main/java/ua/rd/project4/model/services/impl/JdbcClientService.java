package ua.rd.project4.model.services.impl;

import ua.rd.project4.model.dao.EntityDao;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.services.*;
import ua.rd.project4.model.exceptions.EntityInUseException;

class JdbcClientService extends GenericEntityService<Client> implements ClientService {
    private static final JdbcClientService instance = new JdbcClientService();
    private final InvoiceService invoiceService = JdbcInvoiceSevice.getInstance();
    private final CarRequestService carRequestService = JdbcCarRequestService.getInstance();
    private final UserService userService = JdbcUserSevice.getInstance();


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
        if (!(invoiceService.findInvoicesByClientId(id).isEmpty()))
            throw new EntityInUseException(Messages.INUSE_INVOICE);
        if (!(carRequestService.findCarRequestsByClientId(id).isEmpty()))
            throw new EntityInUseException(Messages.INUSE_CARREQUEST);
        if (!(userService.findUsersByClientId(id).isEmpty()))
            throw new EntityInUseException(Messages.INUSE_USER);
        return super.delete(id);
    }
}
