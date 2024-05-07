package org.example.jsonprotocol;

import org.example.Organizer;
import org.example.Participant;
import org.example.Trial;

import java.util.Arrays;
import java.util.List;

public class Response {
    private ResponseType type;
    private String errorMessage;
    private Organizer organizer;
    private Participant participant;
    private Trial trial;
    private List<Trial> trials;
    private List<Participant> participant_list;
    private String trialType;
    private String trialDetails;

    public Response() {
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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

    public void setParticipantList(List<Participant> participant_list) {
        this.participant_list = participant_list;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", errormessage=" + errorMessage + '\'' +
                ", organizer=" + organizer +
                ", participant=" + participant +
                ", trial=" + trial.toString() +
                ", list<Trial>=" + trials.toString() +
                ", list<Participant>=" + participant_list.toString() +
                ", trialType =" + trialType +
                ", trialDetails=" + trialDetails +
                '}';
    }
}
