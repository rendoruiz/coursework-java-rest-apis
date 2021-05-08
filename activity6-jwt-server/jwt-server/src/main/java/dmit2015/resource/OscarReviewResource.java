/**
 * get token as USER
 curl -k -i -X POST https://localhost:8443/dmit2015-project-rest-services-rendoruiz/webapi/jwt/formLogin/ \
 -d 'j_username=user01@dmit2015.ca&j_password=Password2015' \
 -H 'Content-Type:application/x-www-form-urlencoded'

 * get token as ADMIN
 curl -k -i -X POST https://localhost:8443/dmit2015-project-rest-services-rendoruiz/webapi/jwt/formLogin/ \
 -d 'j_username=admin01@dmit2015.ca&j_password=Password2015' \
 -H 'Content-Type:application/x-www-form-urlencoded'

 * read all
 curl -i -X GET http://localhost:8080/dmit2015-project-rest-services-rendoruiz/webapi/OscarReviews

 * read single oscar review
 curl -i -X GET http://localhost:8080/dmit2015-project-rest-services-rendoruiz/webapi/OscarReviews/3


 * read all by username (USER only)
 curl -i -X GET http://localhost:8080/dmit2015-project-rest-services-rendoruiz/webapi/OscarReviews/user/user01@dmit2015.ca \
 -H 'Authorization: Bearer USER_TOKEN'

 curl -i -X GET http://localhost:8080/dmit2015-project-rest-services-rendoruiz/webapi/OscarReviews/user/user01@dmit2015.ca \
 -H 'Authorization: Bearer eyJraWQiOiJxdWlja3N0YXJ0LWp3dC1pc3N1ZXIiLCJ0eXAiOiJqd3QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbjAxQGRtaXQyMDE1LmNhIiwidXBuIjoiYWRtaW4wMUBkbWl0MjAxNS5jYSIsImlzcyI6InF1aWNrc3RhcnQtand0LWlzc3VlciIsImF1ZCI6Imp3dC1hdWRpZW5jZSIsImdyb3VwcyI6WyJBRE1JTiIsIlVTRVIiXSwianRpIjoiMTI2Yjg5ZDAtNGFjNi00YTUzLWE5YjMtN2RmYzUyOTA4ZjEwIiwiaWF0IjoxNjE4NzIyODQwLCJleHAiOjE2MTg3MzcyNDB9.KdioaFedgRXaA2KUJ7FlT2uCHPIFWkN1LoNrwT-cgL1OAyKhAlEr87tYmJZPHTUmQIwLKZDkQEI2Jf1GOy56NSEwlK2yCpWhAYizSL2X4IbFt3OBJCCX1bNIos9ZhdHCSSF41Eo5pd2-LBnu6IQMP0Ci-8s6X8tLLH_R77XMWZmWBKJfewh3CToGlo--Z21eDlbr-6xWsOSvWeLouI99w0WbOg-rBxSM_gCbBbtWQafhlSz2Oo0xQKtCpRVX6tETxennJsicqz2XDcpiSpfW9eCTtF5Xidwh3xZrd1dLzX1gMh-nNSUzC35ad0f2IVWXQ3qjBSfeaPdj8Sa7In6Y6A'


 * create oscar review (USER only)
 curl -i -X POST http://localhost:8080/dmit2015-project-rest-services-rendoruiz/webapi/OscarReviews/ \
 -d '{"category":"effects","nominee":"Affinity Photo","review":"Affinity", "username":"testoo"}' \
 -H 'Content-Type:application/json' \
 -H 'Authorization: Bearer USER_TOKEN'

 curl -i -X POST http://localhost:8080/dmit2015-project-rest-services-rendoruiz/webapi/OscarReviews/ \
 -d '{"category":"effects","nominee":"new oscar review","review":"this is a created review", "username":"testoo"}' \
 -H 'Content-Type:application/json' \
 -H 'Authorization: Bearer eyJraWQiOiJxdWlja3N0YXJ0LWp3dC1pc3N1ZXIiLCJ0eXAiOiJqd3QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMDFAZG1pdDIwMTUuY2EiLCJ1cG4iOiJ1c2VyMDFAZG1pdDIwMTUuY2EiLCJpc3MiOiJxdWlja3N0YXJ0LWp3dC1pc3N1ZXIiLCJhdWQiOiJqd3QtYXVkaWVuY2UiLCJncm91cHMiOlsiVVNFUiJdLCJqdGkiOiIyZGZiYTM1Mi04MDk1LTQwNGEtOGI0OC0zNjUxOGE3OTQxMTEiLCJpYXQiOjE2MTg3MjI2MDYsImV4cCI6MTYxODczNzAwNn0.Pk8EAWjG5IkRMlNO6katuRIb-HNQUazEz2RC67xA_QVcpHeggG-QLaOkR2BUOcvV9Z6PUdu0cki3eE0vHIcn1UFjaCfRax_xegtgkZ-0JtjqNULvRlpN-sLa6JGwV0bh9LtcyF7nupMte3RmQsgqHLjV3-fhEZYlzv5n-Phl8fpVOcMKcSYpdY0CdMe2M4Ojo5Meng4BjAGG0YctHjgjD18ZC63XPFZZVjPEUYyiBpC9TDjbvqQcNu_h3GlGBEHW1voO0GINpdOmfGCxKb_N8_SsEo7t4ZB1a9cC_5L0puc2V1DU8gwsTI1ZOIdvR8iIRM80AMCWScZ5iEQijLx9hQ'


 * update oscar review (USER only)
 curl -i -X PUT http://localhost:8080/dmit2015-project-rest-services-rendoruiz/webapi/OscarReviews/4 \
 -d '{"id":4,"category":"effects","nominee":"new oscar review update","review":"this is an updated review","username":"testoo"}' \
 -H 'Content-Type:application/json' \
 -H 'Authorization: Bearer USER_TOKEN'

 curl -i -X PUT http://localhost:8080/dmit2015-project-rest-services-rendoruiz/webapi/OscarReviews/4 \
 -d '{"id":4,"category":"effects","nominee":"new oscar review update","review":"this is an updated review","username":"testoo"}' \
 -H 'Content-Type:application/json' \
 -H 'Authorization: Bearer eyJraWQiOiJxdWlja3N0YXJ0LWp3dC1pc3N1ZXIiLCJ0eXAiOiJqd3QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMDFAZG1pdDIwMTUuY2EiLCJ1cG4iOiJ1c2VyMDFAZG1pdDIwMTUuY2EiLCJpc3MiOiJxdWlja3N0YXJ0LWp3dC1pc3N1ZXIiLCJhdWQiOiJqd3QtYXVkaWVuY2UiLCJncm91cHMiOlsiVVNFUiJdLCJqdGkiOiIyZGZiYTM1Mi04MDk1LTQwNGEtOGI0OC0zNjUxOGE3OTQxMTEiLCJpYXQiOjE2MTg3MjI2MDYsImV4cCI6MTYxODczNzAwNn0.Pk8EAWjG5IkRMlNO6katuRIb-HNQUazEz2RC67xA_QVcpHeggG-QLaOkR2BUOcvV9Z6PUdu0cki3eE0vHIcn1UFjaCfRax_xegtgkZ-0JtjqNULvRlpN-sLa6JGwV0bh9LtcyF7nupMte3RmQsgqHLjV3-fhEZYlzv5n-Phl8fpVOcMKcSYpdY0CdMe2M4Ojo5Meng4BjAGG0YctHjgjD18ZC63XPFZZVjPEUYyiBpC9TDjbvqQcNu_h3GlGBEHW1voO0GINpdOmfGCxKb_N8_SsEo7t4ZB1a9cC_5L0puc2V1DU8gwsTI1ZOIdvR8iIRM80AMCWScZ5iEQijLx9hQ'


 * delete oscar review (ADMIN only)
 curl -i -X DELETE http://localhost:8080/dmit2015-project-rest-services-rendoruiz/webapi/OscarReviews/4 \
 -H 'Authorization: Bearer ADMIN_TOKEN'

 curl -i -X DELETE http://localhost:8080/dmit2015-project-rest-services-rendoruiz/webapi/OscarReviews/4 \
 -H 'Authorization: Bearer eyJraWQiOiJxdWlja3N0YXJ0LWp3dC1pc3N1ZXIiLCJ0eXAiOiJqd3QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbjAxQGRtaXQyMDE1LmNhIiwidXBuIjoiYWRtaW4wMUBkbWl0MjAxNS5jYSIsImlzcyI6InF1aWNrc3RhcnQtand0LWlzc3VlciIsImF1ZCI6Imp3dC1hdWRpZW5jZSIsImdyb3VwcyI6WyJBRE1JTiIsIlVTRVIiXSwianRpIjoiMTI2Yjg5ZDAtNGFjNi00YTUzLWE5YjMtN2RmYzUyOTA4ZjEwIiwiaWF0IjoxNjE4NzIyODQwLCJleHAiOjE2MTg3MzcyNDB9.KdioaFedgRXaA2KUJ7FlT2uCHPIFWkN1LoNrwT-cgL1OAyKhAlEr87tYmJZPHTUmQIwLKZDkQEI2Jf1GOy56NSEwlK2yCpWhAYizSL2X4IbFt3OBJCCX1bNIos9ZhdHCSSF41Eo5pd2-LBnu6IQMP0Ci-8s6X8tLLH_R77XMWZmWBKJfewh3CToGlo--Z21eDlbr-6xWsOSvWeLouI99w0WbOg-rBxSM_gCbBbtWQafhlSz2Oo0xQKtCpRVX6tETxennJsicqz2XDcpiSpfW9eCTtF5Xidwh3xZrd1dLzX1gMh-nNSUzC35ad0f2IVWXQ3qjBSfeaPdj8Sa7In6Y6A'
 */

