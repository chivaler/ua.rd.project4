package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.exceptions.InvalidParameterException;
import ua.rd.project4.controller.exceptions.RequiredParameterException;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.ViewJsp;
import ua.rd.project4.domain.Car;
import ua.rd.project4.model.services.*;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import java.math.BigDecimal;
import java.util.Optional;

class CrudCarsCommand extends GenericCrudCommand<Car> {
    private static final CrudCarsCommand instance = new CrudCarsCommand();
    private final Logger logger = LogManager.getLogger(CrudCarsCommand.class);
    private final CarService clientService = getServiceFactory().getCarService();


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
        return ViewJsp.CarsCrud.EDIT_CAR_JSP;
    }

    @Override
    String getEntityListJsp() {
        return ViewJsp.CarsCrud.LIST_CARS_JSP;
    }

    @Override
    EntityService<Car> getEntityService() {
        return clientService;
    }

    @Override
    Car parseToEntity(RequestWrapper req) throws InvalidParameterException {
        Car.CarType carType;
        BigDecimal rentPricePerDay;
        BigDecimal price;
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
            rentPricePerDay = new BigDecimal(Optional.ofNullable(req.getParameter("rentPricePerDay")).orElse("0"));
        } catch (Exception e) {
            logger.debug(e);
            throw new InvalidParameterException("rentPricePerDay");
        }
        try {
            price = new BigDecimal(Optional.ofNullable(req.getParameter("price")).orElse("0"));
        } catch (NumberFormatException e) {
            logger.debug(e);
            throw new InvalidParameterException("price");
        }
        if (price.compareTo(BigDecimal.ONE) < 0)
            throw new RequiredParameterException("price");
        if (rentPricePerDay.compareTo(BigDecimal.ONE) < 0)
            throw new RequiredParameterException("rentPricePerDay");
        return new Car(model, color, carType, registrationNumber, description, price, rentPricePerDay);
    }
}