package ua.rd.project4.controller.command.impl;

import ua.rd.project4.controller.command.Command;

public class CrudUsersCommandInsufficentPemissionsNullTest extends CrudGenericCommandInsufficentPemissionsTest {
    @Override
    Command getCommand() {
        return CrudUsersCommand.getInstance();
    }
}