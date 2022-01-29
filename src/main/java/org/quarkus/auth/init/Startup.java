package org.quarkus.auth.init;

import io.quarkus.runtime.StartupEvent;
import org.quarkus.auth.entity.User;

import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

public class Startup {
    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        User.deleteAll();
        User.add("admin", "admin", "admin");
        User.add("user", "user", "user");
    }
}
