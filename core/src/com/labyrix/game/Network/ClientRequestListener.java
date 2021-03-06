package com.labyrix.game.Network;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.labyrix.game.Models.NetworkPlayer;
import com.labyrix.game.NetworkModels.ChangeLobbyToGameResponse;
import com.labyrix.game.NetworkModels.LobbyCreateResponse;
import com.labyrix.game.NetworkModels.LobbyJoinResponse;
import com.labyrix.game.NetworkModels.LobbyLeaveResponse;
import com.labyrix.game.NetworkModels.PlayerStatusResponse;
import com.labyrix.game.NetworkModels.PlayerWinIdResponse;
import com.labyrix.game.NetworkModels.UncoverResponse;
import com.labyrix.game.Screens.GameScreen;
import com.labyrix.game.Screens.JoinScreen;
import com.labyrix.game.Screens.LobbyScreen;

public class ClientRequestListener extends Listener {
    private final Client client;
    private JoinScreen joinScreen;
    private LobbyScreen lobbyScreen;
    private GameScreen gameScreen;

    ClientRequestListener(Client client){
        this.client = client;
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        System.out.println("Connected: "+connection.getID());
    }

    public void addJoinToListener(JoinScreen joinScreen) {
        this.joinScreen = joinScreen;
    }

    public void addLobbyToListener(LobbyScreen lobbyScreen) {
        this.lobbyScreen = lobbyScreen;
    }

    public void addGameToListener(GameScreen gameScreen) { this.gameScreen = gameScreen; }

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
        if (object instanceof LobbyCreateResponse){
            int lobbyId = ((LobbyCreateResponse) object).getLobbyId();
            joinScreen.setLobbyCodeReturn(String.valueOf(lobbyId));
            joinScreen.changeToLobby();
            lobbyScreen.setInLobby(true);
            lobbyScreen.setMainPlayerId(connection.getID());
            System.out.println(lobbyScreen.getMainPlayerId());
        }

        if (object instanceof LobbyJoinResponse){
            if(((LobbyJoinResponse) object).getNetworkPlayerList() != null){
                if(!lobbyScreen.isInLobby()){
                    joinScreen.setNetworkPlayers(((LobbyJoinResponse) object).getNetworkPlayerList());
                    joinScreen.changeToLobby();
                    lobbyScreen.setMainPlayerId(connection.getID());
                    System.out.println(lobbyScreen.getMainPlayerId());
                }else {
                    lobbyScreen.setNetworkPlayers(((LobbyJoinResponse) object).getNetworkPlayerList());
                }
                lobbyScreen.updatePlayers(((LobbyJoinResponse) object).getNetworkPlayerList().size());
            }
            for (NetworkPlayer np:((LobbyJoinResponse) object).getNetworkPlayerList()) {
                System.out.println("PlayerID: "+np.getId()+" and Lobby: "+np.getLobbyId());
            }
        }

        if (object instanceof LobbyLeaveResponse){
            lobbyScreen.setNetworkPlayers(((LobbyLeaveResponse) object).getNetworkPlayerList());
            lobbyScreen.updatePlayers(((LobbyLeaveResponse) object).getNetworkPlayerList().size());
        }

        if (object instanceof PlayerStatusResponse){
            gameScreen.getTl().playerReturnServer(((PlayerStatusResponse) object).getNetworkPlayerList());
        }

        if (object instanceof PlayerWinIdResponse){
            gameScreen.switchToEnd(((PlayerWinIdResponse) object).isWinCondition());
        }

        if (object instanceof ChangeLobbyToGameResponse){
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    gameScreen.setMainPlayerId(lobbyScreen.getMainPlayerId());
                    gameScreen.setNetworkPlayers(lobbyScreen.getNetworkPlayers());
                    lobbyScreen.changeToGame();
                }
            });
        }

        if (object instanceof UncoverResponse){
            gameScreen.getTl().cheatingPlayerMovementspeed(((UncoverResponse) object).getPlayerId());
        }
    }

    public Client getClient() {
        return client;
    }
}
