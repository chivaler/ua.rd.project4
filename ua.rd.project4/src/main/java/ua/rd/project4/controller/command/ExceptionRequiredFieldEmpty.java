package ua.rd.project4.controller.command;

class ExceptionRequiredFieldEmpty extends Exception{
    public ExceptionRequiredFieldEmpty(String message) {
        super(message);
    }
}
