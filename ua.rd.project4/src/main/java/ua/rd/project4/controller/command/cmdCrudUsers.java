package ua.rd.project4.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.domain.Client;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.services.*;

import javax.servlet.http.HttpServletRequest;

class cmdCrudUsers extends cmdCrudGeneric<User> {
    private static final cmdCrudUsers instance = new cmdCrudUsers();
    private final Logger logger = LogManager.getLogger(cmdCrudUsers.class);
    private final UserService userService = getServiceFactory().getUserService();
    private final ClientService clientService = getServiceFactory().getClientService();
    private final String LIST_USERS_JSP = "jsp/users.jsp";
    private final String EDIT_USER_JSP = "jsp/user.jsp";

    private cmdCrudUsers() {
    }

    static cmdCrudUsers getInstance() {
        return instance;
    }

    @Override
    Logger getLogger() {
        return logger;
    }

    @Override
    AbstractServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    @Override
    String getEntityJsp() {
        return EDIT_USER_JSP;
    }

    @Override
    String getEntityListJsp() {
        return LIST_USERS_JSP;
    }

    @Override
    EntityService<User> getEntityService() {
        return userService;
    }

    User parseToEntity(HttpServletRequest req) throws InvalidParameterException {
        boolean isAdmin;
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        String hashedPassword = req.getParameter("password");
        String passwordToSave;
        String strClient = req.getParameter("client");
        int idClient;
        try {
            idClient = Integer.valueOf(strClient);
        } catch (Exception e) {
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