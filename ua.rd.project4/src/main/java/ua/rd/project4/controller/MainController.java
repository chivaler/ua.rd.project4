package ua.rd.project4.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.*;
import ua.rd.project4.controller.command.impl.RentCommandFactory;
import ua.rd.project4.controller.command.impl.UserSpaceCommand;
import ua.rd.project4.controller.exceptions.InsufficientPermissions;
import ua.rd.project4.domain.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MainController extends HttpServlet {
    private final transient Logger logger = LogManager.getLogger(MainController.class);
    private final String INSUFFICIENT_PERMISSIONS = "Please login with manager rights";
    private final Command USERSPACE_VIEW = UserSpaceCommand.getInstance();
    private final CommandFactory commandFactory = RentCommandFactory.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        parseRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        parseRequest(req, resp);
    }

    private void parseRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO jsp as finals
        String jspUrl="";
        HttpSession session = req.getSession(); //wrapper for session Блинов, Романченко
        User user = (User) session.getAttribute("user");
        String commandName = req.getParameter("command");
        try {
            jspUrl = commandFactory.getCommandByName(commandName).execute(req, user);
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.debug(e);
            jspUrl = commandFactory.getFallbackUrl();
        } catch (InsufficientPermissions e) {
            logger.debug(e);
            req.setAttribute("error", INSUFFICIENT_PERMISSIONS);
            jspUrl = commandFactory.getFallbackUrl();
        } catch (Exception e) {
            logger.error(e);
        }
        req.getRequestDispatcher(jspUrl).forward(req, resp);
    }
}