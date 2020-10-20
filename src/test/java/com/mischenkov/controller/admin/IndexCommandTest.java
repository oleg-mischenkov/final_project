package com.mischenkov.controller.admin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import java.io.IOException;

public class IndexCommandTest {

    public IndexCommand command;

    @Before
    public void initCommand() {
        command = Mockito.mock(IndexCommand.class);
    }

    @Test
    public void make() throws IOException, ServletException {
        command.make();
        Assert.assertNotNull(command);
    }
}