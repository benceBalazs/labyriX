package com.labyrix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.labyrix.game.LabyrixMain;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class LoserScreen extends ScreenAdapter {
    private final LabyrixMain labyrixMain;
    private Stage stage;
    private Skin skin;
    private Image backgroundImg;
    private TextButton buttonBackToTitleScreen;
    private Image winnerImg;

    public LoserScreen() {
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
        initScreen();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    public void initScreen(){
        Texture backgroundTex = labyrixMain.getAssets().get("background.png", Texture.class);
        backgroundImg = new Image(backgroundTex);
        backgroundImg.setPosition(0-Gdx.graphics.getWidth()/8f, 0-Gdx.graphics.getHeight()/8f);
        backgroundImg.setSize(Gdx.graphics.getWidth()+2*Gdx.graphics.getWidth()/8f, Gdx.graphics.getHeight()+2*Gdx.graphics.getHeight()/8f);
        backgroundImg.addAction(sequence(alpha(0),fadeIn(.5f)));

        buttonBackToTitleScreen = new TextButton("Back", skin, "default");
        buttonBackToTitleScreen.setSize(labyrixMain.getWIDTH()/3.5f, labyrixMain.getHEIGHT()/9f);
        buttonBackToTitleScreen.setPosition(labyrixMain.getWIDTH()/2f-buttonBackToTitleScreen.getWidth()/2, labyrixMain.getHEIGHT()/2f-labyrixMain.getHEIGHT()/4f);
        buttonBackToTitleScreen.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonBackToTitleScreen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                labyrixMain.setScreen(labyrixMain.getTitleScreen());
            }
        });

        Texture splashTex = labyrixMain.getAssets().get("loserImg.png", Texture.class);
        winnerImg = new Image(splashTex);
        winnerImg.setSize(labyrixMain.getWIDTH()/1.5f,labyrixMain.getHEIGHT()/3f );
        winnerImg.setPosition(labyrixMain.getWIDTH()/2f-winnerImg.getWidth()/2, labyrixMain.getHEIGHT()/2f);
        winnerImg.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));

        stage.addActor(backgroundImg);
        stage.addActor(buttonBackToTitleScreen);
        stage.addActor(winnerImg);
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
