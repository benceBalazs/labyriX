package com.labyrix.game.NetworkModels;

public class UncoverResponse {
    private int playerId;

    public UncoverResponse(int playerId) {
        this.playerId = playerId;
    }

    public UncoverResponse() {

    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
