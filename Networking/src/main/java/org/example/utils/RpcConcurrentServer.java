package org.example.utils;

import org.example.IService;
import org.example.rpcprotocol.ClientRpcWorker;

import java.net.Socket;

public class RpcConcurrentServer extends AbsConcurrentServer{
    private IService server;
    public RpcConcurrentServer(int port, IService server) {
        super(port);
        this.server = server;
        System.out.println("Chat- ChatRpcConcurrentServer");
    }
    @Override
    protected Thread createWorker(Socket client) {
        ClientRpcWorker worker=new ClientRpcWorker(server, client);
        //ClientRpcReflectionWorker worker=new ClientRpcReflectionWorker(server, client);

        Thread tw=new Thread(worker);
        return tw;
    }
    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
