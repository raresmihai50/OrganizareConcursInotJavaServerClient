package org.example.jsonprotocol;

import org.example.Organizer;
import org.example.Participant;
import org.example.Trial;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Request {
    private RequestType type;
    Organizer organizer;
    Participant participant;
    Trial trial;
    List<Trial> trials;
    List<Participant> participant_list;
    String trialType;
    String trialDetails;

    public Request() {
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Trial getTrial() {
        return trial;
    }

    public void setTrial(Trial trial) {
        this.trial = trial;
    }
    public List<Trial> getTrials() {
        return trials;
    }

    public void setTrials(List<Trial> trials) {
        this.trials = trials;
    }

    public List<Participant> getParticipantList() {
        return participant_list;
    }

    public void setParticipantList(List<Participant> participant_list) {
        this.participant_list = participant_list;
    }

    public void setTrialType(String type) {
        this.trialType = type;
    }

    public String getTrialType() {
        return trialType;
    }

    public void setTrialDetails(String details) {
        this.trialDetails = details;
    }

    public String getTrialDetails() {
        return trialDetails;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                ", organizer=" + organizer +
                ", participant=" + participant +
                ", trial=" + trial.toString() +
                ",list<Trial>=" + trials.toString() +
                ", list<Participant>=" + participant_list.toString() +
                ", trialType =" + trialType +
                ", trialDetails=" + trialDetails +
                '}';
    }
}
