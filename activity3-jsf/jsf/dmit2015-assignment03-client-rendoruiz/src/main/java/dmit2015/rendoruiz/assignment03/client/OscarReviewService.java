package dmit2015.rendoruiz.assignment03.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import java.util.List;

/**
 * This class contains the methods for accessing the server-side RESTful API services
 *
 * @author Rendo Ruiz
 * @version 2021.03.12
 *
 */
@RegisterRestClient(baseUri = "http://localhost:8080/dmit2015-assignment03-server-rendoruiz/webapi")
@Path("OscarReviews")
public interface OscarReviewService {

    @GET
    List<OscarReview> findAll();

    @GET
    @Path("{id}")
    OscarReview findOneById(@PathParam("id") Long id);

    @POST
    String create(OscarReview newOscarReview);

    @PUT
    @Path("{id}")
    void update(@PathParam("id") Long id, OscarReview updatedOscarReview);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") Long id);
}
