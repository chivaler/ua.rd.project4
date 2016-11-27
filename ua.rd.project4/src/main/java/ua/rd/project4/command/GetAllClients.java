package ua.rd.project4.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.Dao.JdbcDaoFactory;
import ua.rd.project4.controller.Attributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class GetAllClients implements Command{
    private static final  GetAllClients instance = new GetAllClients();
    private Logger logger = LogManager.getLogger(GetAllClients.class);

    private GetAllClients() {
    }

    public static GetAllClients getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(1);
        response.getWriter().println(2);
        request.setAttribute(Attributes.CLIENTS,
                JdbcDaoFactory.getInstance().getClientDao().findAll());
        System.out.println(1);
        response.getWriter().println(2);
        logger.debug("findAll Clients");
        return "jsp/admin/clients.jsp";
    }
}
