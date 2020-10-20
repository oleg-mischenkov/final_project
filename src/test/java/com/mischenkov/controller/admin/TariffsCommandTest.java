package com.mischenkov.controller.admin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import java.io.IOException;

public class TariffsCommandTest {


    public TariffsCommand command;

    @Before
    public void initCommand() {
        command = Mockito.mock(TariffsCommand.class);
    }

    @Test
    public void getMake() throws IOException, ServletException {
        command.getMake();
        Assert.assertNotNull(command);
    }
}