package ua.rd.project4.services;

import ua.rd.project4.Dao.EntityDao;
import ua.rd.project4.Dao.JdbcDaoFactory;
import ua.rd.project4.entities.SystemUser;

class JdbcUserSevice extends UserService {
    private static final JdbcUserSevice instance = new JdbcUserSevice();
    private JdbcUserSevice() {
    }

    public static JdbcUserSevice getInstance() {
        return instance;
    }

    @Override
    EntityDao<SystemUser> getDao() {
        return JdbcDaoFactory.getInstance().getUserDao();
    }

    @Override
    public boolean delete(int id) throws ExceptionEntityInUse {
        return super.delete(id);
    }
}
