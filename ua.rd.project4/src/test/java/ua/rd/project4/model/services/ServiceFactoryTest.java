package ua.rd.project4.model.services;

import org.junit.Test;
import ua.rd.project4.model.services.impl.JdbcServiceFactory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ServiceFactoryTest {
    ServiceFactory serviceFactory = JdbcServiceFactory.getInstance();
    @Test
    public void getClientService() throws Exception {
        assertThat(serviceFactory.getClientService()==null,is(false));
    }

    @Test
    public void getUserService() throws Exception {
        assertThat(serviceFactory.getUserService()==null,is(false));
    }

    @Test
    public void getCarService() throws Exception {
        assertThat(serviceFactory.getCarService()==null,is(false));
    }

    @Test
    public void getCarFlowService() throws Exception {
        assertThat(serviceFactory.getCarFlowService()==null,is(false));
    }

    @Test
    public void getCarRequestService() throws Exception {
        assertThat(serviceFactory.getCarRequestService()==null,is(false));
    }

    @Test
    public void getInvoiceService() throws Exception {
        assertThat(serviceFactory.getInvoiceService()==null,is(false));
    }

}