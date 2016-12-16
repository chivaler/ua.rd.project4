package ua.rd.project4.controller.command.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.rd.project4.controller.command.Command;
import ua.rd.project4.controller.command.CommandDispatcher;
import ua.rd.project4.controller.exceptions.InsufficientPermissionsException;
import ua.rd.project4.controller.exceptions.NotFoundException;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.domain.User;


public class CommandDispatcherImpl implements CommandDispatcher {
    private static final CommandDispatcherImpl instance = new CommandDispatcherImpl();
    private final Logger logger = LogManager.getLogger(CommandDispatcherImpl.class);

    private CommandDispatcherImpl() {
    }

    public static CommandDispatcherImpl getInstance() {
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
        REGISTER(UserRegisterCommand.getInstance()),
        PRINTINVOICE(PrintInvoiceCommand.getInstance()),
        PRINTCARFLOW(PrintCarFlowCommand.getInstance());

        final Command command;

        CommandMapping(Command instance) {
            this.command = instance;
        }

        public Command getCommand() {
            return command;
        }
    }

    @Override
    public String executeRequest(RequestWrapper requestWrapper) throws InsufficientPermissionsException, NotFoundException {
        User user = requestWrapper.getSessionWrapper(false).getUser();
        String commandName = requestWrapper.getParameter("command");
        try {
            Command command = CommandMapping.valueOf(commandName).getCommand();
            return command.execute(requestWrapper, user);
        } catch (InsufficientPermissionsException ipe) {
            throw ipe;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new NotFoundException();
        } catch (Exception exception) {
            logger.error(exception);
            throw new NotFoundException();
        }
    }

}
