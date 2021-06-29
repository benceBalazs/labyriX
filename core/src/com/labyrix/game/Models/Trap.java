package com.labyrix.game.Models;

import java.util.Random;

public class Trap {

    /**
     * probability: tells how likely a trap is activated.
     * event: what kind of trap it is.
     */
    private float probability;
    private TrapEvent event;

    public Trap (float probability){
        if (probability < 0 || probability > 1){
            throw new IllegalArgumentException("The probability must be between 0 and 1.");
        }
        this.probability = probability;
        this.event = new TrapEvent();
    }

    /**
     * tells if a trap has been activated based on the probability the trap has.
     * @return true, if a trap is activated.
     */
    public boolean isTrapActivated () {
        boolean result = false;

        Random random = new Random();
        float randomFloat = random.nextFloat();

        if (randomFloat <= this.probability){
            result = true;
        }

        return result;
    }

    /**
     * getter and setter.
     */
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