package dmit2015.rendoruiz.assignment05.producer;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * This class contains the logger used for debugging on runtime
 *
 * @author Rendo Ruiz
 * @version 2021.04.12
 *
 */
public class LoggerProducer {

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

}
