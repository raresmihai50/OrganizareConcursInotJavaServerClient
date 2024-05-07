package org.example.dto;

import java.io.Serializable;

public class TrialDTO implements Serializable {
    private String id;
    private String type;
    private String details;

    public TrialDTO(String id, String type, String details) {
        this.id = id;
        this.type = type;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
