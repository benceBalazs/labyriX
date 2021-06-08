package com.labyrix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.labyrix.game.LabyrixMain;
import com.labyrix.game.Models.Board;

public class WinnerScreen implements Screen {
    private final LabyrixMain labyrixMain;
    private Stage stage;
    private Skin skin;
    private Image backgroundImg;
    private TextButton buttonBackToTitleScreen;
    private Image logoImg;

    public WinnerScreen() {
        this.labyrixMain= LabyrixMain.getINSTANCE();
        this.stage = new Stage(new FitViewport(labyrixMain.getWIDTH(), labyrixMain.getHEIGHT(), labyrixMain.getCamera()));
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        this.skin = new Skin();
        this.skin.addRegions(labyrixMain.getAssets().get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", labyrixMain.getFontBig());
        this.skin.load(Gdx.files.internal("ui/uiskins.json"));
        //Methode Call
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


}
