package org.quarkus.auth.controller;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.quarkus.auth.entity.User;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/")
//@RegisterRestClient
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
    public boolean isAuth(@QueryParam("user") String username, @Context SecurityContext sec) {
        //Username exists
        long num = User.count("username", username);
        if (num > 0) {      //Found user
            User foundUser = User.findByUserName(username);
            if (foundUser.isLoggedIn()) {
                System.out.println("Role is: " + foundUser.getRole());
                foundUser.setLoggedIn(true);
                foundUser.persist();
                return true;
            }
            System.out.println("User " + foundUser.getUserName() + " already logged in.");
            return true;
        } else {
            System.out.println("Not found");
            return false;
        }
    }

    @GET
    @PermitAll
    @Path("home")
    public TemplateInstance home(@Context SecurityContext sec) {
        if (sec.getUserPrincipal() != null) {
            String name = sec.getUserPrincipal().getName();
            System.out.println(name);
        } else
            System.out.println("Null");
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
    @Path("loggedin")
    public TemplateInstance loggedin(@Context SecurityContext sec) {
        System.out.println(sec.getUserPrincipal().getName());

        return loggedinTemplate.data("loggedin");
    }

    @GET
    @Path("get_users")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<User> users = User.listAll();
        return Response.ok(users).build();
    }

    @GET
    @Path("get_user/{id}")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        User foundUser = User.findById(id);
        return Response.ok(foundUser).build();
    }
}
