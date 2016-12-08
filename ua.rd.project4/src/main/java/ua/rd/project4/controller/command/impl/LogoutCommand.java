package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.ViewJsp;
import ua.rd.project4.domain.User;

class LogoutCommand implements Command {
    private static final LogoutCommand instance = new LogoutCommand();
    private final Logger logger = LogManager.getLogger(LogoutCommand.class);

    private LogoutCommand() {
    }

    static LogoutCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(RequestWrapper req, User user) {
        req.getSessionWrapper().invalidate();
        logger.info("Session of user:"+user.getLogin()+" has been invalidated");
        return ViewJsp.General.LOGIN_PAGE;
    }
}
