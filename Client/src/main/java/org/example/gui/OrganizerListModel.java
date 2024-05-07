package org.example.gui;

import org.example.Organizer;
import org.example.Participant;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizerListModel extends AbstractListModel {
    List<String> organizers;
    public OrganizerListModel(){
        organizers = new ArrayList<String>();
    }

    @Override
    public int getSize() {
        return organizers.size();
    }

    @Override
    public Object getElementAt(int i) {
        return organizers.get(i);
    }
    public void participantAdded(Participant participant){

    }
}
