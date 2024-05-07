package org.example;


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Participant implements Entity<Integer>, Serializable {
    private int id;
    private String name;
    private int age;
    private List<Trial> trials;

    public Participant(int id, String name, int age, List<Trial> trials) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.trials = trials;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        id = integer;
    }

    public String getName() {
        return name;
    }

    public void setName(String string) {
        name = string;
    }

    public int getAge() {
        return age;
    }

    public void setAge(Integer integer) {
        age = integer;
    }

    public List<Trial> getTrials() {
        return trials;
    }

    public void setTrials(List<Trial> events) {
        trials = events;
    }

    @Override
    public String toString() {
        return "Id=" + id + " " + name + ' ' + age + ' ' + trials.toString();
    }
}
