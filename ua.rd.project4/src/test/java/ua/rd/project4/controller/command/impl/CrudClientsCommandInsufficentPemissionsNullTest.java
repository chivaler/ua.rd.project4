package ua.rd.project4.controller.command.impl;

import ua.rd.project4.controller.command.Command;
import ua.rd.project4.domain.User;

public class CrudClientsCommandInsufficentPemissionsNullTest extends CrudGenericCommandInsufficentPemissionsTest {
    @Override
    Command getCommand() {
        return CrudClientsCommand.getInstance();
    }
}