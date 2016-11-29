package ua.rd.project4.model.services;

import ua.rd.project4.model.dao.JdbcDaoFactory;
import ua.rd.project4.model.dao.UserDao;
import ua.rd.project4.domain.User;

import java.util.List;

class JdbcUserSevice extends UserService {
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
    AbstractServiceFactory getServiceFactory() {
        return JdbcServiceFactory.getInstance();
    }

    @Override
    public boolean delete(int id) throws ExceptionEntityInUse {
        return super.delete(id);
    }

    @Override
    public List<User> findUsersByClientId(int clientId) {
        return getDao().findUsersByClientId(clientId);
    }

    @Override
    public boolean insert(User user) throws UniqueViolationException {
        if (checkExistUsersWithTheSameLogin(user, 0))
            throw new LoginExistsException();
        return super.insert(user);
    }

    @Override
    public boolean update(int id, User user) throws UniqueViolationException {
        if (checkExistUsersWithTheSameLogin(user, id))
            throw new LoginExistsException();
        return super.update(id, user);
    }

    private boolean checkExistUsersWithTheSameLogin(User user, int id) {
        return findAll().stream().
                filter(s -> s.getLogin().equals(user.getLogin())).
                filter(s -> s.getId() != id).
                count() > 0;

    }
}