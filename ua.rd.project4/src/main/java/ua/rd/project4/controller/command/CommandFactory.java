package ua.rd.project4.controller.command;

public interface CommandFactory {
    Command getCommandByName(String commandName);
    Command getFallbackCommand();
}
