package ua.rd.project4.controller.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RentHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public RentHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }


}
