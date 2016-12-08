package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissions;
import ua.rd.project4.controller.exceptions.InvalidParameterException;
import ua.rd.project4.controller.exceptions.RequiredParameterException;
import ua.rd.project4.controller.util.JspMessagesSetter;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.domain.*;
import ua.rd.project4.model.exceptions.EntityInUseException;
import ua.rd.project4.model.exceptions.LoginExistsException;
import ua.rd.project4.model.exceptions.UniqueViolationException;
import ua.rd.project4.model.services.*;

import java.util.Optional;
import java.util.stream.Collectors;


abstract class GenericCrudCommand<T extends Entity> implements Command {
    abstract Logger getLogger();

    abstract ServiceFactory getServiceFactory();

    abstract String getEntityJsp();

    abstract String getEntityListJsp();

    abstract EntityService<T> getEntityService();

    int getIdFromRequest(RequestWrapper req) {
        int parsedId;
        try {
            parsedId = Integer.valueOf(req.getParameter("id"));
        } catch (NumberFormatException e) {
            parsedId = 0;
        }
        return parsedId;
    }

    abstract T parseToEntity(RequestWrapper req) throws InvalidParameterException;

    void addListsToRequest(RequestWrapper req) {
        req.setAttribute("listClients", getServiceFactory().
                getClientService().findAll().stream().
                collect(Collectors.toMap(Entity::getId, Client::toString)));
        req.setAttribute("listUsers", getServiceFactory().
                getUserService().findAll().stream().
                collect(Collectors.toMap(Entity::getId, User::toString)));
        req.setAttribute("listInvoices", getServiceFactory().
                getInvoiceService().findAll().stream().
                collect(Collectors.toMap(Entity::getId, Invoice::toString)));
        req.setAttribute("listCarRequests", getServiceFactory().
                getCarRequestService().findAll().stream().
                collect(Collectors.toMap(Entity::getId, CarRequest::toString)));
        req.setAttribute("listCarFlows", getServiceFactory().
                getCarFlowService().findAll().stream().
                collect(Collectors.toMap(Entity::getId, CarFlow::toString)));
        req.setAttribute("listCars", getServiceFactory().
                getCarService().findAll().stream().
                collect(Collectors.toMap(Entity::getId, Car::toString)));
    }

    String executeUpdate(RequestWrapper req, int id) {
        try {
            T entity = parseToEntity(req);
            if (id > 0)
                getEntityService().update(id, entity);
            else
                getEntityService().insert(entity);
            return executeList(req);
        } catch (RequiredParameterException e) {
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.FIELD_EMPTY_REQUIRED, e.getMessage());
            getLogger().debug("update/insert id:" + req.getParameter("id") + " wrong field:" + e.getMessage());
            getLogger().debug(e);
        } catch (InvalidParameterException e) {
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.FIELD_WRONG_DATA, e.getMessage());
            getLogger().debug("update/insert id:" + req.getParameter("id") + " wrong field:" + e.getMessage());
            getLogger().debug(e);
        } catch (LoginExistsException e) {
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.LOGIN_ALREADY_EXIST);
            getLogger().debug(e);
        } catch (UniqueViolationException e) {
            getLogger().error(e);
        }
        addListsToRequest(req);
        return getEntityJsp();
    }

    String executeGet(RequestWrapper req, int id) {
        if (getEntityService().getById(id) != null) {
            req.setAttribute("entity", getEntityService().getById(id));
            addListsToRequest(req);
            return getEntityJsp();
        } else {
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.UNKNOWN_ID);
            getLogger().debug("Get id:" + req.getParameter("id"));
            return executeList(req);
        }
    }

    String executeDelete(RequestWrapper req, int id) {
        try {
            getEntityService().delete(id);
            req.setAttribute("result", "id:" + id + " deleted.");
            JspMessagesSetter.setOutputMessage(req, JspMessagesSetter.JspResult.ID_REMOVED,"id:" + id );
        } catch (EntityInUseException entityInUseException) {
            getLogger().debug(entityInUseException);
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.COULDNT_REMOVE_ID,+ id + " " + entityInUseException.getMessage());
        }
        return executeList(req);
    }

    String executeList(RequestWrapper req) {
        req.setAttribute("entities", getEntityService().findAll());
        return getEntityListJsp();
    }

    @Override
    public String execute(RequestWrapper req, User user) throws InsufficientPermissions {
        String doCommand = Optional.ofNullable(req.getParameter("do")).orElse("");
        if ((user == null || !user.isAdmin()) && !"get".equals(doCommand))
            throw new InsufficientPermissions();
        final int id = getIdFromRequest(req);
        if ((id == 0) && ("get".equals(doCommand) || "delete".equals(doCommand))) {
            getLogger().debug("Clients:" + doCommand + " id:" + req.getParameter("id"));
            JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.UNKNOWN_ID);
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
                JspMessagesSetter.setOutputError(req, JspMessagesSetter.JspError.UNKNOWN_COMMAND);
                getLogger().debug("Users:" + doCommand + " id:" + req.getParameter("id"));
        }
        return executeList(req);
    }
}
