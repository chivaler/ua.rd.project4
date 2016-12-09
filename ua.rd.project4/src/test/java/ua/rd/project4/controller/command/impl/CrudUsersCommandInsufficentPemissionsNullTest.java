package ua.rd.project4.controller.command.impl;

import ua.rd.project4.controller.command.Command;
import ua.rd.project4.domain.User;

public class CrudUsersCommandInsufficentPemissionsNullTest extends CrudGenericCommandInsufficentPemissionsTest {
    @Override
    Command getCommand() {
        return CrudUsersCommand.getInstance();
    }
}