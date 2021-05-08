package dmit2015.repository;

import common.config.ApplicationConfig;
import dmit2015.entity.OscarReview;
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
 * @version 2021.02.04
 *
 */
@ExtendWith(ArquillianExtension.class)                  // Run with JUnit 5 instead of JUnit 4
public class OscarReviewRepositoryIT {
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
        assertEquals(6, constraintViolations.size());
    }


    @Transactional(TransactionMode.ROLLBACK)
    @Test
    void shouldCreate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        OscarReview newOscarReview = new OscarReview();
        newOscarReview.setCategory("film");
        newOscarReview.setNominee("Test Film");
        newOscarReview.setReview("This is a test review for the test film");
        newOscarReview.setUsername("testusername");
        newOscarReview.setCreatedDateTime(LocalDateTime.parse("2021-02-04 08:00", formatter));
        newOscarReview.setLastModifiedDateTime(LocalDateTime.parse("2021-02-05 08:05", formatter));
        _oscarReviewRepository.create(newOscarReview);

        Optional<OscarReview> optionalOscarReview = _oscarReviewRepository.findById(newOscarReview.getId());
        assertTrue(optionalOscarReview.isPresent());
        OscarReview existingOscarReview = optionalOscarReview.get();
        assertNotNull(existingOscarReview);
        assertEquals("film", existingOscarReview.getCategory());
        assertEquals("Test Film", existingOscarReview.getNominee());
        assertEquals("This is a test review for the test film", existingOscarReview.getReview());
        assertEquals("testusername", existingOscarReview.getUsername());
        assertEquals(LocalDateTime.parse("2021-02-04 08:00", formatter).toString(), existingOscarReview.getCreatedDateTime().toString());
        assertEquals(LocalDateTime.parse("2021-02-05 08:05", formatter).toString(), existingOscarReview.getLastModifiedDateTime().toString());
    }

    @Test
    void shouldFindOne() {
        final Long OscarReviewId = 3L;
        Optional<OscarReview> optionalOscarReview = _oscarReviewRepository.findById(OscarReviewId);
        assertTrue(optionalOscarReview.isPresent());
        OscarReview existingOscarReview = optionalOscarReview.get();
        assertNotNull(existingOscarReview);
        assertEquals("effects", existingOscarReview.getCategory());
        assertEquals("After Effects", existingOscarReview.getNominee());
        assertEquals("Adobe", existingOscarReview.getReview());
        assertEquals("rendo", existingOscarReview.getUsername());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        assertEquals(LocalDateTime.parse("2021-02-04 09:30", formatter).toString(), existingOscarReview.getCreatedDateTime().toString());
        assertEquals(LocalDateTime.parse("2021-02-04 09:35", formatter).toString(), existingOscarReview.getLastModifiedDateTime().toString());
    }

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

    @Transactional(TransactionMode.ROLLBACK)
    @Test
    void shouldUpdate() {
        final Long OscarReviewId = 2L;
        Optional<OscarReview> optionalOscarReview = _oscarReviewRepository.findById(OscarReviewId);
        assertTrue(optionalOscarReview.isPresent());
        OscarReview existingOscarReview = optionalOscarReview.get();
        assertNotNull(existingOscarReview);
        existingOscarReview.setNominee("Sample IT Film");
        existingOscarReview.setReview("This is now an updated review created on IT.");
        LocalDateTime currentDateTime = LocalDateTime.now();
        existingOscarReview.setLastModifiedDateTime(currentDateTime);
        _oscarReviewRepository.update(existingOscarReview);

        Optional<OscarReview> optionalUpdatedOscarReview = _oscarReviewRepository.findById(OscarReviewId);
        assertTrue(optionalUpdatedOscarReview.isPresent());
        OscarReview updatedOscarReview = optionalUpdatedOscarReview.get();
        assertNotNull(updatedOscarReview);
        assertEquals("Sample IT Film", updatedOscarReview.getNominee());
        assertEquals("This is now an updated review created on IT.", updatedOscarReview.getReview());
        long diff = ChronoUnit.MINUTES.between(currentDateTime, updatedOscarReview.getLastModifiedDateTime());
        assertEquals(diff, 0);
    }

    @Transactional(TransactionMode.ROLLBACK)
    @Test
    void shouldDelete() {
        final Long OscarReviewId = 3L;
        Optional<OscarReview> optionalOscarReview = _oscarReviewRepository.findById(OscarReviewId);
        assertTrue(optionalOscarReview.isPresent());
        OscarReview existingOscarReview = optionalOscarReview.get();
        assertNotNull(existingOscarReview);
        _oscarReviewRepository.delete(existingOscarReview.getId());
        optionalOscarReview = _oscarReviewRepository.findById(OscarReviewId);
        assertTrue(optionalOscarReview.isEmpty());
    }

}
