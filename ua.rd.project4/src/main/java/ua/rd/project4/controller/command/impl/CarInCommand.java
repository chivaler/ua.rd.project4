package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissions;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.domain.CarFlow;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.exceptions.WrongCarFlowDirectionException;
import ua.rd.project4.model.services.CarFlowService;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

class CarInCommand implements Command {
    private static final CarInCommand instance = new CarInCommand();
    private final Logger logger = LogManager.getLogger(CarInCommand.class);
    private final CarFlowService carFlowService = JdbcServiceFactory.getInstance().getCarFlowService();

    private CarInCommand() {
    }

    static CarInCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(RequestWrapper req, User user) throws InsufficientPermissions {
        if (user == null || !user.isAdmin())
            throw new InsufficientPermissions();
        try {
            final int carId = Integer.parseInt(req.getParameter("car"));
            CarFlow carFlowOut = carFlowService.findLastCarFlowOfCar(carId);
            carFlowService.checkInCarFlowIn(carFlowOut.getId(), user);
        } catch (WrongCarFlowDirectionException | NumberFormatException e) {
            req.setAttribute("error", e.toString());
            logger.error(e);
        }
        return AdminCommand.getInstance().execute(req, user);
    }
}
