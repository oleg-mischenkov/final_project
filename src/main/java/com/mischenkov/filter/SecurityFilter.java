package com.mischenkov.filter;

import com.mischenkov.entity.Permission;
import com.mischenkov.entity.User;
import com.mischenkov.model.validation.Validator;
import com.mischenkov.session.SessionVariable;
import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SecurityFilter extends HttpFilter {

    private static final Logger LOG = Logger.getLogger(SecurityFilter.class);

    private static final String SECURITY_PATH_ADMIN = "/admin";
    private static final String SECURITY_PATH_ACCOUNT = "/account";
    private static final String SECURITY_PATH_CART = "/cart";

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String servletPath = req.getServletPath();
        HttpSession session = req.getSession();

        User user = getCurrentUser(session);
        Permission permission = getCurrentPermission(session);

        Validator validator = new Validator();
        Validator.Command invalidateDirective = () -> {
            LOG.info("Run invalidateDirective");
            req.getRequestDispatcher("error").forward(req, resp);
        };

        switch (servletPath) {
            case SECURITY_PATH_ADMIN :
                LOG.info(SECURITY_PATH_ADMIN);
                validator
                        .append( permission.isAdminAccess() )
                        .ifInvalidate( invalidateDirective );
                break;

            case SECURITY_PATH_ACCOUNT :
                LOG.info(SECURITY_PATH_ACCOUNT);
                validator
                        .append( permission.isCardAccess() )
                        .ifInvalidate( invalidateDirective );
                break;

            case SECURITY_PATH_CART :
                LOG.info(SECURITY_PATH_CART);
                validator
                        .append( user.getId() != 0 )
                        .ifInvalidate( invalidateDirective );
                break;
        }

        chain.doFilter(req, resp);
    }

    private Permission getCurrentPermission(HttpSession session) {
        Permission permission = (Permission) session.getAttribute(SessionVariable.SESSION_USER_PERMISSION);
        
        if (permission == null) {
            return new Permission();
        }
        
        return permission;
    }

    private User getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute(SessionVariable.SESSION_USER_ATTR);
        
        if ( user == null ) {
            return new User();
        }
        
        return user;
    }
}
