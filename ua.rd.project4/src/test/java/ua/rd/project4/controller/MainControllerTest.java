package ua.rd.project4.controller;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;

import org.apache.commons.io.FileUtils;
import ua.rd.project4.controller.util.ViewJsp;

import java.io.PrintWriter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MainControllerTest {
    private final String tempFileName="test.txt";
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
    PrintWriter writer;

    @Before
    public void init() throws Exception {
        writer = new PrintWriter(tempFileName);
        when(response.getWriter()).thenReturn(writer);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void doPost404() throws Exception {
        writer = new PrintWriter(tempFileName);
        when(request.getParameter("command")).thenReturn("abracadabra");
        new MainController().doPost(request, response);
        verify(request, atLeast(1)).getRequestDispatcher(ViewJsp.General.ERROR_404);
        writer.flush();
        verify(request, atLeast(1)).getParameter("command");
    }

    @Test
    public void doPost404null() throws Exception {
        when(request.getParameter("command")).thenReturn(null);
        new MainController().doPost(request, response);
        writer.flush();
        verify(request, atLeast(1)).getRequestDispatcher(ViewJsp.General.ERROR_404);
        writer.flush();
        verify(request, atLeast(1)).getParameter("command");
    }

    @Test
    public void doPost_getCar() throws Exception {
        when(request.getParameter("command")).thenReturn("CARS");
        when(request.getParameter("do")).thenReturn("get");
        new MainController().doPost(request, response);
        writer.flush();
        verify(request, atLeast(1)).getRequestDispatcher(ViewJsp.CarsCrud.LIST_CARS_JSP);
        writer.flush();
        verify(request, atLeast(1)).getParameter("command");
    }

    @Test
    public void doPost() throws Exception {
        when(request.getParameter("command")).thenReturn("CARS");
        new MainController().doPost(request, response);
        writer.flush();
        verify(request, atLeast(1)).getRequestDispatcher(ViewJsp.General.ERROR_404);
        verify(request, atLeast(1)).setAttribute(eq("error"), any(String.class));
        writer.flush();
        verify(request, atLeast(1)).getParameter("command");
    }

}