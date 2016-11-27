package ua.rd.project4.command;

public enum commandList {
    SHOW_CLIENTS(GetAllClients.getInstance());

    final Command command;

    public Command getCommand() {
        return command;
    }

    commandList(Command instance) {
        this.command = instance;
    }
}
