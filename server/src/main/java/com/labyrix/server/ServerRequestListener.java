package com.labyrix.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.labyrix.game.Models.NetworkPlayer;
import com.labyrix.game.NetworkModels.ChangeLobbyToGameRequest;
import com.labyrix.game.NetworkModels.ChangeLobbyToGameResponse;
import com.labyrix.game.NetworkModels.LobbyCreateRequest;
import com.labyrix.game.NetworkModels.LobbyCreateResponse;
import com.labyrix.game.NetworkModels.LobbyJoinRequest;
import com.labyrix.game.NetworkModels.LobbyJoinResponse;
import com.labyrix.game.NetworkModels.LobbyLeaveResponse;
import com.labyrix.game.NetworkModels.PlayerStatusRequest;
import com.labyrix.game.NetworkModels.PlayerStatusResponse;
import com.labyrix.game.NetworkModels.PlayerWinIdRequest;
import com.labyrix.game.NetworkModels.PlayerWinIdResponse;
import com.labyrix.game.NetworkModels.UncoverRequest;
import com.labyrix.game.NetworkModels.UncoverResponse;

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

        if (object instanceof PlayerStatusRequest){
            PlayerStatusResponse playerStatusResponse = new PlayerStatusResponse();
            int networkPlayerLobby = lobbyHandler.getNetworkPlayerById(connection.getID()).getLobbyId();
            if (lobbyHandler.getLobbyById(networkPlayerLobby).getReadyPlayers().size() < lobbyHandler.getLobbyById(networkPlayerLobby).getNetworkPlayerList().size()){
                System.out.println("Sent Player: "+((PlayerStatusRequest) object).getNetworkPlayer());
                System.out.println("Lobby: "+lobbyHandler.getLobbyById(((PlayerStatusRequest) object).getNetworkPlayer().getLobbyId()));
                lobbyHandler.getLobbyById(((PlayerStatusRequest) object).getNetworkPlayer().getLobbyId()).updateNetworkPlayer(((PlayerStatusRequest) object).getNetworkPlayer().getId(),((PlayerStatusRequest) object).getNetworkPlayer());
                lobbyHandler.getLobbyById(networkPlayerLobby).addReadyPlayer(lobbyHandler.getNetworkPlayerById(connection.getID()));
                if(lobbyHandler.getLobbyById(networkPlayerLobby).getReadyPlayers().size() == lobbyHandler.getLobbyById(networkPlayerLobby).getNetworkPlayerList().size()){
                    playerStatusResponse.setNetworkPlayerList(lobbyHandler.getLobbyById(networkPlayerLobby).getReadyPlayers());
                    server.sendToAllTCP(playerStatusResponse);
                    lobbyHandler.getLobbyById(networkPlayerLobby).resetReadyPlayer();
                }
            }
        }

        if (object instanceof PlayerWinIdRequest){
            for (NetworkPlayer networkPlayer : lobbyHandler.getLobbyById(lobbyHandler.getNetworkPlayerById(connection.getID()).getLobbyId()).getNetworkPlayerList()) {
                if (networkPlayer.getId() != connection.getID()){
                    server.sendToTCP(networkPlayer.getId(),new PlayerWinIdResponse(false));
                }else{
                    if(((PlayerWinIdRequest) object).isWinCondition()){
                        server.sendToTCP(networkPlayer.getId(),new PlayerWinIdResponse(true));
                    }
                }
            }
        }

        if (object instanceof ChangeLobbyToGameRequest){
            server.sendToAllTCP(new ChangeLobbyToGameResponse());
        }

        if (object instanceof UncoverRequest){
            System.out.println("Got request to check: "+((UncoverRequest) object).getId());
            if(lobbyHandler.getNetworkPlayerById(((UncoverRequest) object).getId()).getHasCheated() > 0){
                for (NetworkPlayer networkPlayer:lobbyHandler.getLobbyById(lobbyHandler.getNetworkPlayerById(((UncoverRequest) object).getId()).getLobbyId()).getNetworkPlayerList()) {
                    server.sendToTCP(networkPlayer.getId(),new UncoverResponse(true, ((UncoverRequest) object).getId()));
                }
                System.out.println("Sent: true");
            }else{
                for (NetworkPlayer networkPlayer:lobbyHandler.getLobbyById(lobbyHandler.getNetworkPlayerById(((UncoverRequest) object).getId()).getLobbyId()).getNetworkPlayerList()) {
                    server.sendToTCP(networkPlayer.getId(),new UncoverResponse(false, ((UncoverRequest) object).getId()));
                }
                System.out.println("Sent: false");
            }
        }

        System.out.println("########### All Lobbies ###########");
        if (lobbyHandler.getLobbyList().size()>0){
            for (Lobby lobby : lobbyHandler.getLobbyList()) {
                System.out.println("Lobby: "+lobby.getLobbyId());
                System.out.println("PLAYERS ----------------");
                for (NetworkPlayer networkPlayer : lobby.getNetworkPlayerList()) {
                    System.out.println("+ "+networkPlayer.getName()+" ID: "+networkPlayer.getId());
                }
                System.out.println("READY ----------------");
                for (NetworkPlayer networkPlayer : lobby.getReadyPlayers()) {
                    System.out.println("+ "+networkPlayer.getName()+" ID: "+networkPlayer.getId());
                }
            }
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
        }else{
            server.sendToAllTCP(lobbyLeaveResponse);
        }
    }

    public int randomNumberGenerator(){
        return new Random().nextInt(10000);
    }

    public Server getServer() {
        return server;
    }
}
