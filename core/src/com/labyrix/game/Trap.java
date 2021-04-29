package com.labyrix.game;

public class Trap {
    private float probability;
    private TrapEvent event;


    public Trap (float probability){
        this.probability = probability;
        this.event = new TrapEvent();
    }


    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    public TrapEvent getEvent() {
        return event;
    }

    public void setEvent(TrapEvent event) {
        this.event = event;
    }
}