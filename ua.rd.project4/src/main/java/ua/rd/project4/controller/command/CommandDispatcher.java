package ua.rd.project4.controller.command;

import ua.rd.project4.controller.exceptions.InsufficientPermissionsException;
import ua.rd.project4.controller.exceptions.NotFoundException;
import ua.rd.project4.controller.util.RequestWrapper;

public interface CommandDispatcher {
    String executeRequest(RequestWrapper requestWrapper) throws InsufficientPermissionsException, NotFoundException;
}
