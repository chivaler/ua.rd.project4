package ua.rd.project4.controller.command.impl;

import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissions;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.ViewJsp;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import java.util.stream.Collectors;

class AdminCommand implements Command {
    private static final AdminCommand instance = new AdminCommand();

    private AdminCommand() {
    }

    static AdminCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(RequestWrapper req, User user) throws InsufficientPermissions {
        if (user == null || !user.isAdmin())
            throw new InsufficientPermissions();
        addListsToRequest(req);
        return ViewJsp.AdminSpace.ADMIN_JSP;
    }

    ServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    void addListsToRequest(RequestWrapper req) {
        req.setAttribute("listCarsIn", getServiceFactory().
                getCarFlowService().getCarsInBox());
        req.setAttribute("listCarsOut", getServiceFactory().
                getCarFlowService().getCarsOutOfBox());
        req.setAttribute("mapsCarRequests", getServiceFactory().
                getCarRequestService().getCarRequestsWithStatuses());
        req.setAttribute("listCarFlows", getServiceFactory().
                getCarFlowService().findAll(10));
    }

}
