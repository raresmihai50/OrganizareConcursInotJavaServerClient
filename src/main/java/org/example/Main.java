package org.example;

import org.example.domain.Organizer;
import org.example.domain.Participant;
import org.example.domain.Trial;
import org.example.repository.OrganizerDBRepository;
import org.example.repository.ParticipantDBRepository;
import org.example.repository.TrialDBRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
        OrganizerDBRepository orgrepo = new OrganizerDBRepository(props);
        TrialDBRepository trrepo = new TrialDBRepository(props);
        ParticipantDBRepository partrepo = new ParticipantDBRepository(props, trrepo);
        //orgrepo.addOrganizer(new Organizer(1, "Rares", "123"));
        //trrepo.addTrial(new Trial(1,"Distanta","50m"));
        //List<Trial> lst = new ArrayList<Trial>();
        //lst.add(trrepo.findByIdTrial(1));
        //partrepo.addParticipant(new Participant(1, "Radu", 20, lst));
        //System.out.println(partrepo.findByIdParticipant(1).toString());
    }
}
