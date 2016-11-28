package ua.rd.project4.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.entities.Client;
import ua.rd.project4.entities.SystemUser;
import ua.rd.project4.services.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

class cmdCrudClients extends cmdCrudGeneric<Client> {
    private static final cmdCrudClients instance = new cmdCrudClients();
    private final Logger logger = LogManager.getLogger(cmdCrudClients.class);
    private final ClientService clientService = getServiceFactory().getClientService();
    final String LIST_CLIENTS_JSP = "jsp/admin/clients.jsp";
    final String EDIT_CLIENT_JSP = "jsp/admin/client.jsp";

    private cmdCrudClients() {
    }

    public static cmdCrudClients getInstance() {
        return instance;
    }

    @Override
    Logger getLogger() {
        return logger;
    }

    @Override
    AbstractServiceFactory getServiceFactory() {
        return ServiceFactory.getInstance();
    }

    @Override
    String getEntityJsp() {
        return EDIT_CLIENT_JSP;
    }

    @Override
    String getEntityListJsp() {
        return LIST_CLIENTS_JSP;
    }

    @Override
    EntityService<Client> getEntityService() {
        return clientService;
    }

    Client parseToEntity(HttpServletRequest req) throws InvalidParameterException {
        String firstName = Optional.ofNullable(req.getParameter("firstName")).orElse("");
        String lastName = Optional.ofNullable(req.getParameter("lastName")).orElse("");
        String address = Optional.ofNullable(req.getParameter("address")).orElse("");
        String telephone = Optional.ofNullable(req.getParameter("telephone")).orElse("");
        String email = Optional.ofNullable(req.getParameter("email")).orElse("");
        String idCard = Optional.ofNullable(req.getParameter("idCard")).orElse("");

        if ("".equals(firstName))
            throw new RequiredParameterException("firstName");
        if ("".equals(lastName))
            throw new RequiredParameterException("lastName");

       return new Client(firstName, lastName, address, telephone, email, idCard);
    }
}