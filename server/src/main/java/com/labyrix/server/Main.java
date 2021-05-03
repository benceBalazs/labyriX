package com.labyrix.server;

import com.esotericsoftware.kryonet.Server;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        try {
            server.bind(ServerConfig.TCP, ServerConfig.UDP);
            server.addListener(new ServerRequestListener(server));
            System.out.println("Listening on TCP port: " + ServerConfig.TCP + " and UDP Port: "+ ServerConfig.UDP);
        } catch(IOException ioe) {
            System.err.println("Failed to initiate. Server closed.");
        }

    }
}
