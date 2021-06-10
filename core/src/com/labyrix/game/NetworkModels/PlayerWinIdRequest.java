package com.labyrix.game.NetworkModels;

public class PlayerWinIdRequest {
    private boolean winCondition = false;

    public PlayerWinIdRequest(boolean winCondition) {
        this.winCondition = winCondition;
    }

    public boolean isWinCondition() {
        return winCondition;
    }

    public void setWinCondition(boolean winCondition) {
        this.winCondition = winCondition;
    }
}
