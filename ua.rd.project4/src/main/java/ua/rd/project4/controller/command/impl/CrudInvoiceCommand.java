package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.exceptions.InvalidParameterException;
import ua.rd.project4.controller.exceptions.RequiredParameterException;
import ua.rd.project4.domain.Client;
import ua.rd.project4.domain.Invoice;
import ua.rd.project4.model.services.*;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

class CrudInvoiceCommand extends GenericCrudCommand<Invoice> {
    private static final CrudInvoiceCommand instance = new CrudInvoiceCommand();
    private static final String LIST_INVOICES_JSP = "jsp/invoices.jsp";
    private static final String EDIT_INVOICE_JSP = "jsp/invoice.jsp";
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
        return JdbcServiceFactory.getInstance();
    }

    @Override
    String getEntityJsp() {
        return EDIT_INVOICE_JSP;
    }

    @Override
    String getEntityListJsp() {
        return LIST_INVOICES_JSP;
    }

    @Override
    EntityService<Invoice> getEntityService() {
        return invoiceService;
    }

    @Override
    Invoice parseToEntity(HttpServletRequest req) throws InvalidParameterException {
        int idClient;
        try {
            idClient = Integer.valueOf(req.getParameter("client"));
        } catch (NumberFormatException e) {
            idClient = 0;
        }
        Client client = clientService.getById(idClient);
        String description = Optional.ofNullable(req.getParameter("description")).orElse("");
        boolean paid = new Boolean(req.getParameter("paid"));
        BigDecimal total;
        try {
            total = new BigDecimal(req.getParameter("total"));
        } catch (Exception e) {
            logger.debug(e);
            throw new RequiredParameterException("total");
        }
        return new Invoice(client,total,paid,description);
    }
}