package com.labyrix.game.Network;

import com.esotericsoftware.kryonet.Client;
import java.io.IOException;

public class ClientNetworkHandler {
    private static Client client;
    private static ClientNetworkHandler handler;

    private ClientNetworkHandler(){ }

    public static ClientNetworkHandler getInstance(){
        if(handler == null){
            handler = new ClientNetworkHandler();
        }
        return handler;
    }

    public Client getClient(){
        if (client == null) {
            client = new Client();
            client.addListener(new ClientRequestListener(client));
        }
        return client;
    }

    public void startConnection(){
        new Thread(client).start();
        try {
            client.connect(6000, "yourIpAddress", ClientNetworkConfig.TCP, ClientNetworkConfig.UDP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}