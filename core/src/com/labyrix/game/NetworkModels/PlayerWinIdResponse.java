package com.labyrix.game.NetworkModels;

public class PlayerWinIdResponse {
    private boolean winCondition = false;

    public PlayerWinIdResponse(boolean winCondition) {
        this.winCondition = winCondition;
    }

    public boolean isWinCondition() {
        return winCondition;
    }

    public void setWinCondition(boolean winCondition) {
        this.winCondition = winCondition;
    }
}
