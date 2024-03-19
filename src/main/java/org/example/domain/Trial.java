package org.example.domain;

import org.example.domain.Entity;

public class Trial implements Entity<Integer> {
    private int id;
    private String type;
    private String details;
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        id = integer;
    }
    public String gettype(){
        return type;
    }
    public void settype(String string){
        type = string;
    }
    public String getdetails(){
        return details;
    }
    public void setdetails(String string){
        details = string;
    }
    
}
