package ua.rd.project4.command;

public enum commandList {
    GET_CLIENTS(GetAllClients.getInstance()),
    GET_USERS(GetAllClients.getInstance());;

    final Command command;

    public Command getCommand() {
        return command;
    }

    commandList(Command instance) {
        this.command = instance;
    }
}
