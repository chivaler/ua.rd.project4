package ua.rd.project4.controller.command.impl;

import ua.rd.project4.controller.command.Command;

public enum CommandMapping {
    USERS(CrudUsersCommand.getInstance()),
    CLIENTS(CrudClientsCommand.getInstance()),
    CARS(CrudCarsCommand.getInstance()),
    CARFLOWS(CrudCarFlowCommand.getInstance()),
    CARREQUESTS(CrudCarRequestCommand.getInstance()),
    INVOICES(CrudInvoiceCommand.getInstance()),
    ADMIN(AdminCommand.getInstance()),
    LOCALE(LocalizationCommand.getInstance()),
    LOGIN(LoginCommand.getInstance()),
    LOGOUT(LogoutCommand.getInstance()),
    CAR_IN(CarInCommand.getInstance()),
    CAR_OUT(CarOutCommand.getInstance()),
    USERSPACE(UserSpaceCommand.getInstance()),
    CARSAVAILABLE(CarsAvailableCommand.getInstance()),
    CREATECARREQUEST(UserCreateCarRequestCommand.getInstance()),
    REGISTER(UserRegisterCommand.getInstance());

    final Command command;

    CommandMapping(Command instance) {
        this.command = instance;
    }

    public Command getCommand() {
        return command;
    }


}
