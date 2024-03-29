package dmit2015.security;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

/**
 * This class is contains the initialized CallerUser objects used for authentication
 *
 * @author Rendo Ruiz
 * @version 2021.03.28
 *
 */
@WebListener
public class ApplicationStartupListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(ApplicationStartupListener.class.getName());

    @Inject
    CallerUserRepository callerUserRepository;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (callerUserRepository.list().size() == 0) {
            logger.info("Creating default accounts for application");
            CallerUser student01 = new CallerUser();
            student01.setUsername("stud01@dmit2015.ca");
            callerUserRepository.add(student01, "Password2015", new String[]{"STUDENT", "IT"});
            CallerUser instructor01 = new CallerUser();
            instructor01.setUsername("inst01@dmit2015.ca");
            callerUserRepository.add(instructor01, "Password2015", new String[]{"INSTRUCTOR", "Executive"});
            CallerUser user01 = new CallerUser();
            user01.setUsername("larry@3stooges.com");
            callerUserRepository.add(user01, "Password2015", new String[]{"ADMIN"});
            CallerUser user02 = new CallerUser();
            user02.setUsername("curly@3stooges.com");
            callerUserRepository.add(user02, "Password2015", new String[]{"REGISTER_USER"});
            CallerUser user03 = new CallerUser();
            user03.setUsername("moe@3stooges.com");
            callerUserRepository.add(user03, "Password2015", new String[]{"REGISTER_USER"});
        } else {
            logger.info("Application has user accounts");
        }
    }
}