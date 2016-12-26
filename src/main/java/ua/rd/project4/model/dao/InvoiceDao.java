package ua.rd.project4.model.dao;

import ua.rd.project4.domain.Invoice;

import java.util.List;

public interface InvoiceDao extends EntityDao<Invoice> {
    List<Invoice> findInvoicesByClientId(int idClient);
}
