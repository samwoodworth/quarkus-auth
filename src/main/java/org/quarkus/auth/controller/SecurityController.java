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

    @Location("login")
    Template loginTemplate;

    @Location("loggedin")
    Template loggedinTemplate;

    @Location("error")
    Template errorTemplate;

    @GET
    @PermitAll
    @Path("login")
    public TemplateInstance login() {
        return loginTemplate.data("");
    }

    @GET
    @PermitAll
    @Path("error")
    public TemplateInstance error() {
        return errorTemplate.data("");
    }

    @GET
    @RolesAllowed({"user", "admin"})
    @Transactional
    @Path("loggedin")
    public TemplateInstance loggedin(@Context SecurityContext sec) {
        User foundUser = User.findByUserName(sec.getUserPrincipal().getName());
        User.update("loggedIn = true where userName = ?1", foundUser.getUserName());
        return loggedinTemplate.data("");
    }

    //Used for testing to see if login status changes
    @GET
    @Path("isAuth")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public Response isAuth(@QueryParam("user") String username, @Context SecurityContext sec) {
        //Checking if username exists
        if (User.count("username", username) > 0) {
            User foundUser = User.findByUserName(username);
            return Response.ok(foundUser.isLoggedIn()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    //For testing purposes
    @GET
    @Transactional
    @RolesAllowed({"user", "admin"})
    @Path("logout")
    public Response logout(@Context SecurityContext sec, @QueryParam("username") String username) {

        String credName = User.findByUserName(sec.getUserPrincipal().getName()).getUserName();
        if (User.count("username", username) > 0 && credName.equals(username)) {
            User foundUser = User.findByUserName(username);
            User.update("loggedIn = false where userName = ?1", foundUser.getUserName());
            return Response.ok().build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    //For testing purposes
    @GET
    @Path("get_users")
    @RolesAllowed({"user", "admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(User.listAll()).build();
    }

    //For testing purposes
    @GET
    @Path("get_user/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(User.findById(id)).build();
    }
}
