package dmit2015.rendoruiz.assignment05.batch;

import dmit2015.rendoruiz.assignment05.entity.CurrentCasesByLocalGeographicArea;
import dmit2015.rendoruiz.assignment05.repository.CurrentCasesByLocalGeographicAreaRepository;

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Logger;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Polygon;
import org.geolatte.geom.codec.Wkt;
import org.geolatte.geom.crs.CoordinateReferenceSystems;

/**
 * Batchlets are task oriented step that is called once.
 * It either succeeds or fails. If it fails, it CAN be restarted and it runs again.
 *
 * @author Rendo Ruiz
 * @version 2021.04.12
 *
 */
@Named
public class CurrentCasesByLocalGeographicAreaBatchlet extends AbstractBatchlet {

    @Inject
    private JobContext _jobContext;

    @Inject
    private CurrentCasesByLocalGeographicAreaRepository _repository;

    @Inject
    private Logger _logger;

    /**
     * Perform a task and return "COMPLETED" if the job has successfully completed
     * otherwise return "FAILED" to indicate the job failed to complete.
     */
    @Transactional
    @Override
    public String process() throws Exception {
        String jobStatus = "FAILED";    // The job has failed to complete

        Properties jobParameters = _jobContext.getProperties();
        String inputFile = jobParameters.getProperty("input_file");

        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(inputFile).toFile()))) {
            String lineText;
            final String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
            // We can skip the first line as it contains column headings
            reader.readLine();
            while ((lineText = reader.readLine()) != null) {
                String[] values = lineText.split(delimiter, -1);

                CurrentCasesByLocalGeographicArea currentCurrentCasesByLocalGeographicArea = new CurrentCasesByLocalGeographicArea();
                currentCurrentCasesByLocalGeographicArea.setLocation(values[0]);
                currentCurrentCasesByLocalGeographicArea.setTotalCases(Integer.parseInt(values[1]));
                currentCurrentCasesByLocalGeographicArea.setActiveCases(Integer.parseInt(values[2]));
                currentCurrentCasesByLocalGeographicArea.setRecoveredCases(Integer.parseInt(values[3]));
                currentCurrentCasesByLocalGeographicArea.setDeaths(Integer.parseInt(values[4]));
                Polygon<G2D> polygon = (Polygon<G2D>) Wkt.fromWkt(values[5].replaceAll("\"",""), CoordinateReferenceSystems.WGS84);
                currentCurrentCasesByLocalGeographicArea.setPolygon(polygon);

                _repository.create(currentCurrentCasesByLocalGeographicArea);
            }
            jobStatus = "COMPLETED"; // The job has successfully completed
        } catch (Exception ex) {
            ex.printStackTrace();
            _logger.fine("Batchlet failed with exception: " + ex.getMessage());
        }

        return jobStatus;
    }
}