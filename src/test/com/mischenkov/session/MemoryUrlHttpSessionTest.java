package com.mischenkov.session;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpSession;
import java.util.Deque;
import java.util.LinkedList;

public class MemoryUrlHttpSessionTest {

    private MemoryUrlHttpSession memorySession;
    private static final String[] URLS = {"/1.jsp", "/2.jsp", "/3.jsp", "/4.jsp", "/5.jsp", "/6.jsp"};

    @Before
    public void initSession() {
        Deque<String> urlDeque = new LinkedList<>();

        HttpSession httpSession = Mockito.mock(HttpSession.class);
        Mockito.when(httpSession.getAttribute( SessionVariable.SESSION_URL_SCOPE )).then(invocation -> urlDeque);

        memorySession = new MemoryUrlHttpSession(httpSession);
    }

    @Test(expected = NullPointerException.class)
    public void shouldPutUrlTest() {
        for (String element: URLS) {
            memorySession.putUrl(element);
        }

        Deque<String> resultUrlDeque = (Deque<String>) memorySession.getAttribute( SessionVariable.SESSION_URL_SCOPE );

        int expectedSize = MemoryUrlHttpSession.URL_MAX_CAPACITY;
        Assert.assertEquals(expectedSize, resultUrlDeque.size());

        // NPE
        memorySession.putUrl(null);
     }

     @Test
     public void shouldGetUrlAtEmptyDequeTest() {
        Assert.assertNull( memorySession.getUrl() );
     }

     @Test
     public void shouldGetUrlTest() {
         for (String element: URLS) {
             memorySession.putUrl(element);
         }

         String url = null;

         while ( (url = memorySession.getUrl()) != null ) {
             System.out.println(url);
         }

     }

}
