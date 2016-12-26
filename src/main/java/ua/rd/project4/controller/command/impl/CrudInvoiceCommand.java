package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.exceptions.InvalidParameterException;
import ua.rd.project4.controller.exceptions.RequiredParameterException;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.ViewJsp;
import ua.rd.project4.domain.Client;
import ua.rd.project4.domain.Invoice;
import ua.rd.project4.model.services.*;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

import java.math.BigDecimal;
import java.util.Optional;

class CrudInvoiceCommand extends GenericCrudCommand<Invoice> {
    private static final CrudInvoiceCommand instance = new CrudInvoiceCommand();
    private final Logger logger = LogManager.getLogger(CrudInvoiceCommand.class);
    private final ClientService clientService = getServiceFactory().getClientService();
    private final InvoiceService invoiceService = getServiceFactory().getInvoiceService();

    private CrudInvoiceCommand() {
    }

    static CrudInvoiceCommand getInstance() {
        return instance;
    }

    @Override
    Logger getLogger() {
        return logger;
    }

    @Override
    ServiceFactory getServiceFactory() {
        return DefaultServiceFactory.getInstance();
    }

    @Override
    String getEntityJsp() {
        return ViewJsp.InvoicesCrud.EDIT_INVOICE_JSP;
    }

    @Override
    String getEntityListJsp() {
        return ViewJsp.InvoicesCrud.LIST_INVOICES_JSP;
    }

    @Override
    EntityService<Invoice> getEntityService() {
        return invoiceService;
    }

    @Override
    Invoice parseToEntity(RequestWrapper req) throws InvalidParameterException {
        int idClient;
        try {
            idClient = Integer.valueOf(req.getParameter("client"));
        } catch (NumberFormatException e) {
            idClient = 0;
        }
        Client client = clientService.getById(idClient);
        String description = Optional.ofNullable(req.getParameter("description")).orElse("");
        boolean paid = req.getParameter("paid") != null;
        BigDecimal total;
        try {
            total = new BigDecimal(req.getParameter("total"));
        } catch (Exception e) {
            logger.debug(e);
            throw new RequiredParameterException("total");
        }
        return new Invoice(client, total, paid, description);
    }
}