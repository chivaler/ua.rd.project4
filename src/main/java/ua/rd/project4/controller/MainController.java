package ua.rd.project4.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.*;
import ua.rd.project4.controller.command.impl.DefaultCommandDispatcher;
import ua.rd.project4.controller.exceptions.InsufficientPermissionsException;
import ua.rd.project4.controller.exceptions.NotFoundException;
import ua.rd.project4.controller.util.JspMessagesSetter;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.impl.RequestWrapperImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainController extends HttpServlet {
    private final transient Logger logger = LogManager.getLogger(MainController.class);
    private final transient CommandDispatcher commandDispatcher = DefaultCommandDispatcher.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        parseRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        parseRequest(req, resp);
    }

    private void parseRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jspUrl;
        RequestWrapper requestWrapper = new RequestWrapperImpl(req);
        try {
            jspUrl = commandDispatcher.executeRequest(requestWrapper);
            req.getRequestDispatcher(jspUrl).forward(req, resp);
        } catch (InsufficientPermissionsException e) {
            logger.debug(e);
            JspMessagesSetter.setOutputError(requestWrapper, JspMessagesSetter.JspError.INSUFFICIENT_PERMISSIONS);
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (NotFoundException e) {
            logger.debug(e);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e){
            logger.error(e);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

