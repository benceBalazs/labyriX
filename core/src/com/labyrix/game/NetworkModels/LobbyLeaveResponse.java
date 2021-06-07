package com.labyrix.game.NetworkModels;

import com.labyrix.game.Models.NetworkPlayer;

import java.util.ArrayList;

public class LobbyLeaveResponse {
    private ArrayList<NetworkPlayer> networkPlayerList;

    public LobbyLeaveResponse(){

    }

    public ArrayList<NetworkPlayer> getNetworkPlayerList() {
        return networkPlayerList;
    }

    public void setNetworkPlayerList(ArrayList<NetworkPlayer> networkPlayerList) {
        this.networkPlayerList = networkPlayerList;
    }
}
