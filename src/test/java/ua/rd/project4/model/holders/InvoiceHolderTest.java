package ua.rd.project4.model.holders;

import org.junit.Test;
import ua.rd.project4.domain.*;
import ua.rd.project4.RandomEntities;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InvoiceHolderTest {
    ServiceFactory serviceFactory = DefaultServiceFactory.getInstance();

    @Test
    public void equalsTest() throws Exception {
        Invoice invoice1 = RandomEntities.getInvoice();
        InvoiceHolder invoiceHolder = new InvoiceHolder(0, invoice1.getTotal(), invoice1.isPaid(), invoice1.getDescription(), JdbcDaoFactory.getInstance());
        assertThat(invoice1.equals(invoiceHolder), is(true));
        assertThat(invoiceHolder.equals(invoice1), is(true));
    }

    @Test
    public void insertAndGetBlankTest() throws Exception {
        Invoice invoice1 = RandomEntities.getInvoice();
        serviceFactory.getInvoiceService().insert(invoice1);
        Invoice invoiceDao  = serviceFactory.getInvoiceService().getById(invoice1.getId());

        assertThat(invoiceDao instanceof InvoiceHolder, is(true));
        assertThat(invoiceDao.equals(invoice1), is(true));
        assertThat(invoice1.equals(invoiceDao), is(true));
    }

    @Test
    public void insertAndGetTest() throws Exception {
        Client client1 = RandomEntities.getClient();
        serviceFactory.getClientService().insert(client1);

        Invoice invoice1 = RandomEntities.getInvoice();
        invoice1.setClient(client1);
        serviceFactory.getInvoiceService().insert(invoice1);
        Invoice invoiceDao  = serviceFactory.getInvoiceService().getById(invoice1.getId());

        assertThat(invoiceDao instanceof InvoiceHolder, is(true));
        assertThat(invoiceDao.equals(invoice1), is(true));
        assertThat(invoice1.equals(invoiceDao), is(true));
    }

    @Test
    public void getEntities() throws Exception {
        Invoice invoice1 = RandomEntities.getInvoice();
        InvoiceHolder invoiceHolder = new InvoiceHolder(0, invoice1.getTotal(), invoice1.isPaid(), invoice1.getDescription(), JdbcDaoFactory.getInstance());
        assertThat(invoiceHolder.getClientId(), is(0));
        assertThat(invoiceHolder.getClient() == null, is(true));
    }

    @Test
    public void setEntity() throws Exception {
        Client client1 = RandomEntities.getClient();
        serviceFactory.getClientService().insert(client1);
        Invoice invoice1 = RandomEntities.getInvoice();
        InvoiceHolder invoiceHolder = new InvoiceHolder(0, invoice1.getTotal(), invoice1.isPaid(), invoice1.getDescription(), JdbcDaoFactory.getInstance());
        invoiceHolder.setClient(client1);

        assertThat(invoiceHolder.getClientId(),is(client1.getId()));
        assertThat(invoiceHolder.getClient(),is(client1));
    }
}