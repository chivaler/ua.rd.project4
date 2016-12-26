package ua.rd.project4.controller.exceptions;

public class RequiredParameterException extends InvalidParameterException {
    public RequiredParameterException(String message) {
        super(message);
    }
}
