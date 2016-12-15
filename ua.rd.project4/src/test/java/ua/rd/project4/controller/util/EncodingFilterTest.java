package ua.rd.project4.controller.util;

import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class EncodingFilterTest {
    private final String tempFileName="test.txt";
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
    PrintWriter writer;

    @Test
    public void doFilter() throws Exception {

    }

}