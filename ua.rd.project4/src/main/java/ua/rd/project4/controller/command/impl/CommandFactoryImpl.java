package ua.rd.project4.controller.command.impl;

import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.command.CommandFactory;
import ua.rd.project4.controller.util.ViewJsp;


public class CommandFactoryImpl implements CommandFactory {
    private static final CommandFactoryImpl instance = new CommandFactoryImpl();

    private CommandFactoryImpl() {
    }

    public static CommandFactoryImpl getInstance() {
        return instance;
    }

    enum CommandMapping {
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

    @Override
    public Command getCommandByName(String commandName) {
        try {
            return CommandMapping.valueOf(commandName).getCommand();
        } catch (Exception e) {
            return UserSpaceCommand.getInstance();
        }
    }

    @Override
    public String getFallbackUrl() {
        return ViewJsp.General.LOGIN_PAGE;
    }
}
