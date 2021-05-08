package dmit2015.rendoruiz.assignment05.repository;

import dmit2015.rendoruiz.assignment05.entity.CurrentCasesByLocalGeographicArea;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.geolatte.geom.builder.DSL;
import org.geolatte.geom.crs.CoordinateReferenceSystems;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Optional;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

/**
 * This class is the repository of CurrentCasesByLocalGeographicArea
 *
 * @author Rendo Ruiz
 * @version 2021.04.12
 *
 */
@ApplicationScoped
@Transactional
public class CurrentCasesByLocalGeographicAreaRepository extends AbstractJpaRepository<CurrentCasesByLocalGeographicArea, String> {

    public CurrentCasesByLocalGeographicAreaRepository() { super(CurrentCasesByLocalGeographicArea.class); }

    public Optional<CurrentCasesByLocalGeographicArea> contains(double longitude, double latitude) {
        Optional<CurrentCasesByLocalGeographicArea> optionalSingleResult = Optional.empty();

        final String jpql = "SELECT a FROM CurrentCasesByLocalGeographicArea a WHERE contains(a.polygon, :pointValue) = true";
        TypedQuery<CurrentCasesByLocalGeographicArea> query = getEntityManager().createQuery(jpql, CurrentCasesByLocalGeographicArea.class);
        Point<G2D> point = DSL.point(WGS84, DSL.g(longitude, latitude));
        query.setParameter("pointValue", point);
        try {
            CurrentCasesByLocalGeographicArea singleResult = query.getSingleResult();
            optionalSingleResult = Optional.of(singleResult);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return optionalSingleResult;
    }

}
