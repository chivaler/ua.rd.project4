package ua.rd.project4.model.services.impl;

import ua.rd.project4.model.dao.InvoiceDao;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.domain.Invoice;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.InvoiceService;

import java.util.List;

class DefaultInvoiceSevice extends GenericEntityService<Invoice> implements InvoiceService {
    private static final DefaultInvoiceSevice instance = new DefaultInvoiceSevice();

    private DefaultInvoiceSevice() {
    }

    public static DefaultInvoiceSevice getInstance() {
        return instance;
    }

    @Override
    InvoiceDao getDao() {
        return JdbcDaoFactory.getInstance().getInvoiceDao();
    }

    @Override
    ServiceFactory getServiceFactory() {
        return DefaultServiceFactory.getInstance();
    }

    @Override
    public List<Invoice> findInvoicesByClientId(int idClient) {
        return getDao().findInvoicesByClientId(idClient);
    }
}
