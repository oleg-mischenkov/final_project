package com.mischenkov.controller.admin;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.*;

public class AddServiceCommandTest {

    public AddServiceCommand command;

    @Before
    public void initCommand() {
        command = Mockito.mock(AddServiceCommand.class);
    }

    @Test
    public void postMake() throws IOException, ServletException {
        command.postMake();
    }

    @Test
    public void getMake() throws IOException, ServletException {
        command.getMake();
    }
}