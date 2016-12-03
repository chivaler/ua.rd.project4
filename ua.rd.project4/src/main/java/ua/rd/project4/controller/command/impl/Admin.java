package ua.rd.project4.controller.command.impl;

import ua.rd.project4.controller.command.Command;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.services.ServiceFactory;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class Admin implements Command {
    private static final Admin instance = new Admin();

    private Admin() {
    }

    static Admin getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        addListsToRequest(req);
        return "jsp/admin.jsp";
    }

    ServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    void addListsToRequest(HttpServletRequest req) {
        req.setAttribute("listCars", getServiceFactory().
                getCarService().findAll().stream().
                collect(Collectors.toMap(Entity::getId, Car::toString)));
        req.setAttribute("listCarsIn", getServiceFactory().getCarService().findAll().stream()
                .filter(s -> getServiceFactory().getCarFlowService().isCarInBox(s.getId()))
                .collect(Collectors.toList()));
        req.setAttribute("listCarsOut", getServiceFactory().getCarService().findAll().stream()
                .filter(s -> !getServiceFactory().getCarFlowService().isCarInBox(s.getId()))
                .collect(Collectors.toList()));
        System.out.printf("");
        req.setAttribute("mapsCarRequests", getServiceFactory().
                getCarRequestService().getCarRequestsWithStatuses());
        System.out.println(req.getAttribute("mapsCarRequests"));
//        req.setAttribute("listCarFlows", getServiceFactory().
//                getCarFlowService().findAll().stream().
//                collect(Collectors.toMap(Entity::getId, CarFlow::toString)));
        req.setAttribute("listCarFlows", getServiceFactory().
                getCarFlowService().findAll());
    }

}
