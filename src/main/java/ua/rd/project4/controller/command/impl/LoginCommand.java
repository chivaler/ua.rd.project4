package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissionsException;
import ua.rd.project4.controller.util.JspMessagesSetter;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.ViewJsp;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

class LoginCommand implements Command {
    private static final LoginCommand instance = new LoginCommand();
    private final Logger logger = LogManager.getLogger(LoginCommand.class);

    private LoginCommand() {
    }

    static LoginCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(RequestWrapper req, User user) throws InsufficientPermissionsException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login != null) {
            User auntificatedUser = DefaultServiceFactory.getInstance().getUserService().authentication(login, password);
            if (auntificatedUser != null) {
                req.getSessionWrapper(true).setUser(auntificatedUser);
                if (auntificatedUser.isAdmin()) {
                    logger.info("Admin logged in:" + login);
                    return AdminCommand.getInstance().execute(req, auntificatedUser);
                } else {
                    logger.info("User logged in: " + login);
                    return UserSpaceCommand.getInstance().execute(req, auntificatedUser);
                }
            } else {
                logger.info("Wrong login and/or password");
                JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.WRONG_LOGIN);
                return ViewJsp.General.LOGIN_PAGE;
            }
        } else
            return  ViewJsp.General.LOGIN_PAGE;

    }
}
