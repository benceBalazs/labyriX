package com.labyrix.server;

import com.labyrix.game.Models.NetworkPlayer;
import java.util.ArrayList;

public class Lobby {
    private ArrayList<NetworkPlayer> networkPlayerList = new ArrayList<>(4);
    private ArrayList<NetworkPlayer> readyPlayers = new ArrayList<>(4);
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

    public void removeReadyPlayer(NetworkPlayer networkPlayer) {
        readyPlayers.remove(networkPlayer);
    }

    public void resetReadyPlayer() {
        readyPlayers.clear();
    }

    public void addReadyPlayer(NetworkPlayer networkPlayer) {
        readyPlayers.add(networkPlayer);
    }

    public ArrayList<NetworkPlayer> getReadyPlayers() {
        return readyPlayers;
    }

    public void setReadyPlayers(ArrayList<NetworkPlayer> readyPlayers) {
        this.readyPlayers = readyPlayers;
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
