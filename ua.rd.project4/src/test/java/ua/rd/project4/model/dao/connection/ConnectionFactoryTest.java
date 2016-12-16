package ua.rd.project4.model.dao.connection;

import org.junit.Test;
import ua.rd.project4.model.dao.connection.impl.JdbcConnectionFactory;

import java.sql.Connection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ConnectionFactoryTest {
    @Test
    public void getConnection() throws Exception {
        assertThat(JdbcConnectionFactory.getInstance().getConnection()==null,is(false));
    }


}