package com.labyrix.game.NetworkModels;

import com.labyrix.game.Models.NetworkPlayer;

public class PlayerStatusRequest {
    private NetworkPlayer networkPlayer;

    public PlayerStatusRequest(NetworkPlayer networkPlayer) {
        this.networkPlayer = networkPlayer;
    }

    public PlayerStatusRequest() {

    }

    public NetworkPlayer getNetworkPlayer() {
        return networkPlayer;
    }

    public void setNetworkPlayer(NetworkPlayer player) {
        this.networkPlayer = networkPlayer;
    }
}
