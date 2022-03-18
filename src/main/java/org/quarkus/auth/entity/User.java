package org.quarkus.auth.entity;


import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import javax.persistence.Entity;

@Entity
@UserDefinition
public class User extends PanacheEntity {

    @Username
    private String userName;
    @Password
    private String password;
    @Roles
    private String role;
    private boolean loggedIn;

    public User() {}

    public User(String userName, String password, String role, boolean loggedIn) {
        this.userName = userName;
        this.password = BcryptUtil.bcryptHash(password);
        this.role = role;
        this.loggedIn = loggedIn;
    }

    public static User findByUserName(String userName) {
        return find("username", userName).firstResult();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
