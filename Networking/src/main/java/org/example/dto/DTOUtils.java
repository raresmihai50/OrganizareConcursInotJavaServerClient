package org.example.dto;

import org.example.Organizer;

public class DTOUtils {
    public static Organizer getFromDTO(OrganizerDTO organizerDTO){
        String id=organizerDTO.getId();
        String username = organizerDTO.getUsername();
        String password = organizerDTO.getPassword();
        return new Organizer(Integer.parseInt(id), username, password);

    }
}
