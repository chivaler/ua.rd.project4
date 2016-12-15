package ua.rd.project4.controller.command.impl;

import org.junit.Test;
import ua.rd.project4.controller.command.CommandDispatcher;
import ua.rd.project4.controller.exceptions.InsufficientPermissionsException;
import ua.rd.project4.controller.exceptions.NotFoundException;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.SessionWrapper;
import ua.rd.project4.controller.util.ViewJsp;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandDispatcherImplTest {
    CommandDispatcher commandDispatcher = CommandDispatcherImpl.getInstance();
    RequestWrapper req = mock(RequestWrapper.class);
    SessionWrapper sessionWrapper = mock(SessionWrapper.class);

    @Test(expected = NotFoundException.class)
    public void executeRequest404() throws Exception {
        when(req.getParameter("command")).thenReturn("abracadabra");
        when(req.getSessionWrapper(false)).thenReturn(sessionWrapper);
        when(sessionWrapper.getUser()).thenReturn(null);
        commandDispatcher.executeRequest(req);
    }

    @Test(expected = NotFoundException.class)
    public void executeRequestNull() throws Exception {
        when(req.getParameter("command")).thenReturn(null);
        when(req.getSessionWrapper(false)).thenReturn(sessionWrapper);
        when(sessionWrapper.getUser()).thenReturn(null);
        commandDispatcher.executeRequest(req);
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void executeRequest2() throws Exception {
        when(req.getParameter("command")).thenReturn("INVOICES");
        when(req.getSessionWrapper(false)).thenReturn(sessionWrapper);
        when(sessionWrapper.getUser()).thenReturn(null);
        commandDispatcher.executeRequest(req);
    }

}