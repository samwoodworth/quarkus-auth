package org.quarkus.auth.controller;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.quarkus.auth.entity.User;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/")
public class SecurityController {

    @Location("home")
    Template homeTemplate;

    @Location("login")
    Template loginTemplate;

    @Location("loggedin")
    Template loggedinTemplate;

    //Assumes unique usernames
    @GET
    @Path("isAuth")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public Response isAuth(@QueryParam("user") String username, @Context SecurityContext sec) {
        //Username exists
        long num = User.count("username", username);
        if (num > 0) {      //Found user
            User foundUser = User.findByUserName(username);
            return Response.ok(foundUser.isLoggedIn()).build();
        } else {
            System.out.println("Not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @PermitAll
    @Path("home")
    public TemplateInstance home(@Context SecurityContext sec) {
        return homeTemplate.data("name");
    }

    @GET
    @PermitAll
    @Path("login")
    public TemplateInstance login(@Context SecurityContext sec) {
        return loginTemplate.data("login");
    }

    @GET
    @RolesAllowed({"user", "admin"})
    @Transactional
    @Path("loggedin")
    public TemplateInstance loggedin(@Context SecurityContext sec) {
        User foundUser = User.findByUserName(sec.getUserPrincipal().getName());
        foundUser.setLoggedIn(true);
        User.update("loggedIn = true where userName = ?1", foundUser.getUserName());
        return loggedinTemplate.data("loggedin");
    }

    @GET
    @Path("get_users")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(User.listAll()).build();
    }

    @GET
    @Path("get_user/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(User.findById(id)).build();
    }
}
