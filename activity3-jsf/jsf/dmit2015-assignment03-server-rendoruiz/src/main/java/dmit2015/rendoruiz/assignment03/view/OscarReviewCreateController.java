package dmit2015.rendoruiz.assignment03.view;

import dmit2015.rendoruiz.assignment03.entity.OscarReview;
import dmit2015.rendoruiz.assignment03.repository.OscarReviewRepository;
import lombok.Getter;
import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This class is a controller that contains the methods for creating entities
 *
 * @author Rendo Ruiz
 * @version 2021.03.12
 *
 */
@Named("currentOscarReviewCreateController")
@RequestScoped
public class OscarReviewCreateController {

    @Inject
    private OscarReviewRepository _oscarreviewRepository;

    @Getter
    private OscarReview newOscarReview = new OscarReview();

    public String onCreate() {
        String nextPage = "";
        try {
            _oscarreviewRepository.create(newOscarReview);
            Messages.addFlashGlobalInfo("Create was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful.");
        }
        return nextPage;
    }

}