package ua.rd.project4.model.dao;

import org.junit.BeforeClass;
import ua.rd.project4.domain.Invoice;

public class Invoice_DaoTest extends EntityDaoTest<Invoice> {
    @BeforeClass
    public static void setParams() {
        JdbcDaoFactory.getInstance().getInvoiceDao().createTable();
    }

    @Override
    Invoice initElem1() {
        return new Invoice(null, 0, true, "Bla");
    }

    @Override
    Invoice initElem2() {
        return new Invoice(null, 0, false, "Bla");
    }

    @Override
    Invoice initElem3() {
        return new Invoice(null, 2, true, "Bla");
    }

    @Override
    EntityDao<Invoice> getDao() {
        return JdbcDaoFactory.getInstance().getInvoiceDao();
    }
}
