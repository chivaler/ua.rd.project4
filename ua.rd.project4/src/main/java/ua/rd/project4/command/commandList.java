package ua.rd.project4.command;

public enum commandList {
    GET_CLIENTS(cmdGetClients.getInstance()),
    GET_USERS(cmdGetUsers.getInstance()),
    EDIT_USER(cmdGetUsers.getInstance()),
    CRUD_USERS(cmdCrudUsers.getInstance());

    final Command command;

    public Command getCommand() {
        return command;
    }

    commandList(Command instance) {
        this.command = instance;
    }
}
