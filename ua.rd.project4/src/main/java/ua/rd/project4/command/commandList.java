package ua.rd.project4.command;

public enum commandList {
    USERS(cmdCrudUsers.getInstance()),
    CLIENTS(cmdCrudClients.getInstance());

    final Command command;

    public Command getCommand() {
        return command;
    }

    commandList(Command instance) {
        this.command = instance;
    }
}
