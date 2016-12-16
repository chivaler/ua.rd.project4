package ua.rd.project4.controller.command.impl;

import ua.rd.project4.controller.command.Command;

public class CrudInvoicesCommandInsufficentPemissionsNullTest extends CrudGenericCommandInsufficentPemissionsTest {
    @Override
    Command getCommand() {
        return CrudInvoiceCommand.getInstance();
    }
}