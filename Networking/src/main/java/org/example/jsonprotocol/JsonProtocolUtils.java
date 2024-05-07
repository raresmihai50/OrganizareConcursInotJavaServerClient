package org.example.jsonprotocol;

import com.google.gson.Gson;
import org.example.IObserver;
import org.example.Organizer;
import org.example.Participant;
import org.example.Trial;
import org.example.dto.DTOUtils;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class JsonProtocolUtils {
    public static Response createOkResponse() {
        Response resp = new Response();
        resp.setType(ResponseType.OK);
        return resp;
    }

    public static Response createErrorResponse(String errorMessage) {
        Response resp = new Response();
        resp.setType(ResponseType.ERROR);
        resp.setErrorMessage(errorMessage);
        return resp;
    }

    public static Request createLoginRequest(Organizer organizer) {
        Request req = new Request();
        req.setType(RequestType.LOGIN);
        req.setOrganizer(organizer);
        return req;
    }

    public static Request createLogoutRequest(Organizer organizer) {
        Request req = new Request();
        req.setType(RequestType.LOGOUT);
        req.setOrganizer(organizer);
        return req;
    }

    public static Request createAddParticipantRequest(Participant participant) {
        Request req = new Request();
        req.setType(RequestType.ADD_PARTICIPANT);
        req.setParticipant(participant);
        return req;
    }

    public static Response createAddParticipantResponse(Participant participant) {
        Response res = new Response();
        res.setType(ResponseType.ADD_PARTICIPANT);
        res.setParticipant(participant);
        return res;
    }

    public static Response createGetParticipantsResponse(List<Participant> participant_list) {
        Response res = new Response();
        res.setType(ResponseType.GET_PARTICIPANTS);
        res.setParticipantList(participant_list);
        return res;
    }

    public static Request createGetParticipantsRequest() {
        Request req = new Request();
        req.setType(RequestType.GET_PARTICIPANTS);
        return req;
    }

    public static Request createFindTrialByTypeDetailsRequest(String type, String details) {
        Request req = new Request();
        req.setTrialType(type);
        req.setTrialDetails(details);
        req.setType(RequestType.GET_TRIAL_BY_TYPE_DETAILS);
        return req;
    }

    public static Response createFindTrialByTypeDetailsResponse(String type, String details) {
        Response response = new Response();
        response.setTrialType(type);
        response.setTrialDetails(details);
        response.setType(ResponseType.GET_TRIAL_BY_TYPE_DETAILS);
        return response;
    }

    public static Request createGetParticipantsByTrialsRequest(List<Trial> trials) {
        Request req = new Request();
        req.setTrials(trials);
        req.setType(RequestType.GET_PARTICIPANTS_FILTERED);
        return req;
    }

    public static Response createGetParticipantsByTrialsResponse(List<Trial> trials) {
        Response response = new Response();
        response.setType(ResponseType.GET_PARTICIPANTS_FILTERED);
        response.setTrials(trials);
        return response;
    }


}
