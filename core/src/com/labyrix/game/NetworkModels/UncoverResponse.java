package com.labyrix.game.NetworkModels;

public class UncoverResponse {
    private boolean hasCheated = false;
    private int playerId;

    public UncoverResponse(boolean hasCheated, int playerId) {
        this.hasCheated = hasCheated;
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

    public boolean isHasCheated() {
        return hasCheated;
    }

    public void setHasCheated(boolean hasCheated) {
        this.hasCheated = hasCheated;
    }
}
