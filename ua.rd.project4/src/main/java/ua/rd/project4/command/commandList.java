package ua.rd.project4.command;

public enum commandList {
    SHOW_CLIENTS(GetAllClients.getInstance(), "jsp/admin/clients.jsp");

    Command command;
    String url;

    public Command getCommand() {
        return command;
    }

    commandList(Command instance, String url) {
        this.command = instance;
        this.url = url;
    }
}
