package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommandLogin implements Command {
    private static final CommandLogin instance = new CommandLogin();
    private final Logger logger = LogManager.getLogger(CommandLogin.class);

    private CommandLogin() {
    }

    static CommandLogin getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session;
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = JdbcServiceFactory.getInstance().getUserService().authentication(login,password);
        if (user != null) {
            session = req.getSession(true);
            session.setAttribute("user", user);
            if (user.isAdmin()) {
                logger.info("Admin logged in:"+login);
                return CommandAdmin.getInstance().execute(req, resp);
            } else {
                logger.info("User logged in: "+login);
                return "/jsp/user.jsp";
            }
        }
        logger.info("Wrong login and/or password");
        req.setAttribute("error", "wrong login");
        return "/jsp/login.jsp";
    }
}
