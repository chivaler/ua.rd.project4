package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.Attributes;
import ua.rd.project4.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommandLocalization implements Command {
    private static final CommandLocalization instance = new CommandLocalization();
    private final Logger logger = LogManager.getLogger(CommandLocalization.class);

    private CommandLocalization() {
    }

    static CommandLocalization getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(true);
        String language = req.getParameter("language");
        if ("ENG".equals(language)) {
            session.setAttribute(Attributes.LANG, "en_US");
            logger.info("locale en_US set");
        } else {
            session.setAttribute(Attributes.LANG, "uk_UA");
            logger.info("locale uk_UA set");
        }


        return "/jsp/login.jsp";
    }
}
