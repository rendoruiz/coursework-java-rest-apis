package dmit2015.rendoruiz.assignment05.repository;

import dmit2015.rendoruiz.assignment05.entity.CurrentCasesByLocalGeographicArea;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Optional;

/**
 * This class contains the controller used to find a database record based on the given longitude and latitude
 *
 * @author Rendo Ruiz
 * @version 2021.04.12
 *
 */
@Named("currentCasesByLocalGeographicAreaFindOneByLocationController")
@ViewScoped
public class CurrentCasesByLocalGeographicAreaFindOneByLocation implements Serializable {

    @Inject
    private CurrentCasesByLocalGeographicAreaRepository _currentCasesByLocalGeographicAreaRepository;

    @Getter
    private CurrentCasesByLocalGeographicArea findOneResult;

    @Getter @Setter
    private double longitude = -113.503519;

    @Getter @Setter
    private double latitude = 53.5678765;

    public void onSearch() {
        try {
            Optional<CurrentCasesByLocalGeographicArea> optionalCurrentCasesByLocalGeographicArea = _currentCasesByLocalGeographicAreaRepository.contains(longitude, latitude);
            if (optionalCurrentCasesByLocalGeographicArea.isPresent()) {
                findOneResult = optionalCurrentCasesByLocalGeographicArea.get();
            } else {
                findOneResult = null;
            }
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }

    public void onClear() {
        findOneResult = null;
    }
}
