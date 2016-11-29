package ua.rd.project4.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.domain.CarFlow;
import ua.rd.project4.model.services.*;

import javax.servlet.http.HttpServletRequest;

class cmdCrudCarFlow extends cmdCrudGeneric<CarFlow> {
    private static final cmdCrudCarFlow instance = new cmdCrudCarFlow();
    private final Logger logger = LogManager.getLogger(cmdCrudCarFlow.class);
    private final CarFlowService carFlowService = getServiceFactory().getCarFlowService();
    final String LIST_CARFLOW_JSP = "jsp/car_flows.jsp";
    final String EDIT_CARFLOW_JSP = "jsp/car_flow.jsp";

    private cmdCrudCarFlow() {
    }

    static cmdCrudCarFlow getInstance() {
        return instance;
    }

    @Override
    Logger getLogger() {
        return logger;
    }

    @Override
    AbstractServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    @Override
    String getEntityJsp() {
        return EDIT_CARFLOW_JSP;
    }

    @Override
    String getEntityListJsp() {
        return LIST_CARFLOW_JSP;
    }

    @Override
    EntityService<CarFlow> getEntityService() {
        return carFlowService;
    }

    CarFlow parseToEntity(HttpServletRequest req) throws InvalidParameterException {
//        Car.CarType carType = null;
//        int rentPricePerDay = -1;
//        int price = -1;
//        String model = Optional.ofNullable(req.getParameter("model")).orElse("");
//        String color = Optional.ofNullable(req.getParameter("color")).orElse("");
//        String registrationNumber = Optional.ofNullable(req.getParameter("registrationNumber")).orElse("");
//        String description = Optional.ofNullable(req.getParameter("description")).orElse("");
//        if ("".equals(model))
//            throw new RequiredParameterException("model");
//        if ("".equals(registrationNumber))
//            throw new RequiredParameterException("registrationNumber");
//        try {
//            carType = Car.CarType.valueOf(req.getParameter("carType"));
//        } catch (Exception e) {
//            throw new RequiredParameterException("carType");
//        }
//        try {
//            rentPricePerDay = Integer.valueOf(req.getParameter("rentPricePerDay"));
//        } catch (Exception e) {
//            throw new RequiredParameterException("rentPricePerDay");
//        }
//        try {
//            price = Integer.valueOf(req.getParameter("price"));
//        } catch (Exception e) {
//            throw new RequiredParameterException("price");
//        }
//        if (price < 1)
//            throw new InvalidParameterException("price");
//        if (price < 1)
//            throw new InvalidParameterException("rentPricePerDay");
//        return new Car(model, color, carType, registrationNumber, description, price, rentPricePerDay);
        return null;
    }
}