package ua.rd.project4.controller.command.impl;

import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissions;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

class AdminCommand implements Command {
    private static final AdminCommand instance = new AdminCommand();

    private AdminCommand() {
    }

    static AdminCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, User user) throws InsufficientPermissions {
        if (user == null || !user.isAdmin())
            throw new InsufficientPermissions();
        addListsToRequest(req);
        return "jsp/admin.jsp";
    }

    ServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    void addListsToRequest(HttpServletRequest req) {
//        req.setAttribute("listCars", getServiceFactory().
//                getCarService().findAll().stream().
//                collect(Collectors.toMap(Entity::getId, Car::toString)));
        req.setAttribute("listCarsIn", getServiceFactory().getCarService().findAll().stream()
                .filter(s -> getServiceFactory().getCarFlowService().isCarInBox(s.getId()))
                .collect(Collectors.toList()));
        req.setAttribute("listCarsOut", getServiceFactory().getCarService().findAll().stream()
                .filter(s -> !getServiceFactory().getCarFlowService().isCarInBox(s.getId()))
                .collect(Collectors.toList()));
        req.setAttribute("mapsCarRequests", getServiceFactory().
                getCarRequestService().getCarRequestsWithStatuses());
//        req.setAttribute("listCarFlows", getServiceFactory().
//                getCarFlowService().findAll().stream().
//                collect(Collectors.toMap(Entity::getId, CarFlow::toString)));
        req.setAttribute("listCarFlows", getServiceFactory().
                getCarFlowService().findAll(10));
    }

}
