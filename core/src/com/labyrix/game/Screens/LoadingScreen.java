package com.labyrix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.labyrix.game.LabyrixMain;

public class LoadingScreen implements Screen {
    private final LabyrixMain labyrixMain;
    private ShapeRenderer shapeRenderer;
    private float progress;

    public LoadingScreen(){
        this.labyrixMain = LabyrixMain.getINSTANCE();
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        shapeRenderer.setProjectionMatrix(labyrixMain.getCamera().combined);
        this.progress = 0f;
        queueAssets();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateBar(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(32, labyrixMain.getCamera().viewportHeight / 2 - 8, labyrixMain.getCamera().viewportWidth - 64, 8);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(32, labyrixMain.getCamera().viewportHeight / 2 - 8, progress * (labyrixMain.getCamera().viewportWidth - 64), 8);
        shapeRenderer.end();
    }

    private void updateBar(float delta) {
        progress = MathUtils.lerp(progress, labyrixMain.getAssets().getProgress(), .1f);
        if (labyrixMain.getAssets().update() && progress >= labyrixMain.getAssets().getProgress() - .001f) {
            labyrixMain.setScreen(labyrixMain.getSplashScreen());
        }
    }

    private void queueAssets() {
        labyrixMain.getAssets().load("logo.png", Texture.class);
        labyrixMain.getAssets().load("lobby.png", Texture.class);
        labyrixMain.getAssets().load("ui/uiskin.png", Texture.class);
        labyrixMain.getAssets().load("ui/uiskin.atlas", TextureAtlas.class);
        labyrixMain.getAssets().load("logoTitle.png", Texture.class);
        labyrixMain.getAssets().load("background.png", Texture.class);
        labyrixMain.getAssets().load("winnerImg.png",Texture.class);
        labyrixMain.getAssets().load("loserImg.png", Texture.class);
    }

    @Override
    public void resize(int width, int height) {

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

    @Override
    public void dispose() {

    }
}
