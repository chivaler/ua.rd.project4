package ua.rd.project4.controller.command;

import org.apache.logging.log4j.Logger;
import ua.rd.project4.domain.Client;
import ua.rd.project4.domain.Entity;
import ua.rd.project4.model.services.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;


abstract class cmdCrudGeneric<T extends Entity> implements Command {
    abstract Logger getLogger();

    abstract AbstractServiceFactory getServiceFactory();

    abstract String getEntityJsp();

    abstract String getEntityListJsp();

    abstract EntityService<T> getEntityService();

    private int getIdFromRequest(HttpServletRequest req) {
        int parsedId;
        try {
            parsedId = Integer.valueOf(req.getParameter("id"));
        } catch (NumberFormatException e) {
            parsedId = 0;
        }
        return parsedId;
    }

    abstract T parseToEntity(HttpServletRequest req) throws InvalidParameterException;

    void addListsToRequest(HttpServletRequest req) {
        req.setAttribute("listClients", getServiceFactory().
                getClientService().findAll().stream().
                collect(Collectors.toMap(Entity::getId, Client::toString)));
    }

    String executeUpdate(HttpServletRequest req, int id) {
        try {
            T entity = parseToEntity(req);
            if (id > 0)
                getEntityService().update(id, entity);
            else
                getEntityService().insert(entity);
            return executeList(req);
        } catch (RequiredParameterException e) {
            req.setAttribute("error", "Required field is empty " + e.getMessage());
            getLogger().debug("update/insert id:" + req.getParameter("id") + " wrong field:" + e.getMessage());
            getLogger().debug(e);
        } catch (InvalidParameterException e) {
            req.setAttribute("error", "Wrong data in field " + e.getMessage());
            getLogger().debug("update/insert id:" + req.getParameter("id") + " wrong field:" + e.getMessage());
            getLogger().debug(e);
        } catch (LoginExistsException e) {
            req.setAttribute("error", "User with such login already exists in database");
            getLogger().debug(e);
        } catch (UniqueViolationException e) {
            getLogger().error(e);
        }
        addListsToRequest(req);
        return getEntityJsp();
    }

    String executeGet(HttpServletRequest req, int id) {
        if (getEntityService().getById(id) != null) {
            req.setAttribute("entity", getEntityService().getById(id));
            addListsToRequest(req);
            return getEntityJsp();
        } else {
            req.setAttribute("error", "Couldn't find id:" + id);
            getLogger().debug("Get id:" + req.getParameter("id"));
            return executeList(req);
        }
    }

    String executeDelete(HttpServletRequest req, int id) {
        try {
            getEntityService().delete(id);
            req.setAttribute("result", "id:" + id + " deleted.");
        } catch (ExceptionEntityInUse exceptionEntityInUse) {
            getLogger().debug(exceptionEntityInUse);
            req.setAttribute("error", "Couldn't remove id:" + id + " " + exceptionEntityInUse.getMessage());
        }
        return executeList(req);
    }

    String executeList(HttpServletRequest req) {
        req.setAttribute("entities", getEntityService().findAll());
        return getEntityListJsp();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String doCommand = Optional.ofNullable(req.getParameter("do")).orElse("");
        final int id = getIdFromRequest(req);
        if ((id == 0) && ("get".equals(doCommand) || "delete".equals(doCommand))) {
            getLogger().debug("Clients:" + doCommand + " id:" + req.getParameter("id"));
            req.setAttribute("error", "Unknown id");
        } else switch (doCommand) {
            case "update":
                return executeUpdate(req, id);
            case "get":
                return executeGet(req, id);
            case "delete":
                return executeDelete(req, id);
            case "new":
                addListsToRequest(req);
                return getEntityJsp();
            case "list":
            case "":
                break;
            default:
                req.setAttribute("error", "Users: Unknown command");
                getLogger().debug("Users:" + doCommand + " id:" + req.getParameter("id"));
        }
        return executeList(req);
    }
}
