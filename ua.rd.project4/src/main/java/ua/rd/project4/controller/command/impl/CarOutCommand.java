package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissions;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.exceptions.CarRequestApproveNeededException;
import ua.rd.project4.model.exceptions.CarRequestPaymentNeededException;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import javax.servlet.http.HttpServletRequest;

class CarOutCommand implements Command {
    private static final CarOutCommand instance = new CarOutCommand();
    private final Logger logger = LogManager.getLogger(CarOutCommand.class);
    private final String PAYMENT_NEEDED="Car Request isn't paid";
    private final String APPROVE_NEEDED="Car Request isn't approved";
//    private final String AUTHORIZATION_NEEDED="Autorization needed";

    private CarOutCommand() {
    }

    static CarOutCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest req, User user) throws InsufficientPermissions {
        if (user == null || !user.isAdmin())
            throw new InsufficientPermissions();
        try {
            final int carRequestiId = Integer.parseInt(req.getParameter("carRequest"));
            JdbcServiceFactory.getInstance().getCarRequestService().checkInCarOut(carRequestiId, user);
        } catch (NumberFormatException e) {
            logger.error(e);
        } catch (CarRequestPaymentNeededException e) {
            req.setAttribute("error", PAYMENT_NEEDED);
            logger.debug(e);
        } catch (CarRequestApproveNeededException e) {
            req.setAttribute("error", APPROVE_NEEDED);
            logger.debug(e);
        }
        return AdminCommand.getInstance().execute(req, user);
    }
}
