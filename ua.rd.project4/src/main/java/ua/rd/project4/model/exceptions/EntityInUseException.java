package ua.rd.project4.model.exceptions;

public class EntityInUseException extends Exception {
    public EntityInUseException(String message) {
        super(message);
    }
}
