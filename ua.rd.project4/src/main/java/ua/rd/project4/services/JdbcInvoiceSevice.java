package ua.rd.project4.services;

import ua.rd.project4.Dao.EntityDao;
import ua.rd.project4.Dao.JdbcDaoFactory;
import ua.rd.project4.entities.Car;
import ua.rd.project4.entities.Invoice;

class JdbcInvoiceSevice extends InvoiceService {
    private static final JdbcInvoiceSevice instance = new JdbcInvoiceSevice();
    private JdbcInvoiceSevice() {
    }

    public static JdbcInvoiceSevice getInstance() {
        return instance;
    }

    @Override
    EntityDao<Invoice> getDao() {
        return JdbcDaoFactory.getInstance().getInvoiceDao();
    }

    @Override
    public boolean delete(int id) throws ExceptionEntityInUse {
        return super.delete(id);
    }
}
