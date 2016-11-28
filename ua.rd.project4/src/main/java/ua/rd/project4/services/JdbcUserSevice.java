package ua.rd.project4.services;

import ua.rd.project4.Dao.EntityDao;
import ua.rd.project4.Dao.JdbcDaoFactory;
import ua.rd.project4.Dao.UserDao;
import ua.rd.project4.entities.SystemUser;

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
        return ServiceFactory.getInstance();
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
