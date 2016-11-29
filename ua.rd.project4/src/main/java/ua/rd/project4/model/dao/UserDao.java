package ua.rd.project4.model.dao;

import ua.rd.project4.domain.SystemUser;

import java.util.List;

public abstract class UserDao implements EntityDao<SystemUser> {
    public abstract List<SystemUser> findUsersByClientId(int clientId);
}
