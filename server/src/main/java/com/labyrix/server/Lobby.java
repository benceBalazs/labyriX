package com.labyrix.server;

import com.labyrix.game.Models.NetworkPlayer;
import java.util.ArrayList;

public class Lobby {
    private ArrayList<NetworkPlayer> networkPlayerList = new ArrayList<>(4);
    private int lobbyId;

    public Lobby(NetworkPlayer networkPlayer, int lobbyId){
        this.networkPlayerList.add(networkPlayer);
        this.lobbyId = lobbyId;
    }

    public ArrayList<NetworkPlayer> getNetworkPlayerList() {
        return networkPlayerList;
    }

    public NetworkPlayer getNetworkPlayerById(int id) {
        for (NetworkPlayer networkPlayer:networkPlayerList) {
            if(networkPlayer.getId() == id){
                return networkPlayer;
            }
        }
        return null;
    }

    public void removeNetworkPlayerFromLobby(NetworkPlayer networkPlayer) {
        networkPlayerList.remove(networkPlayer);
    }

    public void addNetworkPlayerToLobby(NetworkPlayer networkPlayer) {
        networkPlayerList.add(networkPlayer);
    }

    public void setNetworkPlayerList(ArrayList<NetworkPlayer> networkPlayerList) {
        this.networkPlayerList = networkPlayerList;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(int lobbyId) {
        this.lobbyId = lobbyId;
    }
}
