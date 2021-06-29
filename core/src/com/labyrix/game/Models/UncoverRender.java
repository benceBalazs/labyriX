package com.labyrix.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UncoverRender {
    private Viewport viewport;
    private Stage stage;

    private Uncover uncover = null;

    public UncoverRender(Camera camera){
        this.viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),camera);
        this.stage = new Stage(viewport);
    }

    public void setInputProcess(){
        Gdx.input.setInputProcessor(stage);
    }

    public void render(){
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void clear(){
        stage.clear();
    }

    public void addToStage(Actor actor){
        this.stage.addActor(actor);
    }

    public Stage getStage() {
        return stage;
    }

    public Uncover getUncover() {
        return uncover;
    }
}
