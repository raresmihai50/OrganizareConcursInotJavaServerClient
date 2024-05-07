package org.example;

import java.io.Serializable;

public class Organizer implements Entity<Integer>, Serializable {
    private int id;
    private String username;
    private String password;

    public Organizer(int id, String username, String password) {
        this.id = id;
        this.password = password;
        this.username = username;
    }

    public Organizer(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        id = integer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String string) {
        username = string;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String string) {
        password = string;
    }

    @Override
    public String toString() {
        return "Id=" + id + " " + username + ' ' + password;
    }
}
