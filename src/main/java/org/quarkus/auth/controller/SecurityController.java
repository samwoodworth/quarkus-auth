package org.quarkus.auth.controller;

import org.quarkus.auth.entity.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class SecurityController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "works";
    }

    @GET
    @Path("/get_users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<User> users = User.listAll();
        return Response.ok(users).build();
    }

    @GET
    @Path("/get_user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        User foundUser = User.findById(id);
        return Response.ok(foundUser).build();
    }
}
