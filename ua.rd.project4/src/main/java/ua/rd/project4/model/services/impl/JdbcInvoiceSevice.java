package ua.rd.project4.model.services.impl;

import ua.rd.project4.model.dao.InvoiceDao;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.domain.Invoice;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.exceptions.EntityInUseException;
import ua.rd.project4.model.services.InvoiceService;

import java.util.List;

class JdbcInvoiceSevice extends GenericEntityService<Invoice> implements InvoiceService {
    private static final JdbcInvoiceSevice instance = new JdbcInvoiceSevice();

    private JdbcInvoiceSevice() {
    }

    public static JdbcInvoiceSevice getInstance() {
        return instance;
    }

    @Override
    InvoiceDao getDao() {
        return JdbcDaoFactory.getInstance().getInvoiceDao();
    }

    @Override
    ServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    @Override
    public boolean delete(int id) throws EntityInUseException {
        return super.delete(id);
    }

    @Override
    public List<Invoice> findInvoicesByClientId(int idClient) {
        return getDao().findInvoicesByClientId(idClient);
    }
}
