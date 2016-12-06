package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissions;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class LoginCommand implements Command {
    private static final LoginCommand instance = new LoginCommand();
    private final Logger logger = LogManager.getLogger(LoginCommand.class);

    private LoginCommand() {
    }

    static LoginCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest req, User user) throws InsufficientPermissions {
        HttpSession session;
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login != null) {
            User auntificatedUser = JdbcServiceFactory.getInstance().getUserService().authentication(login, password);
            if (auntificatedUser != null) {
                session = req.getSession(true);
                session.setAttribute("user", auntificatedUser);
                if (auntificatedUser.isAdmin()) {
                    logger.info("Admin logged in:" + login);
                    return AdminCommand.getInstance().execute(req, auntificatedUser);
                } else {
                    logger.info("User logged in: " + login);
                    return UserSpaceCommand.getInstance().execute(req, auntificatedUser);
                }
            } else {
                logger.info("Wrong login and/or password");
                req.setAttribute("error", "wrong login");
                return "/jsp/login.jsp";
            }
        } else
            return "/jsp/login.jsp";

    }
}
