package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class LogoutCommand implements Command {
    private static final LogoutCommand instance = new LogoutCommand();
    private final Logger logger = LogManager.getLogger(LogoutCommand.class);

    private LogoutCommand() {
    }

    static LogoutCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, User user) {
        req.getSession().invalidate();
        logger.info("Session of user:"+user.getLogin()+" has been invalidated");
        return "/jsp/login.jsp";
    }
}
