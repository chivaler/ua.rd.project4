package ua.rd.project4.model.services.impl;

import ua.rd.project4.model.dao.impl.JdbcDaoFactory;
import ua.rd.project4.model.dao.UserDao;
import ua.rd.project4.domain.User;
import ua.rd.project4.model.exceptions.LoginExistsException;
import ua.rd.project4.model.exceptions.UniqueViolationException;
import ua.rd.project4.model.services.*;

import java.util.List;

class JdbcUserSevice extends GenericEntityService<User> implements UserService {
    private static final JdbcUserSevice instance = new JdbcUserSevice();

    private JdbcUserSevice() {
    }

    public static JdbcUserSevice getInstance() {
        return instance;
    }

    @Override
    UserDao getDao() {
        return JdbcDaoFactory.getInstance().getUserDao();
    }

    @Override
    ServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    @Override
    public List<User> findUsersByClientId(int clientId) {
        return getDao().findUsersByClientId(clientId);
    }

    @Override
    public User authentication(String login, String password) {
        User user = getDao().getByLogin(login);
        if (user!=null && user.getPasswordHash().equals(getHashPassword(password)))
            return user;
        return null;
    }

    @Override
    public boolean insert(User user) throws UniqueViolationException {
        if (getDao().getByLogin(user.getLogin()) != null)
            throw new LoginExistsException();
        return super.insert(user);
    }

    @Override
    public boolean update(int id, User user) throws UniqueViolationException {
        User sqlUser = getDao().getByLogin(user.getLogin());
        if (sqlUser == null || sqlUser.getId() != user.getId())
            throw new LoginExistsException();
        return super.update(id, user);
    }
}
