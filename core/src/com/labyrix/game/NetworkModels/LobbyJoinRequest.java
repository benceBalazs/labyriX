package com.labyrix.game.NetworkModels;

import com.labyrix.game.Models.NetworkPlayer;

public class LobbyJoinRequest {
    private NetworkPlayer networkPlayer;
    private int lobbyId;

    public LobbyJoinRequest(NetworkPlayer networkPlayer, int lobbyId){
        this.networkPlayer = networkPlayer;
        this.lobbyId = lobbyId;
    }

    public LobbyJoinRequest(){

    }

    public NetworkPlayer getNetworkPlayer() {
        return networkPlayer;
    }

    public void setNetworkPlayer(NetworkPlayer player) {
        this.networkPlayer = networkPlayer;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(int lobbyId) {
        this.lobbyId = lobbyId;
    }
}
