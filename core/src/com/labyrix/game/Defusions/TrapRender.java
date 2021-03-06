package com.labyrix.game.Defusions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TrapRender {
    private Viewport viewport;
    private Stage stage;

    private BombDefuse bombDefuse = null;
    private MovementDefuse movementDefuse = null;

    public TrapRender(Camera camera){
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
        stage.addActor(actor);
    }

    public Stage getStage() {
        return stage;
    }

    public MovementDefuse getMovementDefuse() {
        return movementDefuse;
    }

    public void setMovementDefuse(MovementDefuse movementDefuse) {
        this.movementDefuse = movementDefuse;
    }

    public BombDefuse getBombDefuse() {
        return bombDefuse;
    }

    public void setBombDefuse(BombDefuse bombDefuse) {
        this.bombDefuse = bombDefuse;
    }
}
