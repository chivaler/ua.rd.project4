package ua.rd.project4.model.dao;

import org.junit.BeforeClass;
import ua.rd.project4.domain.Invoice;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;

import java.math.BigDecimal;

public class Invoice_DaoTest extends EntityDaoTest<Invoice> {
    @Override
    Invoice initElem1() {
        return new Invoice(null, new BigDecimal(0), true, "Bla");
    }

    @Override
    Invoice initElem2() {
        return new Invoice(null,new BigDecimal(0), false, "Bla");
    }

    @Override
    Invoice initElem3() {
        return new Invoice(null,new BigDecimal(2), true, "Bla");
    }

    @Override
    EntityDao<Invoice> getDao() {
        return JdbcDaoFactory.getInstance().getInvoiceDao();
    }
}
