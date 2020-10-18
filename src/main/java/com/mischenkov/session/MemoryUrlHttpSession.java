package com.mischenkov.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.*;

public class MemoryUrlHttpSession implements HttpSession {

    public static final int URL_MAX_CAPACITY = 5;

    private HttpSession httpSession;

    public MemoryUrlHttpSession(HttpSession httpSession) {
        this.httpSession = Objects.requireNonNull(httpSession,
                "MemoryUrlHttpSession(HttpSession httpSession), \"httpSession\" is null.");

        Enumeration<String> nameEnumeration = httpSession.getAttributeNames();
        boolean flag = false;
        while ( nameEnumeration.hasMoreElements() ) {
            String currentSessionValueName = nameEnumeration.nextElement();
            String expectedName = SessionVariable.SESSION_URL_SCOPE;

            if ( expectedName.equals(currentSessionValueName) ) {
                flag = true;
            }
        }

        if ( !flag ) {
            Deque<String> urlDeque = new LinkedList<>();
            httpSession.setAttribute(
                    SessionVariable.SESSION_URL_SCOPE,
                    urlDeque );
        }

    }


    /**
     * Метод добавляет в очередь url с которого поступил запрос на сервлет. Если очередь содержит больше урлов, чем
     * URL_MAX_CAPACITY (по умолчанию 5) то метод удаляет последний элемент в очереди и вставляет новый в ее голову.
     *
     * @param url строка содержащая урл. Может бросить NPE если УРЛ является нулом.
     */
    public void putUrl(String url) {
        Objects.requireNonNull(url, "putUrl(String url), \"url\" is null.");

        Deque<String> urlDeque = (Deque<String>) httpSession.getAttribute( SessionVariable.SESSION_URL_SCOPE );

        int dequeSize = urlDeque.size();
        if ( dequeSize == URL_MAX_CAPACITY ) {
            urlDeque.pollLast();
        }

        urlDeque.addFirst(url);
    }

    /**
     * Метод возвращает последний посещенный урл или нул если список урлов пуст.
     *
     * @return
     */
    public String getUrl() {
        Deque<String> urlDeque = (Deque<String>) httpSession.getAttribute( SessionVariable.SESSION_URL_SCOPE );
        return urlDeque.pollFirst();
    }

    public String getPreviousUrl() {
        List<String> urlList = (List<String>) httpSession.getAttribute( SessionVariable.SESSION_URL_SCOPE );
        String result = httpSession.getServletContext().getContextPath();

        if (urlList.size() > 1) {
            result = urlList.get(1);
        }

        return result;
    }

    @Override
    public long getCreationTime() {
        return httpSession.getCreationTime();
    }

    @Override
    public String getId() {
        return httpSession.getId();
    }

    @Override
    public long getLastAccessedTime() {
        return httpSession.getLastAccessedTime();
    }

    @Override
    public ServletContext getServletContext() {
        return httpSession.getServletContext();
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        httpSession.setMaxInactiveInterval(interval);
    }

    @Override
    public int getMaxInactiveInterval() {
        return httpSession.getMaxInactiveInterval();
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return httpSession.getSessionContext();
    }

    @Override
    public Object getAttribute(String name) {
        return httpSession.getAttribute(name);
    }

    @Override
    public Object getValue(String name) {
        return httpSession.getValue(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return httpSession.getAttributeNames();
    }

    @Override
    public String[] getValueNames() {
        return httpSession.getValueNames();
    }

    @Override
    public void setAttribute(String name, Object value) {
        httpSession.setAttribute(name, value);
    }

    @Override
    public void putValue(String name, Object value) {
        httpSession.putValue(name, value);
    }

    @Override
    public void removeAttribute(String name) {
        httpSession.removeAttribute(name);
    }

    @Override
    public void removeValue(String name) {
        httpSession.removeValue(name);
    }

    @Override
    public void invalidate() {
        httpSession.invalidate();
    }

    @Override
    public boolean isNew() {
        return httpSession.isNew();
    }
}
