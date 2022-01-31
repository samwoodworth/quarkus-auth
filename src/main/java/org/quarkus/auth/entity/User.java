package org.quarkus.auth.entity;


import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

@Entity
@UserDefinition
public class User extends PanacheEntity {

    @Username
    private String userName;
    @Password
    String password;
    @Roles
    String role;

    public static void add(String userName, String password, String role) {
        User user = new User();
        user.userName = userName;
        //user.password = BcryptUtil.bcryptHash(password);
        user.password = password;
        user.role = role;
        user.persist();
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
}
