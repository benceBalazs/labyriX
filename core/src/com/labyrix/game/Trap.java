package com.labyrix.game;

import java.util.Random;

public class Trap {
    private float probability;
    private TrapEvent event;

    public Trap (float probability){
        this.probability = probability;
        this.event = new TrapEvent();
    }

    public boolean isTrapActivated () {
        boolean result = false;

        Random random = new Random();
        int randomInt = random.nextInt(101);

        float randomFloat = (float) randomInt / 100;

        if (randomFloat <= this.probability){
            result = true;
        }

        return result;
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