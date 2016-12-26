package ua.rd.project4.model.services;

import org.junit.Test;
import ua.rd.project4.domain.Client;
import ua.rd.project4.domain.Invoice;
import ua.rd.project4.RandomEntities;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

import java.util.Collections;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class InvoiceServiceTest {
    private final InvoiceService invoiceService = DefaultServiceFactory.getInstance().getInvoiceService();
    private final ClientService clientService = DefaultServiceFactory.getInstance().getClientService();

    @Test
    public void findInvoicesByClientId_blank() throws Exception {
        assertThat(invoiceService.findInvoicesByClientId(-1).isEmpty(), is(true));
        assertThat(invoiceService.findInvoicesByClientId(0).isEmpty(), is(true));
    }

    @Test
    public void findInvoicesByClientId() throws Exception {
        Invoice invoice1 = RandomEntities.getInvoice();
        assertThat(invoiceService.insert(invoice1), is(true));

        Client client1 = RandomEntities.getClient();
        assertThat(clientService.insert(client1), is(true));

        assertThat(invoiceService.findInvoicesByClientId(client1.getId()).isEmpty(), is(true));

        Invoice invoice2 = RandomEntities.getInvoice();
        invoice2.setClient(client1);
        assertThat(invoiceService.insert(invoice2), is(true));

        assertThat(invoiceService.findInvoicesByClientId(client1.getId()), is(Collections.singletonList(invoice2)));

        invoice1.setClient(client1);
        invoiceService.update(invoice1.getId(), invoice1);
        assertThat(invoiceService.findInvoicesByClientId(client1.getId()), containsInAnyOrder(invoice1, invoice2));
    }


}