package ua.rd.project4.controller.exceptions;

public class RequestAttributeNotPermittedException extends RuntimeException {
    public RequestAttributeNotPermittedException(String message) {
        super(message);
    }
}
