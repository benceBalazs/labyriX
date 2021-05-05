package com.labyrix.game;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ArrowActors extends Actor {
    private static Actor arrowActorLeft = null;
    private static Actor arrowActorRight = null;
    private static Actor arrowActorUp = null;
    private static Actor arrowActorDown = null;


    public Actor getArrowActorLeft() {
        return arrowActorLeft;
    }

    public void setArrowActorLeft(Actor arrowActorLeft) {
        if (ArrowActors.arrowActorLeft == null) {
            ArrowActors.arrowActorLeft = new Actor();
            ArrowActors.arrowActorLeft = arrowActorLeft;
        }
    }

    public Actor getArrowActorRight() {
        return arrowActorRight;
    }

    public void setArrowActorRight(Actor arrowActorRight) {
        ArrowActors.arrowActorRight = arrowActorRight;
    }

    public Actor getArrowActorUp() {
        return arrowActorUp;
    }

    public void setArrowActorUp(Actor arrowActorUp) {
        ArrowActors.arrowActorUp = arrowActorUp;
    }

    public Actor getArrowActorDown() {
        return arrowActorDown;
    }

    public void setArrowActorDown(Actor arrowActorDown) {
        ArrowActors.arrowActorDown = arrowActorDown;
    }
}
