package ua.rd.project4.model.dao;

import ua.rd.project4.domain.Invoice;

import java.util.List;

public abstract class InvoiceDao implements EntityDao<Invoice> {
    public abstract List<Invoice> findInvoicesByClientId(int idClient);
}
