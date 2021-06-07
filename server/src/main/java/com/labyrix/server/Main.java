package com.labyrix.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.labyrix.game.NetworkModels.LobbyCreateRequest;
import com.labyrix.game.NetworkModels.LobbyCreateResponse;
import com.labyrix.game.NetworkModels.LobbyJoinRequest;
import com.labyrix.game.NetworkModels.LobbyJoinResponse;
import com.labyrix.game.NetworkModels.LobbyLeaveResponse;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        Kryo kryo = server.getKryo();
        kryo.register(LobbyCreateRequest.class);
        kryo.register(LobbyCreateResponse.class);
        kryo.register(LobbyJoinRequest.class);
        kryo.register(LobbyJoinResponse.class);
        kryo.register(LobbyLeaveResponse.class);
        kryo.register(com.labyrix.game.Models.NetworkPlayer.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(com.labyrix.game.ENUMS.TurnValue.class);
        new Thread(server).start();
        try {
            server.bind(ServerNetworkConfig.TCP);
            server.addListener(new ServerRequestListener(server));
            System.out.println("Listening on TCP port: " + ServerNetworkConfig.TCP);
        } catch(IOException ioe) {
            System.err.println("Failed to initiate. Server closed.");
        }
    }
}
