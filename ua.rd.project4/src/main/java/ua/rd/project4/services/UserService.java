package ua.rd.project4.services;

import ua.rd.project4.entities.SystemUser;

import java.util.List;

public abstract class UserService extends EntityService<SystemUser> {
    public abstract List<SystemUser> findUsersByClientId(int clientId);
}