package dmit2015.resource;

import dmit2015.entity.OscarReview;
import dmit2015.listener.ApplicationStartupListener;
import dmit2015.repository.OscarReviewRepository;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
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
import java.util.logging.Logger;

/**
 * This class contains the resources for the REST API of the OscarReview class.
 *
 * @author Rendo Ruiz
 * @version 2021.03.02
 *
 */
@RequestScoped
@Path("OscarReviews")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OscarReviewResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private OscarReviewRepository _oscarReviewRepository;

    @Inject
    private JsonWebToken _callerPrincipal;

    @GET
    public Response findAllOscarReviews() {
        List<OscarReview> oscarreviews = _oscarReviewRepository.findAll();
        return Response.ok(oscarreviews).build();
    }

    @RolesAllowed({"USER"})
    @GET
    @Path("user/{user}")
    public Response findAllByUsername(@PathParam("user") String username) {
        List<OscarReview> oscarreviews = _oscarReviewRepository.findByUsername(username);
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

    @RolesAllowed({"USER"})
    @POST
    public Response createNewOscarReview(@Valid OscarReview newOscarReview) {
        if (newOscarReview == null) {
            throw new BadRequestException();
        }
        String username = _callerPrincipal.getName();
        newOscarReview.setUsername(username);

        _oscarReviewRepository.create(newOscarReview);
        URI locationUri = uriInfo.getAbsolutePathBuilder().path(newOscarReview.getId().toString()).build();
        return Response.created(locationUri).build();
    }

    @RolesAllowed({"USER"})
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
        String username = _callerPrincipal.getName();
        existingOscarReview.setUsername(username);
        _oscarReviewRepository.update(existingOscarReview);

        return Response.noContent().build();
    }

    @RolesAllowed({"ADMIN"})
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
