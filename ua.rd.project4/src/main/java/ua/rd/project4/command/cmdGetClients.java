package ua.rd.project4.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.services.ClientService;
import ua.rd.project4.services.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class cmdGetClients implements Command {
    private static final cmdGetClients instance = new cmdGetClients();
    private final Logger logger = LogManager.getLogger(cmdGetClients.class);

    private cmdGetClients() {
    }

    public static cmdGetClients getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClientService clientService = ServiceFactory.getInstance().getClientService();
        request.setAttribute("clients", clientService.findAll());
        logger.debug("findAll Clients");
        return "jsp/admin/clients.jsp";
    }
}
