package ua.rd.project4.controller.command;

import ua.rd.project4.controller.exceptions.InsufficientPermissions;
import ua.rd.project4.domain.User;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest req,
                   User user) throws InsufficientPermissions;
}
