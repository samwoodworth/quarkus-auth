package org.quarkus.auth.controller;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.quarkus.auth.entity.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/")
public class SecurityController {

    @Location("home")
    Template homeTemplate;

    @Location("login")
    Template loginTemplate;

    @Location("loggedin")
    Template loggedinTemplate;

    @GET
    @Path("home")
    public TemplateInstance home(@Context SecurityContext securityContext) {
/*        String name = securityContext.getUserPrincipal().getName();
        if (name != null)
            System.out.println("Not null");*/

        return homeTemplate.data("name");
    }

    @GET
    @Path("login")
    public TemplateInstance login() {
        return loginTemplate.data("login");
    }

    @GET
    @Path("loggedin")
    public TemplateInstance loggedin() {
        return loggedinTemplate.data("loggedin");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "works";
    }

    @GET
    @Path("get_users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<User> users = User.listAll();
        return Response.ok(users).build();
    }

    @GET
    @Path("get_user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        User foundUser = User.findById(id);
        return Response.ok(foundUser).build();
    }
}
