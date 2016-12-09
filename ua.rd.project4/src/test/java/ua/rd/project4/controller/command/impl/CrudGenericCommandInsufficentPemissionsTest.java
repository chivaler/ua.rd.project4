package ua.rd.project4.controller.command.impl;

import org.junit.Test;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.exceptions.InsufficientPermissionsException;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.RandomEntities;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class CrudGenericCommandInsufficentPemissionsTest {
    private RequestWrapper req = mock(RequestWrapper.class);
    User user = null;

    abstract Command getCommand();

    @Test(expected = InsufficientPermissionsException.class)
    public void execute_update() throws Exception {
        when(req.getParameter("do")).thenReturn("update");
        getCommand().execute(req, user);
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void execute_delete() throws Exception {
        when(req.getParameter("do")).thenReturn("update");
        getCommand().execute(req, user);
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void execute_new() throws Exception {
        when(req.getParameter("do")).thenReturn("new");
        getCommand().execute(req, user);
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void execute_list() throws Exception {
        when(req.getParameter("do")).thenReturn("list");
        getCommand().execute(req, user);
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void execute_blank() throws Exception {
        when(req.getParameter("do")).thenReturn("");
        getCommand().execute(req, user);
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void execute_NULL() throws Exception {
        when(req.getParameter("do")).thenReturn(RandomEntities.getString());
        getCommand().execute(req, user);
    }

}