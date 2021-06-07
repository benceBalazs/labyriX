package com.labyrix.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.labyrix.game.Models.NetworkPlayer;
import com.labyrix.game.NetworkModels.LobbyCreateRequest;
import com.labyrix.game.NetworkModels.LobbyCreateResponse;
import com.labyrix.game.NetworkModels.LobbyJoinRequest;
import com.labyrix.game.NetworkModels.LobbyJoinResponse;
import com.labyrix.game.NetworkModels.LobbyLeaveResponse;

import java.util.Random;

public class ServerRequestListener extends Listener {
    final private Server server;
    private LobbyHandler lobbyHandler;

    ServerRequestListener(Server server){
        this.server = server;
        this.lobbyHandler = new LobbyHandler();
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        System.out.println("Connected: "+connection.getID());
    }

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
        if (object instanceof LobbyCreateRequest){
            int newId = randomNumberGenerator();
            while(lobbyHandler.getLobbyById(newId) != null){
                newId = randomNumberGenerator();
            }
            ((LobbyCreateRequest) object).getNetworkPlayer().setId(connection.getID());
            ((LobbyCreateRequest) object).getNetworkPlayer().setLobbyId(newId);
            Lobby newLobby = new Lobby(((LobbyCreateRequest) object).getNetworkPlayer(),newId);
            lobbyHandler.getLobbyList().add(newLobby);

            LobbyCreateResponse lobbyCreateResponse = new LobbyCreateResponse();
            lobbyCreateResponse.setLobbyId(newId);
            server.sendToTCP(connection.getID(), lobbyCreateResponse);
        }

        if (object instanceof LobbyJoinRequest){
            LobbyJoinResponse lobbyJoinResponse = new LobbyJoinResponse();
            if(lobbyHandler.getLobbyById(((LobbyJoinRequest) object).getLobbyId()) != null &&
                    lobbyHandler.getLobbyById(((LobbyJoinRequest) object).getLobbyId()).getNetworkPlayerList().size() < 4){

                ((LobbyJoinRequest) object).getNetworkPlayer().setId(connection.getID());
                ((LobbyJoinRequest) object).getNetworkPlayer().setLobbyId(((LobbyJoinRequest) object).getLobbyId());
                lobbyHandler.getLobbyById(((LobbyJoinRequest) object).getLobbyId()).addNetworkPlayerToLobby(((LobbyJoinRequest) object).getNetworkPlayer());
                lobbyJoinResponse.setNetworkPlayerList(lobbyHandler.getLobbyById(((LobbyJoinRequest) object).getLobbyId()).getNetworkPlayerList());
                server.sendToAllTCP(lobbyJoinResponse);
            }
            server.sendToTCP(connection.getID(),lobbyJoinResponse);
        }

        System.out.println("########### All Lobbies ###########");
        for (Lobby lobby : lobbyHandler.getLobbyList()) {
            System.out.print("Lobby: "+lobby.getLobbyId());
            System.out.println("  ----------------");
            for (NetworkPlayer networkPlayer : lobby.getNetworkPlayerList()) {
                System.out.println("+ "+networkPlayer.getName()+" ID: "+networkPlayer.getId());
            }
            System.out.println("\n");
        }
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
        LobbyLeaveResponse lobbyLeaveResponse = new LobbyLeaveResponse();
        int networkPlayerLobby = lobbyHandler.getNetworkPlayerById(connection.getID()).getLobbyId();
        System.out.println(lobbyHandler.getNetworkPlayerById(connection.getID()).getLobbyId());
        lobbyHandler.getLobbyById(networkPlayerLobby).removeNetworkPlayerFromLobby(lobbyHandler.getNetworkPlayerById(connection.getID()));
        lobbyLeaveResponse.setNetworkPlayerList(lobbyHandler.getLobbyById(networkPlayerLobby).getNetworkPlayerList());
        if(lobbyHandler.getLobbyById(networkPlayerLobby).getNetworkPlayerList().size() == 0){
            lobbyHandler.removeLobby(lobbyHandler.getLobbyById(networkPlayerLobby));
        }
        server.sendToAllTCP(lobbyLeaveResponse);
    }

    public int randomNumberGenerator(){
        return new Random().nextInt(10000);
    }

    public Server getServer() {
        return server;
    }
}
