package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissionsException;
import ua.rd.project4.controller.util.JspMessagesSetter;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.domain.CarFlow;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.exceptions.UniqueViolationException;
import ua.rd.project4.model.exceptions.WrongCarFlowDirectionException;
import ua.rd.project4.model.services.CarFlowService;
import ua.rd.project4.model.services.CarService;
import ua.rd.project4.model.services.Messages;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

class CarInCommand implements Command {
    private static final CarInCommand instance = new CarInCommand();
    private final Logger logger = LogManager.getLogger(CarInCommand.class);
    private final CarFlowService carFlowService = DefaultServiceFactory.getInstance().getCarFlowService();
    private final CarService carService = DefaultServiceFactory.getInstance().getCarService();


    private CarInCommand() {
    }

    static CarInCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(RequestWrapper req, User user) throws InsufficientPermissionsException {
        if (user == null || !user.isAdmin())
            throw new InsufficientPermissionsException();
        try {
            final int carId = Integer.parseInt(req.getParameter("car"));
            CarFlow carFlowOut = carFlowService.findLastCarFlowOutOfCar(carId);
            if (carFlowOut==null) {
                /* Arrive of a new car to box*/
                CarFlow carFlowIn = new CarFlow(carService.getById(carId), CarFlow.CarFlowType.IN,
                        null,user,null, Messages.CAR_ARRIVE);
                carFlowService.insert(carFlowIn);
            } else
                carFlowService.checkInCarFlowIn(carFlowOut.getId(), user);
        } catch (WrongCarFlowDirectionException e) {
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.WRONG_CAR_DIRECTION);
            logger.error(e);
        } catch (NumberFormatException e) {
            logger.info(e);
        } catch (UniqueViolationException e) {
            logger.error(e);
        }
        return AdminCommand.getInstance().execute(req, user);
    }
}
