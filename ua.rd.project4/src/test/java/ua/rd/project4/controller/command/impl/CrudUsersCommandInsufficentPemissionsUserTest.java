package ua.rd.project4.controller.command.impl;

import ua.rd.project4.controller.command.Command;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.RandomEntities;
import ua.rd.project4.model.exceptions.UniqueViolationException;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

public class CrudUsersCommandInsufficentPemissionsUserTest extends CrudGenericCommandInsufficentPemissionsTest {
    {
        user = RandomEntities.getUser();
        String randomPassword = RandomEntities.getString();
        user.setPasswordHash(JdbcServiceFactory.getInstance().getUserService().getHashPassword(randomPassword));
        user.setAdmin(false);
        try {
            JdbcServiceFactory.getInstance().getUserService().insert(user);
        } catch (UniqueViolationException e) {
            e.printStackTrace();
        }
    }

    @Override
    Command getCommand() {
        return CrudUsersCommand.getInstance();
    }


}