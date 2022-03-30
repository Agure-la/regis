package org.acme;

import org.acme.model.User;
import org.passay.PasswordGenerator;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Path("/registration")
public class RegistrationResource {

    @Inject
    PasswordGenerator passwordGenerator;

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUserByUsername(@PathParam("username") String username) {
        return User.findUserByUsername(username)
                .map(u -> Response.ok(u).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertUser(User user){

        user.password = passwordGenerator.generate();
        user.persist();

        URI userUri = UriBuilder.fromResource(RegistrationResource.class).path("/" + user.id).build();

        return Response.created(userUri).build();
    }

}