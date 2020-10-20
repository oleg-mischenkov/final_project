package com.mischenkov.listener;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletContextEvent;

import static org.junit.Assert.*;

public class ContextListenerTest {

    @Test
    public void contextInitialized() {

        ServletContextEvent servletContextEvent = Mockito.mock(ServletContextEvent.class);
        ContextListener listener = Mockito.mock(ContextListener.class);

        listener.contextInitialized(servletContextEvent);

        Assert.assertNotNull(listener);

    }
}