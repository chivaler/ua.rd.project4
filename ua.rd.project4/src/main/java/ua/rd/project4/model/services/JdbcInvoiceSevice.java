package ua.rd.project4.model.services;

import ua.rd.project4.model.dao.InvoiceDao;
import ua.rd.project4.model.dao.JdbcDaoFactory;
import ua.rd.project4.domain.Invoice;

import java.util.List;

class JdbcInvoiceSevice extends InvoiceService {
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
    AbstractServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    @Override
    public boolean delete(int id) throws ExceptionEntityInUse {
        return super.delete(id);
    }

    @Override
    public List<Invoice> findInvoicesByClientId(int idClient) {
        return getDao().findInvoicesByClientId(idClient);
    }
}
