package ua.rd.project4.controller.command.impl;

import org.junit.Test;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.ViewJsp;
import ua.rd.project4.domain.User;
import ua.rd.project4.RandomEntities;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserSpaceCommandTest {
    private RequestWrapper req = mock(RequestWrapper.class);

    @Test
    public void execute_NULL() throws Exception {
        assertThat(UserSpaceCommand.getInstance().execute(req, null), is(ViewJsp.UserSpace.USER_JSP));
        verify(req, never()).setAttribute(eq("error"), any(String.class));
    }

    @Test
    public void execute_USER() throws Exception {
        User user = RandomEntities.getUser();
        String randomPassword = RandomEntities.getString();
        user.setPasswordHash(DefaultServiceFactory.getInstance().getUserService().getHashPassword(randomPassword));
        user.setAdmin(false);
        DefaultServiceFactory.getInstance().getUserService().insert(user);

        assertThat(UserSpaceCommand.getInstance().execute(req, user), is(ViewJsp.UserSpace.USER_JSP));
        verify(req, never()).setAttribute(eq("error"), any(String.class));
    }

    @Test
    public void execute_ADMIN() throws Exception {
        User user = RandomEntities.getUser();
        String randomPassword = RandomEntities.getString();
        user.setPasswordHash(DefaultServiceFactory.getInstance().getUserService().getHashPassword(randomPassword));
        user.setAdmin(true);
        DefaultServiceFactory.getInstance().getUserService().insert(user);

        assertThat(UserSpaceCommand.getInstance().execute(req, user), is(ViewJsp.UserSpace.USER_JSP));
        verify(req, never()).setAttribute(eq("error"), any(String.class));
    }

}