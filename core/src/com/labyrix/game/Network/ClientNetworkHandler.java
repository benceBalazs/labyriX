package com.labyrix.game.Network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.labyrix.game.NetworkModels.LobbyCreateRequest;
import com.labyrix.game.NetworkModels.LobbyCreateResponse;
import com.labyrix.game.NetworkModels.LobbyJoinRequest;
import com.labyrix.game.NetworkModels.LobbyJoinResponse;
import com.labyrix.game.NetworkModels.LobbyLeaveResponse;
import com.labyrix.game.NetworkModels.PlayerStatusRequest;
import com.labyrix.game.NetworkModels.PlayerStatusResponse;
import com.labyrix.game.NetworkModels.PlayerWinIdRequest;
import com.labyrix.game.NetworkModels.PlayerWinIdResponse;
import com.labyrix.game.Screens.GameScreen;
import com.labyrix.game.Screens.JoinScreen;
import com.labyrix.game.Screens.LobbyScreen;

import java.io.IOException;

public class ClientNetworkHandler {
    private static Client client;
    private static ClientNetworkHandler handler;
    private ClientRequestListener clientRequestListener;

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
            Kryo kryo = client.getKryo();
            kryo.register(LobbyCreateRequest.class);
            kryo.register(LobbyCreateResponse.class);
            kryo.register(LobbyJoinRequest.class);
            kryo.register(LobbyJoinResponse.class);
            kryo.register(LobbyLeaveResponse.class);
            kryo.register(PlayerWinIdResponse.class);
            kryo.register(PlayerWinIdRequest.class);
            kryo.register(PlayerStatusResponse.class);
            kryo.register(PlayerStatusRequest.class);
            kryo.register(com.labyrix.game.Models.NetworkPlayer.class);
            kryo.register(java.util.ArrayList.class);
            kryo.register(com.labyrix.game.ENUMS.TurnValue.class);
            kryo.register(com.labyrix.game.NetworkModels.ChangeLobbyToGameRequest.class);
            kryo.register(com.labyrix.game.NetworkModels.ChangeLobbyToGameResponse.class);
            kryo.register(com.labyrix.game.Models.PathField.class);
            clientRequestListener = new ClientRequestListener(client);
            client.addListener(clientRequestListener);
        }
        return client;
    }

    public void startConnection(){
        new Thread(client).start();
        try {
            client.connect(6000, "se2-demo.aau.at", ClientNetworkConfig.TCP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addJoinToClient(JoinScreen joinScreen) {
        this.clientRequestListener.addJoinToListener(joinScreen);
    }

    public void addLobbyToClient(LobbyScreen lobbyScreen) {
        this.clientRequestListener.addLobbyToListener(lobbyScreen);
    }

    public void addGameToClient(GameScreen gameScreen) {
        this.clientRequestListener.addGameToListener(gameScreen);
    }
}
