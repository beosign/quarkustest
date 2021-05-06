package de.beosign.quarkustest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/greetings")
public class GreetingResource {
    private static final Logger log = LoggerFactory.getLogger(GreetingResource.class);

    final GreetingService greetingService;

    @Inject
    public GreetingResource(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGreeting(@PathParam("id") String id) {
        log.debug("Getting greeting with id {}", id);

        Greeting greeting = greetingService.find(Integer.valueOf(id));
        if (greeting != null) {
            return Response.status(Response.Status.OK).entity(greeting).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postGreeting(Greeting greeting) {
        log.debug("Creating greeting with message {}", greeting.getMessage());

        greeting = greetingService.store(greeting);
        return Response.status(Response.Status.OK).entity(greeting).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteGreeting(@PathParam("id") String id) {
        log.debug("Deleting greeting with id {}", id);

        greetingService.delete(Integer.valueOf(id));
        return Response.status(Response.Status.OK).build();
    }
}
