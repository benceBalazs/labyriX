package com.labyrix.game.NetworkModels;

public class UncoverResponse {
    private boolean hasCheated = false;

    public UncoverResponse(boolean hasCheated) {
        this.hasCheated = hasCheated;
    }

    public UncoverResponse() {

    }

    public boolean isHasCheated() {
        return hasCheated;
    }

    public void setHasCheated(boolean hasCheated) {
        this.hasCheated = hasCheated;
    }
}
