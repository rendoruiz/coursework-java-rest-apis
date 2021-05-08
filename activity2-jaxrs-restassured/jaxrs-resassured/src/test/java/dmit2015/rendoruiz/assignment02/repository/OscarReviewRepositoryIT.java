package dmit2015.rendoruiz.assignment02.repository;

import dmit2015.rendoruiz.assignment02.config.ApplicationConfig;
import dmit2015.rendoruiz.assignment02.entity.OscarReview;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains the Arquillian Integration Tests (IT)
 * that tests the methods located on the OscarReviewRepository class.
 *
 * @author Rendo Ruiz
 * @version 2021.03.02
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ArquillianExtension.class)                  // Run with JUnit 5 instead of JUnit 4
public class OscarReviewRepositoryIT {

    static OscarReview currentOscarReview;

    @Inject
    private OscarReviewRepository _oscarReviewRepository;

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        return ShrinkWrap.create(WebArchive.class,"test.war")
                .addAsLibraries(pomFile.resolve("com.h2database:h2:1.4.200").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hamcrest:hamcrest:2.2").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hibernate:hibernate-core:5.3.20.Final").withTransitivity().asFile())
                .addClass(ApplicationConfig.class)
                .addClasses(OscarReview.class, OscarReviewRepository.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/sql/import-data.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml");
    }

    @Test
    void shouldFail() {
        fail("this should fail");
    }

    @Test
    void shouldThrowException() {
        OscarReview newOscarReview = new OscarReview();
        ConstraintViolationException cve = assertThrows(
                ConstraintViolationException.class,
                () -> _oscarReviewRepository.create(newOscarReview)
        );
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<OscarReview>> constraintViolations = validator.validate(newOscarReview);
        assertEquals(4, constraintViolations.size());
    }

    @Order(2)
    @Test
    void shouldCreate() {
        currentOscarReview = new OscarReview();
        currentOscarReview.setCategory("film");
        currentOscarReview.setNominee("Test Film");
        currentOscarReview.setReview("This is a test review for the test film");
        currentOscarReview.setUsername("testusername");
        _oscarReviewRepository.create(currentOscarReview);

        Optional<OscarReview> optionalOscarReview = _oscarReviewRepository.findById(currentOscarReview.getId());
        assertTrue(optionalOscarReview.isPresent());
        OscarReview existingOscarReview = optionalOscarReview.get();
        assertNotNull(existingOscarReview);
        assertEquals("film", existingOscarReview.getCategory());
        assertEquals("Test Film", existingOscarReview.getNominee());
        assertEquals("This is a test review for the test film", existingOscarReview.getReview());
        assertEquals("testusername", existingOscarReview.getUsername());
        LocalDateTime now = LocalDateTime.now();
        long diff = ChronoUnit.MINUTES.between(existingOscarReview.getCreatedDateTime(), now);
        assertEquals(0, diff);
    }

    @Order(3)
    @Test
    void shouldFindOne() {
        final Long OscarReviewId = currentOscarReview.getId();
        Optional<OscarReview> optionalOscarReview = _oscarReviewRepository.findById(OscarReviewId);
        assertTrue(optionalOscarReview.isPresent());
        OscarReview existingOscarReview = optionalOscarReview.get();
        assertNotNull(existingOscarReview);
        assertEquals("film", existingOscarReview.getCategory());
        assertEquals("Test Film", existingOscarReview.getNominee());
        assertEquals("This is a test review for the test film", existingOscarReview.getReview());
        assertEquals("testusername", existingOscarReview.getUsername());
        LocalDateTime now = LocalDateTime.now();
        long diff = ChronoUnit.MINUTES.between(existingOscarReview.getCreatedDateTime(), now);
        assertEquals(0, diff);
    }

    @Order(1)
    @Test
    void shouldFindAll() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<OscarReview> queryResultList = _oscarReviewRepository.findAll();
        assertEquals(3, queryResultList.size());

        OscarReview firstOscarReview = queryResultList.get(0);
        assertEquals("actor", firstOscarReview.getCategory());
        assertEquals("Rendo", firstOscarReview.getNominee());
        assertEquals("pretty good tbh", firstOscarReview.getReview());
        assertEquals("rendo", firstOscarReview.getUsername());
        assertEquals(LocalDateTime.parse("2021-02-02 07:00", formatter).toString(), firstOscarReview.getCreatedDateTime().toString());
        assertEquals(LocalDateTime.parse("2021-02-03 07:00", formatter).toString(), firstOscarReview.getLastModifiedDateTime().toString());

        OscarReview secondOscarReview = queryResultList.get(1);
        assertEquals("film", secondOscarReview.getCategory());
        assertEquals("Sample Film", secondOscarReview.getNominee());
        assertEquals("review soon", secondOscarReview.getReview());
        assertEquals("rendo", secondOscarReview.getUsername());
        assertEquals(LocalDateTime.parse("2021-02-02 07:00", formatter).toString(), secondOscarReview.getCreatedDateTime().toString());
        assertEquals(LocalDateTime.parse("2021-02-03 07:00", formatter).toString(), secondOscarReview.getLastModifiedDateTime().toString());

        OscarReview thirdOscarReview = queryResultList.get(2);
        assertEquals("effects", thirdOscarReview.getCategory());
        assertEquals("After Effects", thirdOscarReview.getNominee());
        assertEquals("Adobe", thirdOscarReview.getReview());
        assertEquals("rendo", thirdOscarReview.getUsername());
        assertEquals(LocalDateTime.parse("2021-02-04 09:30", formatter).toString(), thirdOscarReview.getCreatedDateTime().toString());
        assertEquals(LocalDateTime.parse("2021-02-04 09:35", formatter).toString(), thirdOscarReview.getLastModifiedDateTime().toString());
    }

    @Order(4)
    @Test
    void shouldUpdate() {
        currentOscarReview.setNominee("Sample IT Film");
        currentOscarReview.setReview("This is now an updated review created on IT.");
        _oscarReviewRepository.update(currentOscarReview);

        Optional<OscarReview> optionalUpdatedOscarReview = _oscarReviewRepository.findById(currentOscarReview.getId());
        assertTrue(optionalUpdatedOscarReview.isPresent());
        OscarReview updatedOscarReview = optionalUpdatedOscarReview.get();
        assertNotNull(updatedOscarReview);
        assertEquals(currentOscarReview.getNominee(), updatedOscarReview.getNominee());
        assertEquals(currentOscarReview.getReview(), updatedOscarReview.getReview());
        long diff = ChronoUnit.MINUTES.between(currentOscarReview.getLastModifiedDateTime(), updatedOscarReview.getLastModifiedDateTime());
        assertEquals(0, diff);
    }

    @Order(5)
    @Test
    void shouldDelete() {
        final Long OscarReviewId = currentOscarReview.getId();
        Optional<OscarReview> optionalOscarReview = _oscarReviewRepository.findById(OscarReviewId);
        assertTrue(optionalOscarReview.isPresent());
        OscarReview existingOscarReview = optionalOscarReview.get();
        assertNotNull(existingOscarReview);
        _oscarReviewRepository.delete(existingOscarReview.getId());
        optionalOscarReview = _oscarReviewRepository.findById(OscarReviewId);
        assertTrue(optionalOscarReview.isEmpty());
    }

}
