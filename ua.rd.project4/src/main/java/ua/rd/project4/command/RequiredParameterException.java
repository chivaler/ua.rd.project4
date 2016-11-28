package ua.rd.project4.command;

class RequiredParameterException extends InvalidParameterException {
    public RequiredParameterException(String message) {
        super(message);
    }
}
