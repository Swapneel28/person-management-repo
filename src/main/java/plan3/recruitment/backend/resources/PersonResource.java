package plan3.recruitment.backend.resources;

import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.service.PersonStorage;
import plan3.recruitment.backend.service.PersonStorageImpl;
import static plan3.recruitment.backend.util.Constants.APPLICATION_JSON_UTF8;
import static plan3.recruitment.backend.util.Constants.EMAIL_PARAM;
import static plan3.recruitment.backend.util.Constants.EMAIL_PATH_PARAM;

import java.net.URI;
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.jetty.http.HttpStatus;

@Path("persons")
@Produces(APPLICATION_JSON_UTF8)
@Consumes(APPLICATION_JSON_UTF8)
public class PersonResource {

    PersonStorage storage = new PersonStorageImpl(); // FIXME: Use your PersonStorage implementation here
    
    @POST
    public Response create(final Person person, @Context
    		  UriInfo uriInfo) {
    	Person createdPerson = storage.save(person);
    	if(createdPerson != null) {
    		URI uri = uriInfo.getBaseUriBuilder()
    				.path(PersonResource.class)
    				.path(person.email)
    				.build();   
    		return Response.ok(person)
    				  .status(HttpStatus.CREATED_201)
    				  .location(uri)   				  
    				  .build();
    	}
    	return Response.status(HttpStatus.NO_CONTENT_204).build();
    }

	@GET
    @Path(EMAIL_PATH_PARAM)
    public Response read(@PathParam(EMAIL_PARAM) final String email) {
    	Optional<Person> optional = storage.fetch(email);
    	if(optional.isPresent()) {
    	  Person person = optional.get();
          return Response.ok(person).status(HttpStatus.OK_200).build();
    	}
    	return Response.status(HttpStatus.NO_CONTENT_204).build();
    }

    @PUT
    @Path(EMAIL_PATH_PARAM)
    public Response update(@PathParam(EMAIL_PARAM) final String email, final Person person) {
        Person updatedPerson = storage.update(email, person);
        if(updatedPerson != null) {
        	return Response.ok(updatedPerson).status(HttpStatus.OK_200).build();
        }
        return Response.status(HttpStatus.NO_CONTENT_204).build();
    }

    @DELETE
    @Path(EMAIL_PATH_PARAM)
    public Response delete(@PathParam(EMAIL_PARAM) final String email) {
    	Optional<Person> optional = storage.fetch(email);
    	if(optional.isPresent()) {
    	   Person person = optional.get();
    	   storage.remove(person);
    	   return Response.status(HttpStatus.OK_200).build();
    	}
        return Response.status(HttpStatus.NO_CONTENT_204).build();
    }

    @GET
    public Response list() {
        return Response.ok(storage.list()).status(HttpStatus.OK_200).build();
    } 
}