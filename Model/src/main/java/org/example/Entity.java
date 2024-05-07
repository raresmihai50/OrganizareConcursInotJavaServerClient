package org.example;

import java.io.Serializable;

public interface Entity<Id> extends Serializable {
    Id getId();
    void setId(Id id);
}

