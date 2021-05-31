package de.beosign.quarkustest;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/persons")
public class PersonResource {
    private static final Logger log = LoggerFactory.getLogger(PersonResource.class);

    final PersonRepository personRepository;

    @Inject
    public PersonResource(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") String id) {
        log.debug("Getting person with id {}", id);

        Person person = personRepository.findById(Long.valueOf(id));
        if (person != null) {
            return Response.status(Response.Status.OK).entity(person).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response postPerson(Person person) {
        log.debug("Creating person {}", person);

        personRepository.persist(person);
        return Response.status(Response.Status.OK).entity(person).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response putPerson(Person person) {
        log.debug("Updating person {}", person);

        personRepository.update(person);
        return Response.status(Response.Status.OK).entity(person).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deletePerson(@PathParam("id") String id) {
        log.debug("Deleting person with id {}", id);

        personRepository.deleteById(Long.valueOf(id));
        return Response.status(Response.Status.OK).build();
    }
}
