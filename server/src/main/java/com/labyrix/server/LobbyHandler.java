package com.labyrix.server;

import com.labyrix.game.Models.NetworkPlayer;

import java.util.ArrayList;

public class LobbyHandler {
    private ArrayList<Lobby> lobbyList = new ArrayList<>(4);

    LobbyHandler(){

    }

    public Lobby getLobbyById(int id) {
        for (Lobby lobby:lobbyList) {
            if(lobby.getLobbyId() == id){
                return lobby;
            }
        }
        return null;
    }

    public void removeLobby(Lobby lobby) {
        lobbyList.remove(lobby);
    }

    public NetworkPlayer getNetworkPlayerById(int id) {
        for (Lobby lobby:lobbyList) {
            for (NetworkPlayer networkplayer:lobby.getNetworkPlayerList()) {
                if(networkplayer.getId() == id){
                    return networkplayer;
                }
            }
        }
        return null;
    }

    public ArrayList<Lobby> getLobbyList() {
        return lobbyList;
    }

    public void setLobbyList(ArrayList<Lobby> lobbyList) {
        this.lobbyList = lobbyList;
    }
}
