package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.exceptions.InvalidParameterException;
import ua.rd.project4.controller.exceptions.RequiredParameterException;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.ViewJsp;
import ua.rd.project4.domain.CarFlow;
import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.model.holders.CarFlowHolder;
import ua.rd.project4.model.services.*;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import java.util.Optional;

class CrudCarFlowCommand extends GenericCrudCommand<CarFlow> {
    private static final CrudCarFlowCommand instance = new CrudCarFlowCommand();
    private final Logger logger = LogManager.getLogger(CrudCarFlowCommand.class);
    private final CarFlowService carFlowService = getServiceFactory().getCarFlowService();

    private CrudCarFlowCommand() {
    }

    static CrudCarFlowCommand getInstance() {
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
        return ViewJsp.CarFlowsCrud.EDIT_CARFLOW_JSP;
    }

    @Override
    String getEntityListJsp() {
        return ViewJsp.CarFlowsCrud.LIST_CARFLOW_JSP;
    }

    @Override
    EntityService<CarFlow> getEntityService() {
        return carFlowService;
    }

    @Override
    CarFlow parseToEntity(RequestWrapper req) throws InvalidParameterException {
        int carId = 0;
        try {
            carId = Integer.valueOf(req.getParameter("car"));
        } catch (NumberFormatException e) {
            throw new RequiredParameterException("car");
        }
        CarFlow.CarFlowType carFlowType = CarFlow.CarFlowType.IN;
        try {
            carFlowType = CarFlow.CarFlowType.valueOf(req.getParameter("carFlowType"));
        } catch (Exception e) {
            logger.debug(e);
            throw new RequiredParameterException("carFlowType");
        }
        int responsiblePersonId = 0;
        try {
            responsiblePersonId = Integer.valueOf(req.getParameter("responsibleUser"));
        } catch (NumberFormatException e) {
            throw new RequiredParameterException("responsibleUser");
        }
        int carRequestId = 0;
        try {
            carRequestId = Integer.valueOf(req.getParameter("carRequest"));
        } catch (NumberFormatException e) {
        }
        int invoiceId = 0;
        try {
            invoiceId = Integer.valueOf(req.getParameter("invoice"));
        } catch (NumberFormatException e) {
        }
        String supplement = Optional.ofNullable(req.getParameter("supplement")).orElse("");
        return new CarFlowHolder(carId,carFlowType,carRequestId,responsiblePersonId,invoiceId, supplement, JdbcDaoFactory.getInstance());
    }
}