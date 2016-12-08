package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.exceptions.InvalidParameterException;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.ViewJsp;
import ua.rd.project4.domain.Client;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.services.*;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

class CrudUsersCommand extends GenericCrudCommand<User> {
    private static final CrudUsersCommand instance = new CrudUsersCommand();
    private final Logger logger = LogManager.getLogger(CrudUsersCommand.class);
    private final UserService userService = getServiceFactory().getUserService();
    private final ClientService clientService = getServiceFactory().getClientService();

    private CrudUsersCommand() {
    }

    static CrudUsersCommand getInstance() {
        return instance;
    }

    @Override
    Logger getLogger() {
        return logger;
    }

    @Override
    ServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    @Override
    String getEntityJsp() {
        return ViewJsp.UsersCrud.EDIT_USER_JSP;
    }

    @Override
    String getEntityListJsp() {
        return ViewJsp.UsersCrud.LIST_USERS_JSP;
    }

    @Override
    EntityService<User> getEntityService() {
        return userService;
    }

    User parseToEntity(RequestWrapper req) throws InvalidParameterException {
        boolean isAdmin;
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        String hashedPassword = req.getParameter("passwordHash");
        String passwordToSave;
        String strClient = req.getParameter("client");
        int idClient;
        try {
            idClient = Integer.valueOf(strClient);
        } catch (NumberFormatException e) {
            idClient = 0;
        }
        Client client = clientService.getById(idClient);
        if (idClient > 0 && client == null)
            throw new InvalidParameterException("client");
        if (pass == null || "".equals(pass))
            passwordToSave = hashedPassword;
        else
            passwordToSave = userService.getHashPassword(pass);

        if (login == null || "".equals(login))
            throw new InvalidParameterException("login");
        if ((hashedPassword == null || "".equals(hashedPassword)) && (pass == null || "".equals(pass)))
            throw new InvalidParameterException("pass");

        isAdmin = req.getParameter("isAdmin") != null;
        return new User(isAdmin, login, passwordToSave, client);
    }
}