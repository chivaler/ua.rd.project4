package ua.rd.project4.controller.command.impl;

import ua.rd.project4.controller.command.Command;

public class CrudCarFlowsCommandInsufficentPemissionsNullTest extends CrudGenericCommandInsufficentPemissionsTest {
    @Override
    Command getCommand() {
        return CrudCarFlowCommand.getInstance();
    }

}