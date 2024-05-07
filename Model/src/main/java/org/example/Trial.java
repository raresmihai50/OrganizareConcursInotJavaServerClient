package org.example;

import org.example.Entity;

import java.io.Serializable;
import java.util.Objects;

public class Trial implements Entity<Integer>, Serializable {
    private int id;
    private String type;
    private String details;

    public Trial(int id, String type, String details) {
        this.id = id;
        this.type = type;
        this.details = details;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        id = integer;
    }

    public String gettype() {
        return type;
    }

    public void settype(String string) {
        type = string;
    }

    public String getdetails() {
        return details;
    }

    public void setdetails(String string) {
        details = string;
    }

    @Override
    public String toString() {
        return "Type: " + type + ' ' + "Details: " + details;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Trial other = (Trial) obj;
        return Objects.equals(this.id, other.id);
    }

}
