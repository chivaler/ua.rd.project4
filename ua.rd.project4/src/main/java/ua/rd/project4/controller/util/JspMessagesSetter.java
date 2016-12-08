package ua.rd.project4.controller.util;

public class JspMessagesSetter {

    public enum JspError {
        WRONG_LOGIN,
        INSUFFICIENT_PERMISSIONS,
        PAYMENT_NEEDED,
        APPROVE_NEEDED,
        APPROVE_CONFLICTS,
        WRONG_CAR_DIRECTION,
        UNKNOWN_ID,
        UNKNOWN_COMMAND,
        LOGIN_ALREADY_EXIST,
        FIELD_EMPTY_REQUIRED,
        FIELD_WRONG_DATA,
        COULDNT_REMOVE_ID,
    }

    public enum JspResult {
        ID_REMOVED,
    }

    public static void setOutputError(RequestWrapper req, JspError error) {
        req.setAttribute("error", error.toString());
    }

    public static void setOutputError(RequestWrapper req, JspError error, String message) {
        req.setAttribute("error", error.toString());
        req.setAttribute("error_message", message);
    }

    public static void setOutputMessage(RequestWrapper req, JspResult result) {
        req.setAttribute("result", result.toString());
    }

    public static void setOutputMessage(RequestWrapper req, JspResult result, String message) {
        req.setAttribute("result", result.toString());
        req.setAttribute("result", message);
    }


}
