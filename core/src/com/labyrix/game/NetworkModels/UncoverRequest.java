package com.labyrix.game.NetworkModels;

public class UncoverRequest {
    private int id;

    public UncoverRequest(){
    }

    public UncoverRequest(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
