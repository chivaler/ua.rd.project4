package ua.rd.project4.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainController extends HttpServlet {
    private Logger logger = LogManager.getLogger(MainController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter("command");
        try {
            Command command = commandList.valueOf(commandName).getCommand();
            String jspUrl = command.execute(req, resp);
            req.getRequestDispatcher(jspUrl).forward(req, resp);
        } catch (IllegalArgumentException e){
            //TODO back on previosn error
        }catch (Exception e) {
            logger.info("Exception in command: " + e.toString());
            // TODO error to user here
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}