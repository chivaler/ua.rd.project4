package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissionsException;
import ua.rd.project4.controller.exceptions.InvalidParameterException;
import ua.rd.project4.controller.exceptions.RequiredParameterException;
import ua.rd.project4.controller.util.JspMessagesSetter;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.SessionWrapper;
import ua.rd.project4.controller.util.ViewJsp;
import ua.rd.project4.domain.Client;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.exceptions.LoginExistsException;
import ua.rd.project4.model.exceptions.UniqueViolationException;
import ua.rd.project4.model.services.ClientService;
import ua.rd.project4.model.services.UserService;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

class UserRegisterCommand implements Command {
    private static final UserRegisterCommand instance = new UserRegisterCommand();
    private final Logger logger = LogManager.getLogger(UserRegisterCommand.class);
    private final UserService userService = DefaultServiceFactory.getInstance().getUserService();
    private final ClientService clientService = DefaultServiceFactory.getInstance().getClientService();

    private UserRegisterCommand() {
    }

    static UserRegisterCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(RequestWrapper req, User user) throws InsufficientPermissionsException {
        try {
            String login = req.getParameter("login");
            String pass = req.getParameter("pass");
            if (login == null || "".equals(login))
                throw new InvalidParameterException("login");
            if (pass == null || "".equals(pass))
                throw new InvalidParameterException("pass");
            Client client = CrudClientsCommand.getInstance().parseToEntity(req);
            clientService.insert(client);
            User userNew = new User(false, login, userService.getHashPassword(pass), client);
            userService.insert(userNew);
            SessionWrapper sessionWrapper = req.getSessionWrapper(true);
            sessionWrapper.setUser(userNew);
            return UserSpaceCommand.getInstance().execute(req, userNew);
        } catch (RequiredParameterException e) {
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.FIELD_EMPTY_REQUIRED, e.getMessage());
            logger.debug("update/insert id:" + req.getParameter("id") + " wrong field:" + e.getMessage());
            logger.debug(e);
        } catch (InvalidParameterException e) {
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.FIELD_WRONG_DATA, e.getMessage());
            logger.debug("update/insert id:" + req.getParameter("id") + " wrong field:" + e.getMessage());
            logger.debug(e);
        } catch (LoginExistsException e) {
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.LOGIN_ALREADY_EXIST);
            logger.debug(e);
        } catch (UniqueViolationException e) {
            logger.error(e);
        }
        return ViewJsp.General.REGISTER_JSP;
    }
}
