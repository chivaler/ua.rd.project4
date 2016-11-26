package ua.rd.project4.Dao;

import ua.rd.project4.entities.Invoice;
import org.junit.Before;

public class Invoice_DaoTest extends EntityDaoTest<Invoice> {
    @Before
    public void setParams() {
        elem1 = new Invoice(null, 0, true, "Bla");
        elem2 = new Invoice(null, 0, false, "Bla");
        elem3 = new Invoice(null, 2, true, "Bla");
        setClass(Invoice.class);
        dao.createTable();
    }

}
