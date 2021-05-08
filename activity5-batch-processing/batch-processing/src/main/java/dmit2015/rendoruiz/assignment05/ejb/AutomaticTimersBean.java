package dmit2015.rendoruiz.assignment05.ejb;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timer;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.logging.Logger;

/**
 * This Timer will run at every start up.
 * It is scheduled every 4PM Edmonton Time on weekdays to download the latest csv file and
 * import it to the oracle database using the batchlet
 *
 * @author Rendo Ruiz
 * @version 2021.04.12
 *
 */
@Singleton
@Startup
public class AutomaticTimersBean {
    private Logger _logger = Logger.getLogger(AutomaticTimersBean.class.getName());

//    @Schedule(second="10", minute = "*", hour="*", dayOfWeek="Mon-Fri", timezone="America/Edmonton", info="execute every 10 seconds on weekdays", persistent = false)
    @Schedule(hour="16", dayOfWeek="Mon-Fri", timezone="America/Edmonton", info="execute every 4pm edmonton time during weekdays", persistent = false)
    public void refreshDatabaseFromNewFileData(Timer timer) {
        // download
        HttpClient client = HttpClient.newHttpClient();
        final String downloadUriString = "https://data.edmonton.ca/api/views/ix8f-s9xp/rows.csv?accessType=DOWNLOAD";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(downloadUriString))
                .build();
        Path downloadPath = Path.of("/home/user2015/Downloads");
        try {
            HttpResponse<Path> response = client.send(request,
                    HttpResponse.BodyHandlers.ofFileDownload(downloadPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE));
            _logger.info("Finished download file to " + response.body());
            System.out.println("Status code: " + response.statusCode());
            System.out.println("\n Body: " + response.body());
        } catch (Exception e) {
            _logger.fine("Error downloading file. " + e.getMessage());
            e.printStackTrace();
        }

        // do batchlet
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        long jobId = jobOperator.start("batchletCurrentCasesByLocalGeographicArea", null);
        System.out.println("Job submitted: " + jobId);
    }
}
