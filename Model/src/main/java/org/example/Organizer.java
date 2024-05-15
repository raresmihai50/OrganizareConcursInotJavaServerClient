package org.example;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Organizer")
public class Organizer implements org.example.Entity<Integer>, Serializable {
    private int id;
    private String username;
    private String password;

    public Organizer(int id, String username, String password) {
        this.id = id;
        this.password = password;
        this.username = username;
    }

    public Organizer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Organizer() {
    }

    @Id
    @GeneratedValue(generator = "increment")
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        id = integer;
    }
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String string) {
        username = string;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String string) {
        password = string;
    }
    @Override
    public int hashCode() {
        return java.util.Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Id=" + id + " " + username + ' ' + password;
    }
}
