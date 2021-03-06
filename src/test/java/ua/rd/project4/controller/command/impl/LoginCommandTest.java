package ua.rd.project4.controller.command.impl;

import org.junit.Test;
import ua.rd.project4.controller.util.JspMessagesSetter;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.SessionWrapper;
import ua.rd.project4.controller.util.ViewJsp;
import ua.rd.project4.domain.User;
import ua.rd.project4.RandomEntities;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class LoginCommandTest {
    RequestWrapper req = mock(RequestWrapper.class);
    SessionWrapper sessionWrapper = mock(SessionWrapper.class);

    @Test
    public void execute_NULL() throws Exception {
        User user = RandomEntities.getUser();
        String randomPassword = RandomEntities.getString();
        user.setPasswordHash(DefaultServiceFactory.getInstance().getUserService().getHashPassword(randomPassword));
        user.setAdmin(false);
        DefaultServiceFactory.getInstance().getUserService().insert(user);

        when(req.getParameter("login")).thenReturn(null);
        when(req.getParameter("password")).thenReturn(null);
        when(req.getSessionWrapper(true)).thenReturn(sessionWrapper);

        assertThat(LoginCommand.getInstance().execute(req, null), is(ViewJsp.General.LOGIN_PAGE));
        verify(req, never()).setAttribute(eq("error"), any(String.class));
        verify(sessionWrapper, never()).setUser(user);
    }

    @Test
    public void execute_WrongPass() throws Exception {
        User user = RandomEntities.getUser();
        String randomPassword = RandomEntities.getString();
        user.setPasswordHash(DefaultServiceFactory.getInstance().getUserService().getHashPassword(randomPassword));
        user.setAdmin(false);
        DefaultServiceFactory.getInstance().getUserService().insert(user);

        when(req.getParameter("login")).thenReturn(user.getLogin());
        when(req.getParameter("password")).thenReturn(user.getPasswordHash());
        when(req.getSessionWrapper(true)).thenReturn(sessionWrapper);

        assertThat(LoginCommand.getInstance().execute(req, null), is(ViewJsp.General.LOGIN_PAGE));
        verify(req).setAttribute("error", JspMessagesSetter.JspError.WRONG_LOGIN.toString());
        verify(sessionWrapper, never()).setUser(user);
    }

    @Test
    public void execute_User() throws Exception {
        User user = RandomEntities.getUser();
        String randomPassword = RandomEntities.getString();
        user.setPasswordHash(DefaultServiceFactory.getInstance().getUserService().getHashPassword(randomPassword));
        user.setAdmin(false);
        DefaultServiceFactory.getInstance().getUserService().insert(user);

        when(req.getParameter("login")).thenReturn(user.getLogin());
        when(req.getParameter("password")).thenReturn(randomPassword);
        when(req.getSessionWrapper(true)).thenReturn(sessionWrapper);

        assertThat(LoginCommand.getInstance().execute(req, null), is(ViewJsp.UserSpace.USER_JSP));
        verify(req, never()).setAttribute(eq("error"), any(String.class));
        verify(sessionWrapper).setUser(user);
    }

    @Test
    public void execute_Admin() throws Exception {
        User user = RandomEntities.getUser();
        String randomPassword = RandomEntities.getString();
        user.setPasswordHash(DefaultServiceFactory.getInstance().getUserService().getHashPassword(randomPassword));
        user.setAdmin(true);
        DefaultServiceFactory.getInstance().getUserService().insert(user);

        when(req.getParameter("login")).thenReturn(user.getLogin());
        when(req.getParameter("password")).thenReturn(randomPassword);
        when(req.getSessionWrapper(true)).thenReturn(sessionWrapper);

        assertThat(LoginCommand.getInstance().execute(req, null), is(ViewJsp.AdminSpace.ADMIN_JSP));
        verify(req, never()).setAttribute(eq("error"), any(String.class));
        verify(sessionWrapper).setUser(user);
    }

}