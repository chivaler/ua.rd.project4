package ua.rd.project4.services;

import ua.rd.project4.Dao.InvoiceDao;
import ua.rd.project4.Dao.JdbcDaoFactory;
import ua.rd.project4.entities.Invoice;

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
    public boolean delete(int id) throws ExceptionEntityInUse {
        boolean inUse = findInvoicesByClientId(id).size()>0;
        if (inUse)
            throw new ExceptionEntityInUse();
        else
            return super.delete(id);
    }

    @Override
    List<Invoice> findInvoicesByClientId(int idClient) {
        return getDao().findInvoicesByClientId(idClient);
    }
}
