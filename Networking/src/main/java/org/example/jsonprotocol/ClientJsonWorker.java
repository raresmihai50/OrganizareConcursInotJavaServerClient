package org.example.jsonprotocol;

import com.google.gson.Gson;
import org.example.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientJsonWorker implements Runnable, IObserver {
    private IService server;
    private Socket connection;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private volatile boolean connected;

    public ClientJsonWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        gsonFormatter = new Gson();
        try {
            output = new PrintWriter(connection.getOutputStream());
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (connected) {
            try {
                String requestLine = input.readLine();
                Request request = gsonFormatter.fromJson(requestLine, Request.class);
                Response response = handleRequest(request);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }

    }



    private static Response okResponse = JsonProtocolUtils.createOkResponse();

    private Response handleRequest(Request request) {
        Response response = null;
        if (request.getType() == RequestType.LOGIN) {
            System.out.println("Login request ..." + request.getType());
            Organizer org = request.getOrganizer();
            try {
                server.login(org, this);
                okResponse.setOrganizer(org);
                return okResponse;

            } catch (MessageError e) {
                connected = false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.LOGOUT) {
            System.out.println("Logout request");
            Organizer org = request.getOrganizer();
            try {
                server.logout(org, this);
                okResponse.setOrganizer(org);
                connected = false;
                return okResponse;

            } catch (MessageError e) {
                connected=false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.ADD_PARTICIPANT) {
            System.out.println("Add Participant Request");
            Participant part = request.getParticipant();
            try {
                server.addParticipant(part);
                return okResponse;

            } catch (MessageError e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_PARTICIPANTS) {
            System.out.println("Get Participants Request");
            try {
                okResponse.setParticipantList(server.findAllParticipant());
                return okResponse;

            } catch (MessageError e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_PARTICIPANTS_FILTERED) {
            System.out.println("Get Participants Filtered Request");
            List<Trial> trials = request.getTrials();
            try {
                okResponse.setParticipantList(server.findParticipantsByTrials(trials));
                return okResponse;

            } catch (MessageError e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_TRIAL_BY_TYPE_DETAILS) {
            System.out.println("Get Participants Filtered Request");
            String type = request.getTrialType();
            String details = request.getTrialDetails();
            try {
                okResponse.setTrial(server.findTrialByTypeDetails(type, details));
                return okResponse;

            } catch (MessageError e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }

        return response;
    }

    private void sendResponse(Response response) throws IOException {
        String responseLine = gsonFormatter.toJson(response);
        System.out.println("sending response " + responseLine);
        synchronized (output) {
            output.println(responseLine);
            output.flush();
        }
    }

    @Override
    public void participantAdded(Participant participant) throws MessageError {
        Response resp=JsonProtocolUtils.createAddParticipantResponse(participant);
        System.out.println("Participant Added:  "+participant);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
