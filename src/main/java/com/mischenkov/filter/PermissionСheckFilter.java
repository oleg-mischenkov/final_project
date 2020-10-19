package com.mischenkov.filter;


import com.mischenkov.entity.Permission;
import com.mischenkov.entity.User;
import com.mischenkov.model.exception.DBException;
import com.mischenkov.model.dbservice.MySqlUserDbService;
import com.mischenkov.model.dbservice.UserDbService;
import com.mischenkov.session.SessionVariable;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *  The filter checks the restrictions for the user.
 */
public class PermissionСheckFilter extends HttpFilter {

    private static final Logger LOG = Logger.getLogger(PermissionСheckFilter.class);

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {

        Permission userPerm = new Permission();

        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute(SessionVariable.SESSION_USER_ATTR);

        if (user != null) {
            UserDbService service = new MySqlUserDbService();
            int userId = user.getId();

            try {
                userPerm = service.getPermissionByUserId(userId);
            } catch (DBException e) {
                e.printStackTrace();
            }

            httpSession.setAttribute(SessionVariable.SESSION_USER_PERMISSION, userPerm);
        }

        chain.doFilter(req, resp);
    }
}
