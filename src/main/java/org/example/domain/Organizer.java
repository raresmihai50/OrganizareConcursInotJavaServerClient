package org.example.domain;

public class Organizer implements Entity<Integer> {
    private int id;
    private String username;
    private String password;
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        id = integer;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String string){
        username = string;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String string){
        password = string;
    }
}
