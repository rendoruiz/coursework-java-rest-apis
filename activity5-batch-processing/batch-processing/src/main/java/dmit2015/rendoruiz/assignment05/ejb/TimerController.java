package dmit2015.rendoruiz.assignment05.ejb;

import javax.ejb.Timer;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

/**
 * This class is ProgrammaticTimersManager's controller that can be accesed by the front-end page
 *
 * @author Rendo Ruiz
 * @version 2021.04.12
 *
 */
@Named
@SessionScoped
public class TimerController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private ProgrammaticTimersManagerBean timerBean;

    public String cancelAllTimers() {
        timerBean.cancelAllTimers();;
        return "";
    }

    public Collection<Timer> list() {
        return timerBean.listAllTimers();
    }

    public void cancelTimer(Timer selectedTimer) {
        timerBean.cancelTimer(selectedTimer);
    }
}