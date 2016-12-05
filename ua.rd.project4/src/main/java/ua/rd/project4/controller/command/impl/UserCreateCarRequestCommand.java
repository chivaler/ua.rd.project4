package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissions;
import ua.rd.project4.controller.exceptions.InvalidParameterException;
import ua.rd.project4.domain.CarRequest;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.exceptions.UniqueViolationException;
import ua.rd.project4.model.services.CarRequestService;
import ua.rd.project4.model.services.CarService;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

class UserCreateCarRequestCommand implements Command {
    private static final UserCreateCarRequestCommand instance = new UserCreateCarRequestCommand();
    private final Logger logger = LogManager.getLogger(UserCreateCarRequestCommand.class);
    private final CarRequestService carRequestService = JdbcServiceFactory.getInstance().getCarRequestService();
    private final CarService carService = JdbcServiceFactory.getInstance().getCarService();

    private UserCreateCarRequestCommand() {
    }

    static UserCreateCarRequestCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, User user) throws InsufficientPermissions {
        try {
            if (user.getClient()==null)
                throw new InsufficientPermissions();
            int carId = 0;
            try {
                carId = Integer.valueOf(req.getParameter("car"));
            } catch (NumberFormatException e) {
                throw new InvalidParameterException("car");
            }
            Date dateFrom;
            try {
                dateFrom = Date.valueOf(req.getParameter("dateFrom"));
            } catch (Exception e) {
                throw new InvalidParameterException("dateFrom");
            }
            Date dateTo;
            try {
                dateTo = Date.valueOf(req.getParameter("dateTo"));
            } catch (Exception e) {
                throw new InvalidParameterException("dateTo");
            }
            CarRequest carRequest = new CarRequest(carService.getById(carId),
                    user.getClient(),dateFrom,dateTo,null, CarRequest.RequestStatus.NEW,
                    null,"" );
            carRequest.setTotalCost(carRequestService.calculateTotal(carRequest));
            carRequestService.insert(carRequest);
        } catch (InvalidParameterException e) {
            req.setAttribute("error", "Wrong data in field " + e.getMessage());
            logger.debug("update/insert id:" + req.getParameter("id") + " wrong field:" + e.getMessage());
            logger.debug(e);
        } catch (UniqueViolationException e) {
            logger.error(e);
        }
        return UserSpaceCommand.getInstance().execute(req, resp, user);
    }
}
