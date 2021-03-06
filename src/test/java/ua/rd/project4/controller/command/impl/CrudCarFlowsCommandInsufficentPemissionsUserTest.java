package ua.rd.project4.controller.command.impl;

import ua.rd.project4.controller.command.Command;
import ua.rd.project4.RandomEntities;
import ua.rd.project4.model.exceptions.UniqueViolationException;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

public class CrudCarFlowsCommandInsufficentPemissionsUserTest extends CrudGenericCommandInsufficentPemissionsTest {
    {
        user = RandomEntities.getUser();
        String randomPassword = RandomEntities.getString();
        user.setPasswordHash(DefaultServiceFactory.getInstance().getUserService().getHashPassword(randomPassword));
        user.setAdmin(false);
        try {
            DefaultServiceFactory.getInstance().getUserService().insert(user);
        } catch (UniqueViolationException e) {
            e.printStackTrace();
        }
    }

    @Override
    Command getCommand() {
        return CrudCarFlowCommand.getInstance();
    }
}