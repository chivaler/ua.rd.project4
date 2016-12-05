package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissions;
import ua.rd.project4.controller.exceptions.InvalidParameterException;
import ua.rd.project4.controller.exceptions.RequiredParameterException;
import ua.rd.project4.domain.Client;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.exceptions.CarRequestApproveNeededException;
import ua.rd.project4.model.exceptions.CarRequestPaymentNeededException;
import ua.rd.project4.model.exceptions.LoginExistsException;
import ua.rd.project4.model.exceptions.UniqueViolationException;
import ua.rd.project4.model.services.ClientService;
import ua.rd.project4.model.services.UserService;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class UserRegisterCommand implements Command {
    private static final UserRegisterCommand instance = new UserRegisterCommand();
    private final Logger logger = LogManager.getLogger(UserRegisterCommand.class);
    private final UserService userService = JdbcServiceFactory.getInstance().getUserService();
    private final ClientService clientService = JdbcServiceFactory.getInstance().getClientService();

    private UserRegisterCommand() {
    }

    static UserRegisterCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, User user) throws InsufficientPermissions {
        try {
            String login = req.getParameter("login");
            String pass = req.getParameter("pass");
            if (login == null || "".equals(login))
                throw new InvalidParameterException("login");
            if (pass == null || "".equals(pass))
                throw new InvalidParameterException("pass");
            Client client = CrudClientsCommand.getInstance().parseToEntity(req);
            clientService.insert(client);
            client.setId(clientService.findId(client));
            User userNew = new User(false,login,userService.getHashPassword(pass),client);
            userService.insert(userNew);
            userNew.setId(userService.findId(userNew));
            HttpSession session = req.getSession(true);
            session.setAttribute("user", userNew);
            return UserSpaceCommand.getInstance().execute(req, resp, userNew);
        } catch (RequiredParameterException e) {
            req.setAttribute("error", "Required field is empty " + e.getMessage());
            logger.debug("update/insert id:" + req.getParameter("id") + " wrong field:" + e.getMessage());
            logger.debug(e);
        } catch (InvalidParameterException e) {
            req.setAttribute("error", "Wrong data in field " + e.getMessage());
            logger.debug("update/insert id:" + req.getParameter("id") + " wrong field:" + e.getMessage());
            logger.debug(e);
        } catch (LoginExistsException e) {
            req.setAttribute("error", "User with such login already exists in database");
            logger.debug(e);
        } catch (UniqueViolationException e) {
            logger.error(e);
        }
        return "jsp/register.jsp";
    }
}
