package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.exceptions.InvalidParameterException;
import ua.rd.project4.controller.exceptions.RequiredParameterException;
import ua.rd.project4.domain.CarRequest;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.model.holders.CarRequestHolder;
import ua.rd.project4.model.services.*;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;

class cmdCrudCarRequest extends cmdCrudGeneric<CarRequest> {
    private static final cmdCrudCarRequest instance = new cmdCrudCarRequest();
    private static final String LIST_CARREQUESTS_JSP = "jsp/car_requests.jsp";
    private static final String EDIT_CARREQUEST_JSP = "jsp/car_request.jsp";
    private final Logger logger = LogManager.getLogger(cmdCrudCarRequest.class);
    private final CarRequestService carRequestService = getServiceFactory().getCarRequestService();


    private cmdCrudCarRequest() {
    }

    static cmdCrudCarRequest getInstance() {
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
        return EDIT_CARREQUEST_JSP;
    }

    @Override
    String getEntityListJsp() {
        return LIST_CARREQUESTS_JSP;
    }

    @Override
    EntityService<CarRequest> getEntityService() {
        return carRequestService;
    }

    @Override
    CarRequest parseToEntity(HttpServletRequest req) throws InvalidParameterException {
        int carId = 0;
        try {
            carId = Integer.valueOf(req.getParameter("car"));
        } catch (NumberFormatException e) {
            throw new RequiredParameterException("car");
        }
        int clientId = 0;
        try {
            clientId = Integer.valueOf(req.getParameter("client"));
        } catch (NumberFormatException e) {
            throw new RequiredParameterException("client");
        }
        Date dateFrom = null;
        try {
            dateFrom = Date.valueOf(req.getParameter("dateFrom"));
        } catch (Exception e) {
            throw new RequiredParameterException("dateFrom");
        }
        Date dateTo = null;
        try {
            dateTo = Date.valueOf(req.getParameter("dateTo"));
        } catch (Exception e) {
            throw new RequiredParameterException("dateTo");
        }
        int invoiceId = 0;
        try {
            invoiceId = Integer.valueOf(req.getParameter("invoice"));
        } catch (NumberFormatException e) {
        }
        CarRequest.RequestStatus status = CarRequest.RequestStatus.NEW;
        try {
            status = CarRequest.RequestStatus.valueOf(req.getParameter("status"));
        } catch (Exception e) {
        }

        BigDecimal totalCost = null;
        try {
            totalCost = new BigDecimal(req.getParameter("totalCost"));
        } catch (NumberFormatException e) {
        }
        String rejectReason = Optional.ofNullable(req.getParameter("rejectReason")).orElse("");

        return new CarRequestHolder(carId, clientId, dateFrom, dateTo, totalCost, status, invoiceId, rejectReason, JdbcDaoFactory.getInstance());
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if ("approve".equals(req.getParameter("do"))) {
            final int id = getIdFromRequest(req);
            if (id == 0) {
                req.setAttribute("error", "Unknown id");
            } else
                carRequestService.approve(id);
            return "";
        }
        if ("reject".equals(req.getParameter("do"))) {
            final int id = getIdFromRequest(req);
            if (id == 0) {
                req.setAttribute("error", "Unknown id");
            } else
                carRequestService.reject(id,"Sorry, impossible for now");
            return "";
        } else
        return super.execute(req, resp);
    }
}