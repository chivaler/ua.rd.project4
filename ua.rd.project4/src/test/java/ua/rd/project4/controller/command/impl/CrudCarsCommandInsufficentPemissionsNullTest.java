package ua.rd.project4.controller.command.impl;

import ua.rd.project4.controller.command.Command;

public class CrudCarsCommandInsufficentPemissionsNullTest extends CrudGenericCommandInsufficentPemissionsTest {
    @Override
    Command getCommand() {
        return CrudCarsCommand.getInstance();
    }
}