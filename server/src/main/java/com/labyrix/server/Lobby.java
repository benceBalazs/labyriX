package com.labyrix.server;

import com.labyrix.game.Models.NetworkPlayer;
import java.util.ArrayList;
import java.util.Random;

public class Lobby {
    private ArrayList<String> playerAvatars = new ArrayList<>();
    private ArrayList<NetworkPlayer> networkPlayerList = new ArrayList<>(4);
    private ArrayList<NetworkPlayer> readyPlayers = new ArrayList<>(4);
    private int lobbyId;

    public Lobby(NetworkPlayer networkPlayer, int lobbyId){
        this.networkPlayerList.add(networkPlayer);
        this.lobbyId = lobbyId;
        this.playerAvatars.add("DinoBlue.png");
        this.playerAvatars.add("DinoOrange.png");
        this.playerAvatars.add("DinoPink.png");
        this.playerAvatars.add("img_0116.png");
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

    public ArrayList<String> getPlayerAvatars() {
        return playerAvatars;
    }

    public void setPlayerAvatars(ArrayList<String> playerAvatars) {
        this.playerAvatars = playerAvatars;
    }

    public String givePlayerRandomAvatar(){
        int randomNumber = new Random().nextInt(playerAvatars.size());
        String avatar = this.playerAvatars.get(randomNumber);
        this.playerAvatars.remove(randomNumber);
        return avatar;
    }

    public void removeNetworkPlayerFromLobby(NetworkPlayer networkPlayer) {
        networkPlayerList.remove(networkPlayer);
    }

    public void addNetworkPlayerToLobby(NetworkPlayer networkPlayer) {
        networkPlayerList.add(networkPlayer);
    }

    public void updateNetworkPlayer(int id, NetworkPlayer networkPlayer) {
        networkPlayerList.remove(getNetworkPlayerById(id));
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
