package com.mischenkov.listener;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.User;
import com.mischenkov.model.dao.user.ExtendedUserDao;
import com.mischenkov.model.dao.user.MySqlUserDao;
import com.mischenkov.model.dbservice.*;
import com.mischenkov.model.exception.DBException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.stream.Collectors;

import static com.mischenkov.listener.ContextVariable.CONTEXT_INIT_DEFAULT_LANG;

public class ContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute(CONTEXT_INIT_DEFAULT_LANG, servletContext.getInitParameter(CONTEXT_INIT_DEFAULT_LANG));

        initLog4J(servletContext);
        languageInit(servletContext);

        moneyTakerDemon(servletContext);
    }

    private void moneyTakerDemon(ServletContext servletContext) {

        Runnable moneyDemon = ()-> {
            while (true) {

                try {
                    UserDbService userDbService = new MySqlUserDbService();
                    List<User> userList = userDbService.getAll();

                    OrderDbService orderDbService = new MySqlOrderDbService();

                    for (User user: userList) {
                        orderDbService.takeMoney(user);
                    }

                } catch (DBException e) {
                    LOG.warn("There is cant to take some money from User wallets.", e);
                    throw new RuntimeException("There is cant to take some money from User wallets.", e);
                }

                try {
                    // 1000 * 60 * 60 * 24 = 86_400_000 (OR 24h)
                    Thread.sleep(86_400_000);
                } catch (InterruptedException e) {
                    LOG.warn("The Demon was broken.", e);
                }
            }
        };

        Thread thread = new Thread(moneyDemon);
        thread.setDaemon(true);
        thread.start();

    }

    private void initLog4J(ServletContext servletContext) {
        PropertyConfigurator.configure(
                servletContext.getRealPath("WEB-INF/log4j.properties")
        );
        LOG.info("Log4j init");
    }

    private void languageInit(ServletContext servletContext) {

        try {
            MySqlLanguageDbService service = new MySqlLanguageDbService();
            List<Language> languageList = service.getAll()
                    .stream()
                    .filter(elem -> elem.isActive())
                    .collect(Collectors.toList());

            servletContext.setAttribute(ContextVariable.CONTEXT_CURRENT_LANGS, languageList);

        } catch (DBException e) {
            LOG.warn("languageInit(ServletContext servletContext) caused an exception", e);
        }

        LOG.info("Language init");
    }
}
