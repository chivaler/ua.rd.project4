package ua.rd.project4.model.services;

import ua.rd.project4.model.dao.JdbcDaoFactory;
import ua.rd.project4.model.dao.UserDao;
import ua.rd.project4.domain.SystemUser;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<SystemUser> findUsersByClientId(int clientId) {
        return getDao().findUsersByClientId(clientId);
    }

    @Override
    public boolean insert(SystemUser systemUser) throws UniqueViolationException {
        if (checkExistUsersWithTheSameLogin(systemUser, 0))
            throw new LoginExistsException();
        return super.insert(systemUser);
    }

    @Override
    public boolean update(int id, SystemUser systemUser) throws UniqueViolationException {
        if (checkExistUsersWithTheSameLogin(systemUser, id))
            throw new LoginExistsException();
        return super.update(id, systemUser);
    }

    private boolean checkExistUsersWithTheSameLogin(SystemUser systemUser, int id) {
        return (findAll().stream().
                filter(s -> (s.getLogin().equals(systemUser.getLogin()))).
                filter(s -> (s.getId() != id)).
                count() > 0);

    }
}
