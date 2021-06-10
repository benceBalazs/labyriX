package com.labyrix.game.NetworkModels;

import com.labyrix.game.Models.NetworkPlayer;

import java.util.ArrayList;

public class PlayerStatusResponse {
    private ArrayList<NetworkPlayer> networkPlayerList;

    public PlayerStatusResponse(){

    }

    public ArrayList<NetworkPlayer> getNetworkPlayerList() {
        return networkPlayerList;
    }

    public void setNetworkPlayerList(ArrayList<NetworkPlayer> networkPlayerList) {
        this.networkPlayerList = networkPlayerList;
    }
}
