package org.example;

import org.example.utils.AbstractServer;
import org.example.utils.JsonConcurrentServer;
import org.example.utils.ServerException;

import java.io.IOException;
import java.util.Properties;

public class StartJsonServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartJsonServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }
        //OrganizerDBRepository orgRepo = new OrganizerDBRepository(serverProps);
        OrganizerORMRepository orgRepo = new OrganizerORMRepository(serverProps);
        TrialDBRepository trRepo = new TrialDBRepository(serverProps);
        ParticipantDBRepository partRepo = new ParticipantDBRepository(serverProps, trRepo);
        IService serverImpl=new ServiceImpl(orgRepo, partRepo, trRepo);

        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new JsonConcurrentServer(chatServerPort, serverImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
    }
}
