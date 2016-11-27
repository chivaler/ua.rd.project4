package ua.rd.project4.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.entities.Client;
import ua.rd.project4.entities.SystemUser;
import ua.rd.project4.services.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

class cmdCrudUsers implements Command {
    private static final cmdCrudUsers instance = new cmdCrudUsers();
    private final Logger logger = LogManager.getLogger(cmdCrudUsers.class);
    private final UserService userService = ServiceFactory.getInstance().getUserService();
    private final ClientService clientService = ServiceFactory.getInstance().getClientService();

    private cmdCrudUsers() {
    }

    public static cmdCrudUsers getInstance() {
        return instance;
    }

    SystemUser parseToEntity(HttpServletRequest req) throws InvalidParameterException {
        boolean isAdmin;
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        String hashedPassword = req.getParameter("password");
        String passwordToSave;
        String strClient = req.getParameter("client");

        if (login == null || "".equals(login))
            throw new InvalidParameterException("login");
        if ((hashedPassword == null || "".equals(hashedPassword) ) && (pass == null || "".equals(pass)))
            throw new InvalidParameterException("pass");

        isAdmin = req.getParameter("isAdmin") != null;

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

        return new SystemUser(isAdmin, login, passwordToSave, client);
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String doCommand = req.getParameter("do");
        int parsedId=0;
        try {
            parsedId = Integer.valueOf(req.getParameter("id"));
        } catch (Exception e) {
        }
        final int id = parsedId;
        if ((id == 0) && ("get".equals(doCommand) || "delete".equals(doCommand))) {
            logger.debug("Users:" + doCommand + " id:" + req.getParameter("id"));
            req.setAttribute("error", "Unknown id");
            req.setAttribute("users", userService.findAll());
            return "jsp/admin/users.jsp";
        }
        switch (doCommand) {
            case "insertOrUpdate":
                try {
                    SystemUser user = parseToEntity(req);
                    if (userService.findAll().stream().
                            filter(s -> (s.getLogin().equals(user.getLogin()) && s.getId() != id)).
                            count() > 0)
                        throw new InvalidParameterException("login");
                    if (id > 0)
                        userService.update(id, user);
                    else
                        userService.insert(user);
                } catch (InvalidParameterException e) {
                    req.setAttribute("error", "Check entered data in field " + e.getMessage());
                    logger.debug("Users:" + doCommand + " id:" + req.getParameter("id") + " wrond field:" + e.getMessage());
                    return "jsp/admin/user.jsp";
                }
                break;
            case "get":
                if (userService.getById(id) != null) {
                    req.setAttribute("user", userService.getById(id));
                    return "jsp/admin/user.jsp";
                } else {
                    req.setAttribute("error", "Users: Couldn't find User with id");
                    logger.debug("Users:" + doCommand + " id:" + req.getParameter("id"));
                }
                break;
            case "delete":
                try {
                    userService.delete(id);
                    req.setAttribute("result", "User with id:" + id + " deleted.");
                } catch (ExceptionEntityInUse exceptionEntityInUse) {
                    logger.warn(exceptionEntityInUse.toString());
                    req.setAttribute("error", "Couldn't remove user with id:" + id + " " + exceptionEntityInUse.getMessage());
                }
                break;
            case "list":
                break;
            default:
                req.setAttribute("error", "Users: Unknown command");
                logger.debug("Users:" + doCommand + " id:" + req.getParameter("id"));
                break;
        }
        req.setAttribute("users", userService.findAll());
        return "jsp/admin/users.jsp";
    }
}
