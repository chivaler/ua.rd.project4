package ua.rd.project4.controller.command.impl;

import org.junit.Test;
import ua.rd.project4.controller.util.JspMessagesSetter;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.controller.util.SessionWrapper;
import ua.rd.project4.controller.util.ViewJsp;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class LocalizationCommandTest {
    private RequestWrapper req = mock(RequestWrapper.class);
    private SessionWrapper sessionWrapper = mock(SessionWrapper.class);

    @Test
    public void execute_NULL() throws Exception {
        when(req.getSessionWrapper(true)).thenReturn(sessionWrapper);
        when(req.getParameter("language")).thenReturn(null);
        assertThat(LocalizationCommand.getInstance().execute(req, null), is(ViewJsp.General.LOGIN_PAGE));
        verify(req, never()).setAttribute(eq("error"), any(String.class));
        verify(sessionWrapper).setLanguage("uk_UA");
    }

    @Test
    public void execute_ENG() throws Exception {
        when(req.getSessionWrapper(true)).thenReturn(sessionWrapper);
        when(req.getParameter("language")).thenReturn("ENG");
        assertThat(LocalizationCommand.getInstance().execute(req, null), is(ViewJsp.General.LOGIN_PAGE));
        verify(req, never()).setAttribute(eq("error"), any(String.class));
        verify(sessionWrapper).setLanguage("en_US");
    }

    @Test
    public void execute_UA() throws Exception {
        when(req.getSessionWrapper(true)).thenReturn(sessionWrapper);
        when(req.getParameter("language")).thenReturn("UKR");
        assertThat(LocalizationCommand.getInstance().execute(req, null), is(ViewJsp.General.LOGIN_PAGE));
        verify(req, never()).setAttribute(eq("error"), any(String.class));
        verify(sessionWrapper).setLanguage("uk_UA");
    }

}