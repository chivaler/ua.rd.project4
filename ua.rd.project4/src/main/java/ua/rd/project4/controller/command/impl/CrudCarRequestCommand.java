package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.exceptions.InsufficientPermissionsException;
import ua.rd.project4.controller.exceptions.InvalidParameterException;
import ua.rd.project4.controller.exceptions.RequiredParameterException;
import ua.rd.project4.controller.util.JspMessagesSetter;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.ViewJsp;
import ua.rd.project4.domain.CarRequest;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.model.exceptions.ConflictsRequestException;
import ua.rd.project4.model.exceptions.PaymentExistException;
import ua.rd.project4.model.holders.CarRequestHolder;
import ua.rd.project4.model.services.*;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;


class CrudCarRequestCommand extends GenericCrudCommand<CarRequest> {
    private static final CrudCarRequestCommand instance = new CrudCarRequestCommand();
    private final Logger logger = LogManager.getLogger(CrudCarRequestCommand.class);
    private final CarRequestService carRequestService = getServiceFactory().getCarRequestService();

    private CrudCarRequestCommand() {
    }

    static CrudCarRequestCommand getInstance() {
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
        return ViewJsp.CarRequestsCrud.EDIT_CARREQUEST_JSP;
    }

    @Override
    String getEntityListJsp() {
        return ViewJsp.CarRequestsCrud.LIST_CARREQUESTS_JSP;
    }

    @Override
    EntityService<CarRequest> getEntityService() {
        return carRequestService;
    }

    @Override
    CarRequest parseToEntity(RequestWrapper req) throws InvalidParameterException {
        int carId;
        try {
            carId = Integer.valueOf(req.getParameter("car"));
        } catch (NumberFormatException e) {
            throw new RequiredParameterException("car");
        }
        int clientId;
        try {
            clientId = Integer.valueOf(req.getParameter("client"));
        } catch (NumberFormatException e) {
            throw new RequiredParameterException("client");
        }
        Date dateFrom;
        try {
            dateFrom = Date.valueOf(req.getParameter("dateFrom"));
        } catch (Exception e) {
            throw new RequiredParameterException("dateFrom");
        }
        Date dateTo;
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
    public String execute(RequestWrapper req, User user) throws InsufficientPermissionsException {
        if ("approve".equals(req.getParameter("do"))) {
            final int id = getIdFromRequest(req);
            if (id == 0) {
                JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.UNKNOWN_ID);
            } else
                try {
                    carRequestService.approve(id);
                } catch (ConflictsRequestException e) {
                    JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.APPROVE_CONFLICTS);
                }
            return AdminCommand.getInstance().execute(req, user);
        }
        if ("reject".equals(req.getParameter("do"))) {
            final int id = getIdFromRequest(req);
            if (id == 0) {
                JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.UNKNOWN_ID);
            } else
                try {
                    carRequestService.reject(id, Messages.DEFAULT_REJECT_REASON);
                } catch (PaymentExistException e) {
                    logger.info(e);
                    JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.PAYMENT_EXIST);
                }
            return AdminCommand.getInstance().execute(req, user);
        } else
            return super.execute(req, user);
    }
}