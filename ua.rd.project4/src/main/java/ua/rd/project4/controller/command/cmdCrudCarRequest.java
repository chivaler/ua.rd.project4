package ua.rd.project4.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.domain.CarRequest;
import ua.rd.project4.model.services.*;

import javax.servlet.http.HttpServletRequest;

class cmdCrudCarRequest extends cmdCrudGeneric<CarRequest> {
    private static final cmdCrudCarRequest instance = new cmdCrudCarRequest();
    private final Logger logger = LogManager.getLogger(cmdCrudCarRequest.class);
    private final CarRequestService carRequestService = getServiceFactory().getCarRequestService();
    final String LIST_CARREQUESTS_JSP = "jsp/car_requests.jsp";
    final String EDIT_CARREQUEST_JSP = "jsp/car_request.jsp";

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
    AbstractServiceFactory getServiceFactory() {
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

    CarRequest parseToEntity(HttpServletRequest req) throws InvalidParameterException {
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