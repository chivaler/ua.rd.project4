package ua.rd.project4.model.services;

import ua.rd.project4.domain.Invoice;

import java.util.List;

public abstract class InvoiceService extends EntityService<Invoice> {
    public abstract List<Invoice> findInvoicesByClientId(int idClient);
}
