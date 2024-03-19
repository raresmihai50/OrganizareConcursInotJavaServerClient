package org.example.domain;

public class Participant implements Entity<Integer>{
    private int id;
    private String name;
    private int age;
    private Trial[] trials;
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        id = integer;
    }
    public String getName(){
        return name;
    }
    public void setName(String string){
        name = string;
    }
    public int getAge(){
        return age;
    }
    public void setAge(Integer integer){
        age = integer;
    }
    public Trial[] getTrials(){
        return trials;
    }
    public void setTrials(Trial[] events){
        trials = events;
    }
}
