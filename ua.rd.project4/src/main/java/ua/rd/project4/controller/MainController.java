package ua.rd.project4.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.*;
import ua.rd.project4.controller.command.impl.CommandMapping;
import ua.rd.project4.controller.command.impl.UserSpaceCommand;
import ua.rd.project4.controller.exceptions.InsufficientPermissions;
import ua.rd.project4.domain.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainController extends HttpServlet {
    private final transient Logger logger = LogManager.getLogger(MainController.class);
    private final String INSUFFICIENT_PERMISSIONS = "Please login with manager rights";
    private final Command USERSPACE_VIEW = UserSpaceCommand.getInstance();
    private final Map<String, Command> commandMappings = new HashMap<>();

    public MainController() {
        try {
            for (CommandMapping cmd : CommandMapping.values())
                addCommandMapping(cmd.name(), cmd.getCommand());
        } catch (Exception e) {
            logger.debug(e);
        }
    }

    void addCommandMapping(String str, Command command) {
        commandMappings.put(str, command);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jspUrl = "jsp/login.jsp";
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String commandName = req.getParameter("command");
        try {
            if (commandName == null)
                jspUrl = USERSPACE_VIEW.execute(req, resp, user);
            else {
                Command command = commandMappings.get(commandName);
//                        CommandMapping.valueOf(commandName).getCommand();
                jspUrl = command.execute(req, resp, user);
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.debug(e);
        } catch (InsufficientPermissions e) {
            logger.debug(e);
            req.setAttribute("error", INSUFFICIENT_PERMISSIONS);
        } catch (Exception e) {
            logger.error(e);
        }
        req.getRequestDispatcher(jspUrl).forward(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}