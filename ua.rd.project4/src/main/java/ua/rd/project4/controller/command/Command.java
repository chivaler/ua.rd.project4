package ua.rd.project4.controller.command;

import ua.rd.project4.controller.exceptions.InsufficientPermissions;
import ua.rd.project4.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String execute(HttpServletRequest req,
                   HttpServletResponse resp, User user) throws InsufficientPermissions;
}
