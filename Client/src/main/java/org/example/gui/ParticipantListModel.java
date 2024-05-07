package org.example.gui;

import org.example.Participant;

import javax.swing.*;
import java.util.List;

public class ParticipantListModel extends AbstractListModel {
    List<String> participants;

    @Override
    public int getSize() {
        return participants.size();
    }

    @Override
    public Object getElementAt(int i) {
        return participants.get(i);
    }
    public void participantAdded(String id){
        participants.add(id);
        fireContentsChanged(this,participants.size()-1,participants.size());
    }
}
