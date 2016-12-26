package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissionsException;
import ua.rd.project4.controller.util.JspMessagesSetter;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.exceptions.CarRequestApproveNeededException;
import ua.rd.project4.model.exceptions.CarRequestPaymentNeededException;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

class CarOutCommand implements Command {
    private static final CarOutCommand instance = new CarOutCommand();
    private final Logger logger = LogManager.getLogger(CarOutCommand.class);

    private CarOutCommand() {
    }

    static CarOutCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(RequestWrapper req, User user) throws InsufficientPermissionsException {
        if (user == null || !user.isAdmin())
            throw new InsufficientPermissionsException();
        try {
            final int carRequestiId = Integer.parseInt(req.getParameter("carRequest"));
            DefaultServiceFactory.getInstance().getCarRequestService().checkInCarOut(carRequestiId, user);
        } catch (NumberFormatException e) {
            logger.error(e);
        } catch (CarRequestPaymentNeededException e) {
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.PAYMENT_NEEDED);
            logger.debug(e);
        } catch (CarRequestApproveNeededException e) {
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.APPROVE_NEEDED);
            logger.debug(e);
        }
        return AdminCommand.getInstance().execute(req, user);
    }
}
