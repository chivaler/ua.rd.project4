package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.exceptions.InvalidParameterException;
import ua.rd.project4.controller.exceptions.RequiredParameterException;
import ua.rd.project4.domain.Car;
import ua.rd.project4.model.services.*;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

class CrudCarsCommand extends GenericCrudCommand<Car> {
    private static final CrudCarsCommand instance = new CrudCarsCommand();
    private final Logger logger = LogManager.getLogger(CrudCarsCommand.class);
    private final CarService clientService = getServiceFactory().getCarService();
    private static final String LIST_CARS_JSP = "jsp/cars.jsp";
    private static final  String EDIT_CAR_JSP = "jsp/car.jsp";

    private CrudCarsCommand() {
    }

    static CrudCarsCommand getInstance() {
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
        return EDIT_CAR_JSP;
    }

    @Override
    String getEntityListJsp() {
        return LIST_CARS_JSP;
    }

    @Override
    EntityService<Car> getEntityService() {
        return clientService;
    }

    @Override
    Car parseToEntity(HttpServletRequest req) throws InvalidParameterException {
        Car.CarType carType;
        int rentPricePerDay;
        int price;
        String model = Optional.ofNullable(req.getParameter("model")).orElse("");
        String color = Optional.ofNullable(req.getParameter("color")).orElse("");
        String registrationNumber = Optional.ofNullable(req.getParameter("registrationNumber")).orElse("");
        String description = Optional.ofNullable(req.getParameter("description")).orElse("");
        if ("".equals(model))
            throw new RequiredParameterException("model");
        if ("".equals(registrationNumber))
            throw new RequiredParameterException("registrationNumber");
        try {
            carType = Car.CarType.valueOf(req.getParameter("carType"));
        } catch (Exception e) {
            logger.debug(e);
            throw new RequiredParameterException("carType");
        }
        try {
            rentPricePerDay = Integer.valueOf(req.getParameter("rentPricePerDay"));
        } catch (Exception e) {
            logger.debug(e);
            throw new RequiredParameterException("rentPricePerDay");
        }
        try {
            price = Integer.valueOf(req.getParameter("price"));
        } catch (NumberFormatException e) {
            logger.debug(e);
            throw new RequiredParameterException("price");
        }
        if (price < 1)
            throw new InvalidParameterException("price");
        if (price < 1)
            throw new InvalidParameterException("rentPricePerDay");
        return new Car(model, color, carType, registrationNumber, description, new BigDecimal(price), new BigDecimal(rentPricePerDay));
    }
}