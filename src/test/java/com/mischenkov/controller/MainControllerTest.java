package com.mischenkov.controller;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainControllerTest {

    @Test
    public void doGet() throws ServletException, IOException {
        MainController controller = Mockito.mock(MainController.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        controller.doGet(request, response);

        Assert.assertNotNull(controller);
    }
}