package dmit2015.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * This class is contains the Logout back-end boilerplate code
 *
 * @author Rendo Ruiz
 * @version 2021.03.28
 *
 */
@Named
@RequestScoped
public class Logout {

    @Inject
    private HttpServletRequest request;

    public String submit() throws ServletException {
        request.logout();
        request.getSession().invalidate();
        return "/index?faces-redirect=true";
    }
}