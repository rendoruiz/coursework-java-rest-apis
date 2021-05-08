package dmit2015.security;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.SecurityContext;
import java.util.logging.Logger;

/**
 * This class is contains the Security Interceptor for BillPaymentRepository
 *
 * @author Rendo Ruiz
 * @version 2021.03.28
 *
 */
public class BillPaymentSecurityInterceptor {

    @Inject
    private SecurityContext _securityContext;

    @AroundInvoke
    public Object verifyAccess(InvocationContext ic) throws Exception {
        // Only authenticated users with the role REGISTER_USER or ADMIN can execute any methods
        if (_securityContext.getCallerPrincipal() == null) {
            throw new RuntimeException("Access denied! Only authenticated users have permission to execute this method.");
        }
        if (!_securityContext.isCallerInRole("REGISTER_USER")) {
            if (!_securityContext.isCallerInRole("ADMIN")) {
                throw new RuntimeException("Access denied! You do not have permission to execute this method.");
            }
        }
        return ic.proceed();
    }
}
