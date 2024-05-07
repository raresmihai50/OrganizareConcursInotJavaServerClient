package org.example.jsonprotocol;

import com.google.gson.Gson;
import org.example.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesJsonProxy implements IService {
    private String host;
    private int port;

    private IObserver client;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ServicesJsonProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }

    private void initializeConnection() throws MessageError {
        try {
            gsonFormatter = new Gson();
            connection = new Socket(host, port);
            output = new PrintWriter(connection.getOutputStream());
            output.flush();
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request) throws MessageError {
        String reqLine = gsonFormatter.toJson(request);
        try {
            output.println(reqLine);
            output.flush();
        } catch (Exception e) {
            throw new MessageError("Error sending object " + e);
        }

    }

    private Response readResponse() throws MessageError {
        Response response = null;
        try {

            response = qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public void login(Organizer organizer, IObserver observer) throws MessageError {
        initializeConnection();

        Request req = JsonProtocolUtils.createLoginRequest(organizer);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.OK) {
            this.client = observer;
            return;
        }
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            closeConnection();
            throw new MessageError(err);
        }
    }

    @Override
    public void logout(Organizer organizer, IObserver observer) throws MessageError {
        Request req = JsonProtocolUtils.createLogoutRequest(organizer);
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();//data().toString();
        }
    }

    @Override
    public List<Participant> findParticipantsByTrials(List<Trial> trials) throws MessageError {
        Request req = JsonProtocolUtils.createGetParticipantsByTrialsRequest(trials);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();//data().toString();
            throw new MessageError(err);
        }
        return response.getParticipantList();
    }

    @Override
    public List<Participant> findAllParticipant() throws MessageError {
        Request req = JsonProtocolUtils.createGetParticipantsRequest();
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();//data().toString();
            throw new MessageError(err);
        }
        return response.getParticipantList();
    }

    @Override
    public void addParticipant(Participant participant) throws MessageError {
        Request req = JsonProtocolUtils.createAddParticipantRequest(participant);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();//data().toString();
            throw new MessageError(err);
        }
    }

    @Override
    public Trial findTrialByTypeDetails(String type, String details) throws MessageError {
        Request req = JsonProtocolUtils.createFindTrialByTypeDetailsRequest(type, details);
        sendRequest(req);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();//data().toString();
            throw new MessageError(err);
        }
        return response.getTrial();
    }

    private void handleUpdate(Response response) {
        if (response.getType() == ResponseType.ADD_PARTICIPANT) {

            Participant participant = response.getParticipant();
            System.out.println("Participant Added " + participant);
            try {
                client.participantAdded(participant);
            } catch (MessageError e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response) {
        //if(response == null) return false;
        return response.getType() == ResponseType.ADD_PARTICIPANT;
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    String responseLine = input.readLine();
                    System.out.println("response received " + responseLine);

                    if (responseLine == null) {
                        responseLine = input.readLine();
                    }//asta rezolva eroarea de la response = null oarecum

                    Response response = gsonFormatter.fromJson(responseLine, Response.class);
                    if (isUpdate(response)) {
                        handleUpdate(response);
                    } else {

                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}
