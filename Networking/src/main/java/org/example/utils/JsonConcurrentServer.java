package org.example.utils;

import java.net.Socket;

import org.example.IService;
import org.example.jsonprotocol.ClientJsonWorker;

public class JsonConcurrentServer extends AbsConcurrentServer {
    private IService server;

    public JsonConcurrentServer(int port, IService chatServer) {
        super(port);
        this.server = chatServer;
        System.out.println("JsonConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientJsonWorker worker = new ClientJsonWorker(server, client);

        Thread tw = new Thread(worker);
        return tw;
    }
}
