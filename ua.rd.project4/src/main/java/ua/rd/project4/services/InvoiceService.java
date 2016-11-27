package ua.rd.project4.services;

import ua.rd.project4.entities.*;

import java.util.List;

public abstract class InvoiceService extends EntityService<Invoice> {
    abstract List<Invoice> findInvoicesByClientId(int idClient);
}
