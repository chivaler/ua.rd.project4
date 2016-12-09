package ua.rd.project4.controller.exceptions;

public class ExceptionRequiredFieldEmptyException extends Exception{
    public ExceptionRequiredFieldEmptyException(String message) {
        super(message);
    }
}
