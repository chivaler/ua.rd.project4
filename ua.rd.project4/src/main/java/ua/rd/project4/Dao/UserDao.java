package ua.rd.project4.Dao;

import ua.rd.project4.entities.SystemUser;

import java.util.List;

public abstract class UserDao implements EntityDao<SystemUser> {
    public abstract List<SystemUser> findUsersByClientId(int clientId);
}
