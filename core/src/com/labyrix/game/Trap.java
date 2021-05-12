package com.labyrix.game;

import java.util.Random;

public class Trap {
    private float probability;
    private TrapEvent event;

    public Trap (float probability){
        if (probability > 1 || probability < 0){
            throw new IllegalArgumentException("Probability has to be ≥0 and ≤1");
        }
        this.probability = probability;
        this.event = new TrapEvent();
    }

    public boolean isTrapActivated () {
        boolean result = false;

        Random random = new Random();
        float randomFloat = random.nextFloat();

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