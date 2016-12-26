package ua.rd.project4.model.dao;

import ua.rd.project4.domain.User;

import java.util.List;

public interface UserDao extends EntityDao<User> {
    List<User> findUsersByClientId(int clientId);

    User getByLogin(String login);
}
