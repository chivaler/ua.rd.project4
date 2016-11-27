package ua.rd.project4.Dao;

import ua.rd.project4.entities.Invoice;

import java.util.List;

public abstract class InvoiceDao implements EntityDao<Invoice> {
    public abstract List<Invoice> findInvoicesByClientId(int idClient);
}
