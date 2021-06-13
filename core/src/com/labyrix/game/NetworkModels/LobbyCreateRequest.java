package com.labyrix.game.NetworkModels;

import com.labyrix.game.Models.NetworkPlayer;

public class LobbyCreateRequest {
    private NetworkPlayer networkPlayer;

    public LobbyCreateRequest(NetworkPlayer networkPlayer){
        this.networkPlayer = networkPlayer;
    }

    public LobbyCreateRequest(){

    }

    public NetworkPlayer getNetworkPlayer() {
        return networkPlayer;
    }

    public void setNetworkPlayer(NetworkPlayer networkPlayer) {
        this.networkPlayer = networkPlayer;
    }
}
