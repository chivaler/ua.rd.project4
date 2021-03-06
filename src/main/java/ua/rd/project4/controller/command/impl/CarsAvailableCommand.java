package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissionsException;
import ua.rd.project4.controller.exceptions.InvalidParameterException;
import ua.rd.project4.controller.exceptions.RequiredParameterException;
import ua.rd.project4.controller.util.JspMessagesSetter;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.domain.Car;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

class CarsAvailableCommand implements Command {
    private static final CarsAvailableCommand instance = new CarsAvailableCommand();
    private final Logger logger = LogManager.getLogger(CarsAvailableCommand.class);

    private CarsAvailableCommand() {
    }

    static CarsAvailableCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(RequestWrapper req, User user) throws InsufficientPermissionsException {
        try {
            Date dateFrom;
            try {
                dateFrom = Date.valueOf(req.getParameter("dateFrom"));
            } catch (Exception e) {
                logger.debug(e);
                throw new RequiredParameterException("dateFrom");
            }
            Date dateTo;
            try {
                dateTo = Date.valueOf(req.getParameter("dateTo"));
            } catch (Exception e) {
                logger.debug(e);
                throw new RequiredParameterException("dateTo");
            }
            if (dateFrom.compareTo(dateTo) > 0)
                throw new InvalidParameterException("dateTo<dateFrom");
            if (dateFrom.compareTo(Date.valueOf(LocalDate.now())) < 0)
                throw new InvalidParameterException("dateTo<current");
            List<Car> listAvailableCars = DefaultServiceFactory.getInstance()
                    .getCarRequestService().findAvailableCars(dateFrom, dateTo);
            req.setAttribute("listAvailableCars", listAvailableCars);
        } catch (RequiredParameterException e) {
            JspMessagesSetter.setOutputError(req,  JspMessagesSetter.JspError.FIELD_EMPTY_REQUIRED, e.getMessage());
            logger.debug("update/insert id:" + req.getParameter("id") + " wrong field:" + e.getMessage());
            logger.debug(e);
        } catch (InvalidParameterException e) {
            JspMessagesSetter.setOutputError(req,  JspMessagesSetter.JspError.FIELD_WRONG_DATA, e.getMessage());
            logger.debug("update/insert id:" + req.getParameter("id") + " wrong field:" + e.getMessage());
            logger.debug(e);
        }
        return UserSpaceCommand.getInstance().execute(req, user);
    }
}
