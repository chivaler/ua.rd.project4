package ua.rd.project4.controller.command.impl;

import org.junit.Test;
import ua.rd.project4.controller.command.CommandDispatcher;
import ua.rd.project4.controller.util.ViewJsp;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CommandDispatcherImplTest {
    CommandDispatcher commandDispatcher = CommandDispatcherImpl.getInstance();

    @Test
    public void getFallbackPermissionsUrl() throws Exception {
        assertThat(commandDispatcher.getFallbackPermissionsUrl(),is(ViewJsp.UserSpace.USER_JSP));
    }

}