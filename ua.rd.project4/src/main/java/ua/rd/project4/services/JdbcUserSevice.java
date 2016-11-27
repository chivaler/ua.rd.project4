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
}
