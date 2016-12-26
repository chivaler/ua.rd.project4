package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissionsException;
import ua.rd.project4.controller.util.JspMessagesSetter;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.ViewJsp;
import ua.rd.project4.domain.CarFlow;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.services.CarFlowService;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

class PrintCarFlowCommand implements Command {
    private static final PrintCarFlowCommand instance = new PrintCarFlowCommand();
    private final Logger logger = LogManager.getLogger(PrintCarFlowCommand.class);
    CarFlowService carFlowService = DefaultServiceFactory.getInstance().getCarFlowService();

    private PrintCarFlowCommand() {
    }

    static PrintCarFlowCommand getInstance() {
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
        CarFlow carFlow= carFlowService.getById(id);
        if (carFlow == null) {
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.UNKNOWN_ID);
            return "";
        }
        if (carFlow.getCarRequest()!=null && carFlow.getCarRequest().getClientId() == 0) {
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.UNKNOWN_ID);
            return "";
        }
        if (user == null || !(user.isAdmin() || carFlow.getCarRequest().getClient().equals(user.getClient())))
            throw new InsufficientPermissionsException();
        req.setAttribute("entity", carFlow);
        return ViewJsp.InvoicesCrud.VIEW_INVOICE_JSP;
    }
}
