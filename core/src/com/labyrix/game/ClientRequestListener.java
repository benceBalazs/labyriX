package com.labyrix.game;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientRequestListener extends Listener {
    private final Client client;

    ClientRequestListener(Client client){
        this.client = client;
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        System.out.println("Connected: "+connection.getID());
    }

    public Client getClient() {
        return client;
    }
}
