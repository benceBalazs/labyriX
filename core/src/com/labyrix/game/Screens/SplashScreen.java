package com.labyrix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.labyrix.game.LabyrixMain;
import com.badlogic.gdx.graphics.GL20;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen implements Screen {
    private final LabyrixMain labyrixMain;
    private Stage stage;
    private Image splashImg;

    public SplashScreen(){
        this.labyrixMain = LabyrixMain.getINSTANCE();
        this.stage = new Stage(new FitViewport(labyrixMain.getWIDTH(), labyrixMain.getHEIGHT(), labyrixMain.getCamera()));
    }

    @Override
    public void show() {
        System.out.println("SPLASH");
        Gdx.input.setInputProcessor(stage);

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                labyrixMain.setScreen(labyrixMain.getTitleScreen());
            }
        };

        Texture splashTex = labyrixMain.getAssets().get("logo.png", Texture.class);
        splashImg = new Image(splashTex);
        splashImg.setOrigin(0, 0);
        splashImg.setSize(labyrixMain.getWIDTH()/3f,labyrixMain.getHEIGHT()/2.75f);
        splashImg.setPosition(labyrixMain.getWIDTH()/2f, labyrixMain.getHEIGHT());
        splashImg.addAction(sequence(alpha(0), scaleTo(.1f, .1f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(1f, 1f, 2.5f, Interpolation.pow2),
                        moveTo(labyrixMain.getWIDTH()/2f - splashImg.getWidth()/2, labyrixMain.getHEIGHT()/2f - splashImg.getHeight()/2, 2f, Interpolation.swing)),
                delay(1.5f), fadeOut(1.25f), run(transitionRunnable)));

        stage.addActor(splashImg);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
    }

    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.clear();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
