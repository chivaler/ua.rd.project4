package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.SessionWrapper;
import ua.rd.project4.controller.util.ViewJsp;
import ua.rd.project4.domain.User;

class LocalizationCommand implements Command {
    private static final LocalizationCommand instance = new LocalizationCommand();
    private final Logger logger = LogManager.getLogger(LocalizationCommand.class);

    private LocalizationCommand() {
    }

    static LocalizationCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(RequestWrapper req, User user) {
        SessionWrapper sessionWrapper = req.getSessionWrapper(true);
        String language = req.getParameter("language");
        if ("ENG".equals(language)) {
            sessionWrapper.setLanguage("en_US");
            logger.info("locale en_US set");
        } else {
            sessionWrapper.setLanguage("uk_UA");
            logger.info("locale uk_UA set");
        }

        return ViewJsp.General.LOGIN_PAGE;
    }
}
