package ua.rd.project4.model.services;

import ua.rd.project4.domain.Invoice;

import java.util.List;

public interface InvoiceService extends EntityService<Invoice> {
    List<Invoice> findInvoicesByClientId(int idClient);
}
