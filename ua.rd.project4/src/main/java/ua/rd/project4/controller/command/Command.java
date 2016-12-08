package ua.rd.project4.controller.command;

import ua.rd.project4.controller.exceptions.InsufficientPermissions;
import ua.rd.project4.controller.util.RequestWrapper;
import ua.rd.project4.domain.User;

public interface Command {
    String execute(RequestWrapper req,
                   User user) throws InsufficientPermissions;
}
