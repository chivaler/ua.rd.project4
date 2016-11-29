package ua.rd.project4.controller.command;

public enum commandList {
    USERS(cmdCrudUsers.getInstance()),
    CLIENTS(cmdCrudClients.getInstance()),
    CARS(cmdCrudCars.getInstance()),
    CARFLOWS(cmdCrudCarFlow.getInstance()),
    CARREQUESTS(cmdCrudCarRequest.getInstance()),
    INVOICES(cmdCrudInvoice.getInstance());

    final Command command;

    commandList(Command instance) {
        this.command = instance;
    }

    public Command getCommand() {
        return command;
    }


}
