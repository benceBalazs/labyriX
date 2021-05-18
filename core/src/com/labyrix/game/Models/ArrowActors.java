package com.labyrix.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ArrowActors {
    private Stage stage;
    private Viewport viewport;
    private boolean isHidden = false;


    private ArrowActor arrowActorLeft = null;
    private ArrowActor arrowActorRight = null;
    private ArrowActor arrowActorUp = null;
    private ArrowActor arrowActorDown = null;


    public ArrowActors(Camera camera) {
        this.viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),camera);
        this.stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);
    }

    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void hide() {
        stage.clear();
    }

    public Actor getArrowActorLeft() {
        return this.arrowActorLeft;
    }

    public void setArrowActorLeft(ArrowActor arrowActorLeft) {
        if (this.arrowActorLeft == null) {
            this.arrowActorLeft = arrowActorLeft;
        }
    }

    public Actor getArrowActorRight() {
        return this.arrowActorRight;
    }

    public void setArrowActorRight(ArrowActor arrowActorRight) {
        this.arrowActorRight = arrowActorRight;
    }

    public Actor getArrowActorUp() {
        return this.arrowActorUp;
    }

    public void setArrowActorUp(ArrowActor arrowActorUp) {
        this.arrowActorUp = arrowActorUp;
    }

    public Actor getArrowActorDown() {
        return this.arrowActorDown;
    }

    public void setArrowActorDown(ArrowActor arrowActorDown) {
        this.arrowActorDown = arrowActorDown;
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean ishidden) {
        this.isHidden = ishidden;
    }
}
