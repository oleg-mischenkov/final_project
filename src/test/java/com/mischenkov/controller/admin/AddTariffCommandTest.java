package com.mischenkov.controller.admin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import java.io.IOException;

public class AddTariffCommandTest {

    public AddTariffCommand command;

    @Before
    public void initCommand() {
        command = Mockito.mock(AddTariffCommand.class);
    }

    @Test
    public void postMake() throws IOException, ServletException {
        command.postMake();
        Assert.assertNotNull(command);
    }

    @Test
    public void getMake() throws IOException, ServletException {
        command.getMake();
        Assert.assertNotNull(command);
    }
}