package ua.rd.project4.controller.util;

public class ErrorSetter {

    public enum JspOuterError {
        INSUFFICIENT_PERMISSIONS
    }

    public static void setOutputError(RequestWrapper req, JspOuterError error) {
        req.setAttribute("error", error.toString());
    }
}
