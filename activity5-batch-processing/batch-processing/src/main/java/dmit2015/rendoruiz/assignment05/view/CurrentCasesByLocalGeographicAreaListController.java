package dmit2015.rendoruiz.assignment05.view;

import dmit2015.rendoruiz.assignment05.entity.CurrentCasesByLocalGeographicArea;
import dmit2015.rendoruiz.assignment05.repository.CurrentCasesByLocalGeographicAreaRepository;
import org.omnifaces.util.Messages;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("currentCurrentCasesByLocalGeographicAreaListController")
@ViewScoped
public class CurrentCasesByLocalGeographicAreaListController implements Serializable {

    @Inject
    private CurrentCasesByLocalGeographicAreaRepository _currentcasesbylocalgeographicareaRepository;

    @Getter
    private List<CurrentCasesByLocalGeographicArea> currentcasesbylocalgeographicareaList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            currentcasesbylocalgeographicareaList = _currentcasesbylocalgeographicareaRepository.list();
        } catch (RuntimeException ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}