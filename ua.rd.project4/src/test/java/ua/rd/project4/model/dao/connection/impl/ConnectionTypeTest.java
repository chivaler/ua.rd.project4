package ua.rd.project4.model.dao.connection.impl;

import org.junit.Test;
import ua.rd.project4.model.dao.connection.ConnectionFactory;

import java.sql.Connection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ConnectionTypeTest {
    @Test
    public void getConnection() throws Exception {
        assertThat(JdbcConnectionFactory.getInstance().getConnection()==null,is(false));
    }

    @Test
    public void DatasourcePoolTest() throws Exception {
        ConnectionFactory connectionFactory = JdbcConnectionFactory.getInstance();
        Connection connection = connectionFactory.getConnection();
        assertNotNull(connection);
        assertThat(JdbcConnectionFactory.getInstance().connectionType,is(JdbcConnectionFactory.ConnectionType.POOL_PREFERRED));
    }

}