package com.mischenkov.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterControllerTest {

    public RegisterController controller;
    public HttpServletRequest request;
    public HttpServletResponse response;

    @Before
    public void initController() {
        controller = Mockito.mock(RegisterController.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        controller.doGet(request, response);
        Assert.assertNotNull(controller);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        controller.doPost(request, response);
        Assert.assertNotNull(controller);
    }
}