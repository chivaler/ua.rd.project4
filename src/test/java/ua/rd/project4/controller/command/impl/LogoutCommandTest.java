package ua.rd.project4.controller.command.impl;

import org.junit.Test;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.SessionWrapper;
import ua.rd.project4.controller.util.ViewJsp;
import ua.rd.project4.domain.User;
import ua.rd.project4.RandomEntities;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LogoutCommandTest {
    private RequestWrapper req = mock(RequestWrapper.class);
    private SessionWrapper sessionWrapper = mock(SessionWrapper.class);

    @Test
    public void execute() throws Exception {
        User user = RandomEntities.getUser();
        String randomPassword = RandomEntities.getString();
        user.setPasswordHash(DefaultServiceFactory.getInstance().getUserService().getHashPassword(randomPassword));
        user.setAdmin(false);
        DefaultServiceFactory.getInstance().getUserService().insert(user);

        when(req.getSessionWrapper()).thenReturn(sessionWrapper);

        assertThat(LogoutCommand.getInstance().execute(req, user), is(ViewJsp.General.LOGIN_PAGE));
        verify(req, never()).setAttribute(eq("error"), any(String.class));
        verify(sessionWrapper).invalidate();
    }

    @Test
    public void execute_blank() throws Exception {
        when(req.getSessionWrapper()).thenReturn(sessionWrapper);
        assertThat(LogoutCommand.getInstance().execute(req, null), is(ViewJsp.General.LOGIN_PAGE));
        verify(req, never()).setAttribute(eq("error"), any(String.class));
        verify(sessionWrapper).invalidate();
    }

}