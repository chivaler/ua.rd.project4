package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class LocalizationCommand implements Command {
    private static final LocalizationCommand instance = new LocalizationCommand();
    private final Logger logger = LogManager.getLogger(LocalizationCommand.class);

    private LocalizationCommand() {
    }

    static LocalizationCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest req, User user) {
        HttpSession session = req.getSession(true);
        String language = req.getParameter("language");
        if ("ENG".equals(language)) {
            session.setAttribute("lang", "en_US");
            logger.info("locale en_US set");
        } else {
            session.setAttribute("lang", "uk_UA");
            logger.info("locale uk_UA set");
        }


        return "/jsp/login.jsp";
    }
}
