package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Optional;

@Entity
public class User extends PanacheEntity {
    @Column(name = "username")
    public String userName;
    @Column(name = "email")
    public String email;
    @Column(name = "password")
    public String password;

    public static Optional<User> findUserByUsername(String userName){
        return find("username", userName).firstResultOptional();
    }

}
