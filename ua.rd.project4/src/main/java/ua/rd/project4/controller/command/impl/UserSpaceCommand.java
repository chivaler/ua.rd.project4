package ua.rd.project4.controller.command.impl;

import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissions;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserSpaceCommand implements Command {
    private static final UserSpaceCommand instance = new UserSpaceCommand();

    private UserSpaceCommand() {
    }

    public static UserSpaceCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, User user) throws InsufficientPermissions {
        addListsToRequest(req, user);
        return "jsp/userspace.jsp";
    }

    ServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    void addListsToRequest(HttpServletRequest req, User user) {
        req.setAttribute("listCars", getServiceFactory()
                .getCarService().findAll());
        if (user!=null && user.getClient()!=null) {
            req.setAttribute("listInvoices", getServiceFactory()
                    .getInvoiceService().findInvoicesByClientId(user.getClientId()));
            req.setAttribute("listCarRequests", getServiceFactory()
                    .getCarRequestService().findCarRequestsByClientId(user.getClientId()));
            req.setAttribute("listCarFlows", getServiceFactory().
                    getCarFlowService().findCarFlowsByUserId(user.getId()));
        }

    }

}
