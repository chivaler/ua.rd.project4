package ua.rd.project4.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.Attributes;
import ua.rd.project4.services.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class cmdGetUsers implements Command {
    private static final cmdGetUsers instance = new cmdGetUsers();
    private final Logger logger = LogManager.getLogger(cmdGetUsers.class);

    private cmdGetUsers() {
    }

    public static cmdGetUsers getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        ClientService s = ServiceFactory.getInstance().getClientService();

        request.setAttribute(Attributes.CLIENTS,
                ServiceFactory.getInstance().getClientService().findAll());
        logger.debug("findAll Clients");
        return "jsp/admin/clients.jsp";
    }
}
