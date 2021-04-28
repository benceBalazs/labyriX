package com.labyrix.game;

public class Trap {
    private float probability;
    private TrapEvent event;

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
