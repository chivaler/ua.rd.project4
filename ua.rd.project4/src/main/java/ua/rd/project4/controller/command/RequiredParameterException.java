package ua.rd.project4.controller.command;

class RequiredParameterException extends InvalidParameterException {
    public RequiredParameterException(String message) {
        super(message);
    }
}
