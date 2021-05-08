package dmit2015.rendoruiz.assignment03.repository;

import dmit2015.rendoruiz.assignment03.entity.OscarReview;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * This class is a repository containing the CRUD methods used on the OscarReview class.
 *
 * @author Rendo Ruiz
 * @version 2021.03.12
 *
 */
@ApplicationScoped
@Transactional
public class OscarReviewRepository {

    @PersistenceContext(unitName = "h2database-jpa-pu")
    private EntityManager entityManager;

    public void create(OscarReview newOscarReview) {
        entityManager.persist(newOscarReview);
    }

    public Optional<OscarReview> findById(Long OscarReviewID) {
        Optional<OscarReview> optionalOscarReview = Optional.empty();
        try {
            OscarReview querySingleResult = entityManager.find(OscarReview.class, OscarReviewID);
            if (querySingleResult != null) {
                optionalOscarReview = Optional.of(querySingleResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return optionalOscarReview;
    }

    public List<OscarReview> findAll() {
        return entityManager.createQuery("FROM OscarReview", OscarReview.class).getResultList();
    }

    public void update(OscarReview updatedOscarReview) {
        Optional<OscarReview> optionalOscarReview = findById(updatedOscarReview.getId());
        if (optionalOscarReview.isPresent()) {
            OscarReview existingOscarReview = optionalOscarReview.get();
            existingOscarReview.setCategory(updatedOscarReview.getCategory());
            existingOscarReview.setNominee(updatedOscarReview.getNominee());
            existingOscarReview.setReview(updatedOscarReview.getReview());
            existingOscarReview.setUsername(updatedOscarReview.getUsername());
            existingOscarReview.setCreatedDateTime(updatedOscarReview.getCreatedDateTime());
            existingOscarReview.setLastModifiedDateTime(updatedOscarReview.getLastModifiedDateTime());
            entityManager.merge(existingOscarReview);
            entityManager.flush();
        }
    }

    public void delete(Long id) {
        Optional<OscarReview> optionalOscarReview = findById(id);
        if (optionalOscarReview.isPresent()) {
            OscarReview existingOscarReview = optionalOscarReview.get();
            entityManager.remove(existingOscarReview);
        }
    }

}
