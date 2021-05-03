package com.labyrix.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerRequestListener extends Listener {
    final private Server server;

    ServerRequestListener(Server server){
        this.server = server;
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        System.out.println("Connected: "+connection.getID());
    }

    public Server getServer() {
        return server;
    }
}
