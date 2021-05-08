/**
 * read all
 curl -i -X GET http://localhost:8080/dmit2015-assignment02-rendoruiz/webapi/OscarReviews

 * read single oscar review
 curl -i -X GET http://localhost:8080/dmit2015-assignment02-rendoruiz/webapi/OscarReviews/3

 * new oscar review
 curl -i -X POST http://localhost:8080/dmit2015-assignment02-rendoruiz/webapi/OscarReviews/ \
 -d '{"category":"effects","nominee":"Affinity Photo","review":"Affinity","username":"affinityshill"}' \
 -H 'Content-Type:application/json'

 * update oscar review
 curl -i -X PUT http://localhost:8080/dmit2015-assignment02-rendoruiz/webapi/OscarReviews/4 \
 -d '{"id":4,"category":"effects","nominee":"Affinity Design","review":"Affinity Design is Better","username":"affinityshill"}' \
 -H 'Content-Type:application/json'

 * delete oscar review
 curl -i -X DELETE http://localhost:8080/dmit2015-assignment02-rendoruiz/webapi/OscarReviews/4
 */

package dmit2015.rendoruiz.assignment02.resource;

import dmit2015.rendoruiz.assignment02.entity.OscarReview;
import dmit2015.rendoruiz.assignment02.repository.OscarReviewRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * This class contains the resources for the REST API of the OscarReview class.
 *
 * @author Rendo Ruiz
 * @version 2021.03.02
 *
 */
@Path("OscarReviews")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OscarReviewResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private OscarReviewRepository _oscarReviewRepository;

    @GET
    public Response findAllOscarReviews() {
        List<OscarReview> oscarreviews = _oscarReviewRepository.findAll();
        return Response.ok(oscarreviews).build();
    }

    @GET
    @Path("{id : \\d+}")
    public Response findOneById(@PathParam("id") Long oscarReviewId) {
        if (oscarReviewId == null) {
            throw new BadRequestException();
        }

        Optional<OscarReview> optionalOscarReview = _oscarReviewRepository.findById(oscarReviewId);
        if (optionalOscarReview.isEmpty()) {
            throw new NotFoundException();
        }

        OscarReview existingOscarReview = optionalOscarReview.get();
        return Response.ok(existingOscarReview).build();
    }

    @POST
    public Response createNewOscarReview(@Valid OscarReview newOscarReview) {
        if (newOscarReview == null) {
            throw new BadRequestException();
        }
        _oscarReviewRepository.create(newOscarReview);
        URI locationUri = uriInfo.getAbsolutePathBuilder().path(newOscarReview.getId().toString()).build();
        return Response.created(locationUri).build();
    }

    @PUT
    @Path("{id : \\d+}")
    public Response updateOscarReview(@PathParam("id") Long oscarReviewId, @Valid OscarReview updatedOscarReview) {
        if (oscarReviewId == null || updatedOscarReview.getId() == null || !updatedOscarReview.getId().equals(oscarReviewId)) {
            throw new BadRequestException();
        }

        Optional<OscarReview> optionalOscarReview = _oscarReviewRepository.findById(oscarReviewId);
        if (optionalOscarReview.isEmpty()) {
            throw new NotFoundException();
        }

        OscarReview existingOscarReview = optionalOscarReview.get();
        existingOscarReview.setCategory(updatedOscarReview.getCategory());
        existingOscarReview.setNominee(updatedOscarReview.getNominee());
        existingOscarReview.setReview(updatedOscarReview.getReview());
        existingOscarReview.setUsername(updatedOscarReview.getUsername());
        _oscarReviewRepository.update(existingOscarReview);

        return Response.noContent().build();
    }

    @DELETE
    @Path("{id : \\d+}")
    public Response deleteOscarReview(@PathParam("id") Long oscarReviewId) {
        if (oscarReviewId == null) {
            throw new BadRequestException();
        }

        Optional<OscarReview> existingOscarReview = _oscarReviewRepository.findById(oscarReviewId);
        if (existingOscarReview.isEmpty()) {
            throw new NotFoundException();
        }

        _oscarReviewRepository.delete(oscarReviewId);
        return Response.noContent().build();
    }

}
