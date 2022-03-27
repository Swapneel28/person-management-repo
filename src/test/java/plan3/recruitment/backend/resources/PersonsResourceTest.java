package plan3.recruitment.backend.resources;


import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import plan3.recruitment.backend.model.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
public class PersonsResourceTest {

    private static  ResourceExtension RESOURCE_EXTENSION = ResourceExtension.builder()
            .addResource(new PersonResource())
            .build();
    
    @BeforeEach
    public void init() {
    	  Person stefan = Person.valueOf("Stefan", "Petersson", "stefan@example.com");
          Person markus = Person.valueOf("Markus", "Gustavsson", "markus@example.com");
          Person ian = Person.valueOf("Ian", "Vännman", "ian@example.com");
          Person marten = Person.valueOf("Mårten", "Gustafson", "marten@example.com");

    	RESOURCE_EXTENSION.target("/persons").request().post(Entity.json(stefan));
        RESOURCE_EXTENSION.target("/persons").request().post(Entity.json(markus));
        RESOURCE_EXTENSION.target("/persons").request().post(Entity.json(ian));
        RESOURCE_EXTENSION.target("/persons").request().post(Entity.json(marten));
        
    }

    @Test
    public void listSortedOnLastname() {
    	 Person stefan = Person.valueOf("Stefan", "Petersson", "stefan@example.com");
         Person markus = Person.valueOf("Markus", "Gustavsson", "markus@example.com");
         Person ian = Person.valueOf("Ian", "Vännman", "ian@example.com");
         Person marten = Person.valueOf("Mårten", "Gustafson", "marten@example.com");
        
        List<Person> actualPersons = RESOURCE_EXTENSION.target("/persons").request().get(new GenericType<List<Person>>() {});
        List<Person> expectedPersons = List.of(marten, markus, stefan, ian);
        assertEquals(expectedPersons, actualPersons);
    }

    @Test
    public void readExistingPerson() {
         Person actualPerson = RESOURCE_EXTENSION.target("/persons/marten@example.com").request().get(Person.class);
         Person expectedPerson = Person.valueOf("Mårten", "Gustafson", "marten@example.com");
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void readNonExistentPerson() {
         Response response = RESOURCE_EXTENSION.target("/persons/user@example.com").request().get();
         int expectedRespStatus = 204; // FIXME: Pick an HTTP response status you think is most suitable here.
        assertEquals(expectedRespStatus, response.getStatus());
    }

    @Test
    public void createNewPerson() {
         Person payload = Person.valueOf("Petter", "Gustafson", "petter@example.com");
         Response response = RESOURCE_EXTENSION.target("/persons").request().post(Entity.json(payload));
         int expectedRespStatus = 201; // FIXME: Pick an HTTP response status you think is most suitable here.
        assertEquals(expectedRespStatus, response.getStatus());
        assertEquals("/persons/petter@example.com", response.getLocation().getPath());
    }

    @Test
    public void createPersonThatAlreadyExists() {
    	 Person payload = Person.valueOf("Mårten", "Gustafson", "marten@example.com");
         Response response = RESOURCE_EXTENSION.target("/persons").request().post(Entity.json(payload));
         int expectedRespStatus = 204; // FIXME: Pick an HTTP response status you think is most suitable here.
        assertEquals(expectedRespStatus, response.getStatus());
    }
    
    @Test
    public void updateExistingPerson() {
         Person payload = Person.valueOf("Mårten2", "Gustafson2", "marten@example.com");
         Person actualPerson = RESOURCE_EXTENSION.target("/persons/marten@example.com").request()
                .put(Entity.json(payload), Person.class);
         Person expectedPerson = Person.valueOf("Mårten2", "Gustafson2", "marten@example.com");
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void updateNonExistentPerson() {
         Person payload = Person.valueOf("Mårten", "Gustafson", "marten@example.com");
         Response response = RESOURCE_EXTENSION.target("/persons/user@example.com").request().put(Entity.json(payload));
         int expectedRespStatus = 204; // FIXME: Pick an HTTP response status you think is most suitable here.
         assertEquals(expectedRespStatus, response.getStatus());
    }

    @Test
    public void deleteExistingPerson() {
         Response response = RESOURCE_EXTENSION.target("/persons/marten@example.com").request().delete();
         int expectedRespStatus = 200; // FIXME: Pick an HTTP response status you think is most suitable here.
        assertEquals(expectedRespStatus, response.getStatus());
    }

    @Test
    public void deleteNonExistentPerson() {
         Response response = RESOURCE_EXTENSION.target("/persons/user@example.com").request().delete();
         int expectedRespStatus = 204; // FIXME: Pick an HTTP response status you think is most suitable here.
        assertEquals(expectedRespStatus, response.getStatus());
    }
    
    @AfterEach
    public void finalize() {
        
        RESOURCE_EXTENSION.target("/persons/stefan@example.com").request().delete();
        RESOURCE_EXTENSION.target("/persons/markus@example.com").request().delete();
        RESOURCE_EXTENSION.target("/persons/ian@example.com").request().delete();
        RESOURCE_EXTENSION.target("/persons/marten@example.com").request().delete();
        
    }
    
    
}