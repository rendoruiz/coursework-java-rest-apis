package dmit2015.rendoruiz.assignment05.resource;

import dmit2015.rendoruiz.assignment05.entity.CurrentCasesByLocalGeographicArea;
import dmit2015.rendoruiz.assignment05.repository.CurrentCasesByLocalGeographicAreaRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 *  curl -i -X GET http://localhost:8080/dmit2015-assignment05-rendoruiz/webapi/CurrentCasesByLocalGeographicArea/
 *
 *  curl -i -X GET http://localhost:8080/dmit2015-assignment05-rendoruiz/webapi/CurrentCasesByLocalGeographicArea/contains/-113.503519/53.5678765
 *
 *  curl -i -X GET http://localhost:8080/dmit2015-assignment05-rendoruiz/webapi/CurrentCasesByLocalGeographicArea/contains/-113.6344048/53.4867745
 *
 */

/**
 * This class is the resource class of CurrentCasesByLocalGeographicArea for curl/webpage access of data
 *
 * @author Rendo Ruiz
 * @version 2021.04.12
 *
 */
@RequestScoped
@Path("CurrentCasesByLocalGeographicArea")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CurrentCasesByLocalGeographicAreaResource {

    @Inject
    private CurrentCasesByLocalGeographicAreaRepository _repository;

    @GET
    public Response list() {
        return Response.ok(_repository.list()).build();
    }

    @GET
    @Path("/contains/{longitude}/{latitude}")
    public Response contains(@PathParam("longitude") double longitude, @PathParam("latitude") double latitude) {
        Optional<CurrentCasesByLocalGeographicArea> optionalSingleResult = _repository.contains(longitude, latitude);
        if (optionalSingleResult.isEmpty()) {
            throw new NotFoundException();
        }
        CurrentCasesByLocalGeographicArea singleResult = optionalSingleResult.get();
        return Response.ok(singleResult).build();
    }

}
