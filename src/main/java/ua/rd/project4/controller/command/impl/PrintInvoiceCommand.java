package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissionsException;
import ua.rd.project4.controller.util.JspMessagesSetter;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.ViewJsp;
import ua.rd.project4.domain.Invoice;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.services.InvoiceService;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

class PrintInvoiceCommand implements Command {
    private static final PrintInvoiceCommand instance = new PrintInvoiceCommand();
    private final Logger logger = LogManager.getLogger(PrintInvoiceCommand.class);
    InvoiceService invoiceService = DefaultServiceFactory.getInstance().getInvoiceService();

    private PrintInvoiceCommand() {
    }

    static PrintInvoiceCommand getInstance() {
        return instance;
    }

    int getIdFromRequest(RequestWrapper req) {
        int parsedId;
        try {
            parsedId = Integer.valueOf(req.getParameter("id"));
        } catch (NumberFormatException e) {
            parsedId = 0;
        }
        return parsedId;
    }

    @Override
    public String execute(RequestWrapper req, User user) throws InsufficientPermissionsException {
        int id = getIdFromRequest(req);
        Invoice invoice = invoiceService.getById(id);
        if (invoice == null) {
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.UNKNOWN_ID);
            return "";
        }
        if (invoice.getClientId() == 0) {
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.UNKNOWN_ID);
            return "";
        }
        if (user == null || !(user.isAdmin() || invoice.getClient().equals(user.getClient())))
            throw new InsufficientPermissionsException();
        req.setAttribute("entity", invoiceService.getById(id));
        return ViewJsp.InvoicesCrud.VIEW_INVOICE_JSP;
    }
}
